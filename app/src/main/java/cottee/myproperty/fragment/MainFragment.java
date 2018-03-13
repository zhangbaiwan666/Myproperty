package cottee.myproperty.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cottee.myproperty.R;
import cottee.myproperty.activity.AddServerActivity;
import cottee.myproperty.activity.PaymentActivity;
import cottee.myproperty.activity.PropertyAdActivity;
import cottee.myproperty.activity.RepairProjectActivity;
import cottee.myproperty.activity.TabLessActivity;
import cottee.myproperty.adapter.ChoosePropertyAdapter;
import cottee.myproperty.adapter.PreviewBulletinAdapter;
import cottee.myproperty.constant.BullentinBean;
import cottee.myproperty.constant.PropertyListBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.HealthMap;
import cottee.myproperty.uitils.Session;

public class MainFragment extends Fragment {
	private Button bt_placard;
	private TextView tvRight;
	private boolean click=true;
	private int imageIds[];
	private String[] titles;
	private ArrayList<ImageView> images;
	private ArrayList<View> dots;
	private TextView title;
	private ViewPager mViewPager;
	private ViewPagerAdapter adapter;
	private PopupWindow popRight;
	private View layoutRight;
	private ListView menulistRight;
	private List<Map<String, String>> listRight;

	private int oldPosition = 0;//记录上一次点的位置
	private int currentItem; //当前页面
	private ScheduledExecutorService scheduledExecutorService;
	private View inflate;
	private Button bt_property_server;
	private LinearLayout ll_placard;
	private LinearLayout ll_payFee;
	private LinearLayout ll_repair;
	private LinearLayout ll_property_server;
	private Button bt_repair;
	private Button bt_payFee;
	private static ArrayList<String> property_list;
	private static ArrayList<String> pro_id_list;
	private TextView positon_pro_name;
	private View rootView;
	private ListView list_preview_bulletin;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (rootView == null) {
			property_list = (ArrayList<String>) HealthMap.get("property_list");
			rootView = inflater.inflate(R.layout.fragment_main, null);
			bt_property_server = (Button) rootView.findViewById(R.id.bt_property_server);
			positon_pro_name = rootView.findViewById(R.id.positon_pro_name);
			bt_placard = (Button) rootView.findViewById(R.id.bt_placard);
			bt_payFee = (Button) rootView.findViewById(R.id.bt_payFee);
			bt_repair = (Button) rootView.findViewById(R.id.bt_repair);
			ll_placard = (LinearLayout) rootView.findViewById(R.id.ll_placard);
			ll_payFee = (LinearLayout) rootView.findViewById(R.id.ll_payFee);
			ll_repair = (LinearLayout) rootView.findViewById(R.id.ll_repair);
			ll_property_server = (LinearLayout) rootView.findViewById(R.id.ll_property_server);
			ll_placard.setFocusable(true);
			ll_payFee.setFocusable(true);
			ll_repair.setFocusable(true);
			ll_property_server.setFocusable(true);

			pro_id_list = (ArrayList<String>) HealthMap.get("pro_id_list");
			pop();
			initEven();
			initParam();

		}ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null)
		{
			parent.removeView(rootView);
		}
		return rootView;
	}
	private void initParam() {
		tvRight = (TextView) rootView.findViewById(R.id.tv_right);
		tvRight.setOnClickListener(myListener);
		// 初始化数据项
		listRight = new ArrayList<Map<String, String>>();
		for (int i = 1; i < 10; i++) {
			HashMap<String, String> mapTemp = new HashMap<String, String>();
			mapTemp.put("item", "right " + i);
			listRight.add(mapTemp);
		}
		list_preview_bulletin = rootView.findViewById(R.id.list_preview_bulletin);
		List<BullentinBean> bullentinlist = initbullentinlist();
		BullentinBean bullentinBean = new BullentinBean();
		bullentinBean.setTime("18:30");
		bullentinBean.setTitle("供暖通知");
		bullentinBean.setMessage("小区将于清明节后停止供暖");
		bullentinlist.add(0,bullentinBean);

		BullentinBean bullentinBean1 = new BullentinBean();
		bullentinBean1.setTime("11:30");
		bullentinBean1.setTitle("停水通知");
		bullentinBean1.setMessage("明天后两天小区停水，望周知");
		bullentinlist.add(1,bullentinBean1);

		BullentinBean bullentinBean2 = new BullentinBean();
		bullentinBean2.setTime("8:30");
		bullentinBean2.setTitle("停电通知");
		bullentinBean2.setMessage("明天后两天小区停电，望周知");
		bullentinlist.add(2,bullentinBean2);

		BullentinBean bullentinBean3 = new BullentinBean();
		bullentinBean3.setTime("2:30");
		bullentinBean3.setTitle("停水通知");
		bullentinBean3.setMessage("明天后两天小区停水，望周知");
		bullentinlist.add(3,bullentinBean3);

		PreviewBulletinAdapter previewBulletinAdapter = new PreviewBulletinAdapter(getContext(), R.layout.layout_bulletin_list, bullentinlist);
		list_preview_bulletin.setAdapter(previewBulletinAdapter);
		list_preview_bulletin.setDivider(getContext().getResources().getDrawable(R.drawable.list_divider));
		list_preview_bulletin.setDividerHeight(1);
		previewBulletinAdapter.notifyDataSetChanged();
	}

	private List<BullentinBean> initbullentinlist() {
		List<BullentinBean> bullentinlist=new ArrayList<BullentinBean>();
		return bullentinlist;
	}

	private void initEven() {
		ll_placard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(), TabLessActivity.class);
				getActivity().startActivity(intent);
			}
		});

		ll_property_server.setOnClickListener(new NoDoubleClickListener(){
			@Override
			public void onNoDoubleClick(View view) {
				Intent intent = new Intent(getActivity(), AddServerActivity.class);
				startActivity(intent);
			}
		});
		ll_repair.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
            Intent intent=new Intent(getActivity(), RepairProjectActivity.class);
            startActivity(intent);
			}
		});
		ll_payFee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(getActivity(), PaymentActivity.class);
				startActivity(intent);

			}
		});

	}

	//切换滚轮图片
	private void pop() {
		//TODO 增加切换图片数量，来自服务器；在小区团购中增加物业pop用于物业宣传
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		//每隔5秒钟切换一张图片
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);
				imageIds = new int[]{
						R.mipmap.ad5,
						R.mipmap.ad2,
						R.mipmap.ad3,
						R.mipmap.ad4,
						R.mipmap.splash,
						R.mipmap.ad3,
						R.mipmap.ad5,
						R.mipmap.ad2
				};
				//图片标题
				titles = new String[]{
						"全新版本爱尚家上线啦",
						"app广告位1",
						"app广告位2",
						"app广告位3",
						"app广告位4",
						"app广告位5",
						"app广告位6",
						"app广告位7"
				};

				//显示的图片
				images = new ArrayList<ImageView>();
				for(int i =0; i < imageIds.length; i++){
					ImageView imageView = new ImageView(getContext());
					imageView.setBackgroundResource(imageIds[i]);
					final int finalI = i;
					imageView.setOnClickListener(new NoDoubleClickListener() {
						@Override
						protected void onNoDoubleClick(View v) {
							Intent intent = new Intent(getContext(), PropertyAdActivity.class);
							intent.putExtra("Ad_numb",finalI);
							intent.putExtra("Ad_name",titles[finalI]);
							startActivity(intent);
						}
					});

					images.add(imageView);
				}

				//显示的点
				dots = new ArrayList<View>();
				dots.add(rootView.findViewById(R.id.dot_0));
				dots.add(rootView.findViewById(R.id.dot_1));
				dots.add(rootView.findViewById(R.id.dot_2));
				dots.add(rootView.findViewById(R.id.dot_3));
				dots.add(rootView.findViewById(R.id.dot_4));
				dots.add(rootView.findViewById(R.id.dot_5));
				dots.add(rootView.findViewById(R.id.dot_6));
				dots.add(rootView.findViewById(R.id.dot_7));

				title = (TextView) rootView.findViewById(R.id.title);
				title.setText(titles[0]);

				mViewPager = (ViewPager)rootView.findViewById(R.id.vp);

				adapter = new ViewPagerAdapter();
				mViewPager.setAdapter(adapter);

				mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// TODO Auto-generated method stub
						title.setText(titles[position]);

						dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
						dots.get(position).setBackgroundResource(R.drawable.dot_focused);

						oldPosition = position;
						currentItem = position;
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});

			}

			private class ViewPagerAdapter extends PagerAdapter {

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return images.size();
				}

				//是否是同一张图片
				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					// TODO Auto-generated method stub
					return arg0 == arg1;
				}

				@Override
				public void destroyItem(ViewGroup view, int position, Object object) {
					// TODO Auto-generated method stub
//            super.destroyItem(container, position, object);
//            view.removeViewAt(position);
					view.removeView(images.get(position));

				}

				@Override
				public Object instantiateItem(ViewGroup view, int position) {
					// TODO Auto-generated method stub
					view.addView(images.get(position));

					return images.get(position);
				}
			}

			//切换图片
			private class ViewPagerTask implements Runnable {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					currentItem = (currentItem +1) % imageIds.length;
					//更新界面
            handler.sendEmptyMessage(0);
					handler.obtainMessage().sendToTarget();
				}

			}

			private Handler handler = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					//设置当前页面
					mViewPager.setCurrentItem(currentItem);
				}

			};

	private List<PropertyListBean> init() {
		List<PropertyListBean> proList=new ArrayList<PropertyListBean>();
		for(int i=0;i<property_list.size();i++){
			String s = property_list.get(i);
			PropertyListBean pro_Info = new PropertyListBean(s);
			proList.add(pro_Info);
		}
		return proList;
	}
//--------------------物业切换栏--------------------------------------------------------------STA
	private View.OnClickListener myListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (property_list == null) {
				Toast.makeText(getContext(), "当前无物业", Toast.LENGTH_SHORT).show();
			} else {
				switch (v.getId()) {
					case R.id.tv_right:
						if (popRight != null && popRight.isShowing()) {
							popRight.dismiss();
						} else {
							if (property_list.size() > 0) {
								List<PropertyListBean> subList = init();
								layoutRight = getLayoutInflater().inflate(
										R.layout.pop_menulist, null);
								menulistRight = (ListView) layoutRight
										.findViewById(R.id.menulist);
								ChoosePropertyAdapter listAdapter = new ChoosePropertyAdapter(
//								getContext(), listRight, R.layout.pop_menuitem,
//								new String[] { "item" },
//								new int[] { R.id.menuitem }
										getContext(), R.layout.pop_menuitem, subList);
								menulistRight.setAdapter(listAdapter);
								listAdapter.notifyDataSetChanged();

								// 点击listview中item的处理
								menulistRight
										.setOnItemClickListener(new AdapterView.OnItemClickListener() {
											@Override
											public void onItemClick(AdapterView<?> parent,
																	View view, int position, long id) {
												String strItem = property_list.get(position);
//												tvRight.setText(strItem);
												positon_pro_name.setText(strItem);
												HealthMap.put("choosed_property_name", strItem);
												String session = Session.getSession();
												LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(getContext(), "", "");
												LoginRegisterManager loginRegisterManager = new LoginRegisterManager(getContext(), loginRegisterHandler);
												loginRegisterManager.ChooseProperty(session, pro_id_list.get(position));

												if (popRight != null && popRight.isShowing()) {
													popRight.dismiss();
												}
											}
										});

								popRight = new PopupWindow(layoutRight, 200,
										ViewGroup.LayoutParams.WRAP_CONTENT);

//								ColorDrawable cd = new ColorDrawable(-1000);
////								popRight.setBackgroundDrawable(cd);
								popRight.setAnimationStyle(R.style.PopupAnimation);
								popRight.update();
								popRight.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
								popRight.setTouchable(true); // 设置popupwindow可点击
								popRight.setOutsideTouchable(true); // 设置popupwindow外部可点击
								popRight.setFocusable(true); // 获取焦点

								// 设置popupwindow的位置
								int topBarHeight = 30;
								popRight.showAsDropDown(tvRight, 0,
										(topBarHeight - tvRight.getHeight()) / 2);

								popRight.setTouchInterceptor(new View.OnTouchListener() {

									@Override
									public boolean onTouch(View v, MotionEvent event) {
										// 如果点击了popupwindow的外部，popupwindow也会消失
										if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
											popRight.dismiss();
											return true;
										}
										return false;
									}
								});
							}
							break;
						}

					default:
						break;
				}
			}
		}
	};

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				super.onStop();
			}



//	private void showPopupMenu(View view) {
//		// View当前PopupMenu显示的相对View的位置
//		PopupMenu popupMenu = new PopupMenu(getContext(), view);
//		// menu布局
//		popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
//		// menu的item点击事件
//		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
//		// PopupMenu关闭事件
//		popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//			@Override
//			public void onDismiss(PopupMenu menu) {
//				Toast.makeText(getContext(), "切换", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		popupMenu.show();
//	}
}
