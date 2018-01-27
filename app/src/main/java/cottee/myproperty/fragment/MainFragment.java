package cottee.myproperty.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cottee.myproperty.R;
import cottee.myproperty.activity.LoginActivity;
import cottee.myproperty.activity.RepairProjectActivity;
import cottee.myproperty.activity.TabLessActivity;
import cottee.myproperty.listener.NoDoubleClickListener;

public class MainFragment extends Fragment {
	private Button bt_property;
	private Button bt_checkout;
	private Button bt_placard;
	private static Map<String,Activity> destoryMap = new HashMap<>();
	private boolean click=true;
	private Button btn_change_pro; private int imageIds[];
	private String[] titles;
	private ArrayList<ImageView> images;
	private ArrayList<View> dots;
	private TextView title;
	private ViewPager mViewPager;
	private ViewPagerAdapter adapter;

	private int oldPosition = 0;//记录上一次点的位置
	private int currentItem; //当前页面
	private ScheduledExecutorService scheduledExecutorService;
	private View inflate;
	private Button bt_property_server;
	private LinearLayout ll_placard;
	private Button bt_repair;
	private Button bt_payFee;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		inflate = inflater.inflate(R.layout.fragment_main, null);
		bt_checkout = (Button) inflate.findViewById(R.id.bt_checkout);
		btn_change_pro = (Button) inflate.findViewById(R.id.btn_change_pro);
		bt_property_server = (Button) inflate.findViewById(R.id.bt_property_server);
		bt_placard = (Button) inflate.findViewById(R.id.bt_placard);
		bt_payFee = (Button)inflate.findViewById(R.id.bt_payFee);
		bt_repair = (Button)inflate.findViewById(R.id.bt_repair);
		ll_placard = (LinearLayout) inflate.findViewById(R.id.ll_placard);
		pop();
		initEven();
		return inflate;
	}

	private void initEven() {
		bt_placard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(), TabLessActivity.class);
				getActivity().startActivity(intent);
			}
		});

		bt_property_server.setOnClickListener(new NoDoubleClickListener(){
			@Override
			public void onNoDoubleClick(View view) {

			}
		});
		btn_change_pro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showPopupMenu(btn_change_pro);
			}
		});
		bt_repair.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
            Intent intent=new Intent(getActivity(), RepairProjectActivity.class);
            startActivity(intent);
			}
		});
		bt_payFee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		bt_checkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new AlertDialog.Builder(getActivity())
						.setMessage("确定要退出吗")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SharedPreferences preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
								String email=preferences.getString("name", "");
								String password=preferences.getString("psword", "");
								Intent intent = new Intent(getActivity(), LoginActivity.class);
								intent.putExtra("name",email);
								intent.putExtra("psword",password);
								startActivity(intent);
								dialog.dismiss();
//                                    ((BaseActivity)getActivity()).goNextAnim();
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.create()
						.show();
			}
		});
	}

	private void pop() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		//每隔5秒钟切换一张图片
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);
				imageIds = new int[]{
						R.mipmap.ad5,
						R.mipmap.ad2,
						R.mipmap.ad3,
						R.mipmap.ad4,
						R.mipmap.splash
				};
				//图片标题
				titles = new String[]{
						"巩俐不低俗，我就不能低俗",
						"扑树又回来啦！再唱经典老歌引万人大合唱",
						"揭秘北京电影如何升级",
						"乐视网TV版大派送",
						"热血屌丝的反杀"
				};

				//显示的图片
				images = new ArrayList<ImageView>();
				for(int i =0; i < imageIds.length; i++){
					ImageView imageView = new ImageView(getContext());
					imageView.setBackgroundResource(imageIds[i]);

					images.add(imageView);
				}

				//显示的点
				dots = new ArrayList<View>();
				dots.add(inflate.findViewById(R.id.dot_0));
				dots.add(inflate.findViewById(R.id.dot_1));
				dots.add(inflate.findViewById(R.id.dot_2));
				dots.add(inflate.findViewById(R.id.dot_3));
				dots.add(inflate.findViewById(R.id.dot_4));

				title = (TextView) inflate.findViewById(R.id.title);
				title.setText(titles[0]);

				mViewPager = (ViewPager)inflate.findViewById(R.id.vp);

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

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				super.onStop();
			}






	private void showPopupMenu(View view) {
		// View当前PopupMenu显示的相对View的位置
		PopupMenu popupMenu = new PopupMenu(getContext(), view);
		// menu布局
		popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
		// menu的item点击事件
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		// PopupMenu关闭事件
		popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
			@Override
			public void onDismiss(PopupMenu menu) {
				Toast.makeText(getContext(), "切换", Toast.LENGTH_SHORT).show();
			}
		});

		popupMenu.show();
	}
}
