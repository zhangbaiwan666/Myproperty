package cottee.myproperty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import cottee.myproperty.R;
import cottee.myproperty.adapter.ChooseHouseAdapter;
import cottee.myproperty.adapter.ChoosePropertyAdapter;
import cottee.myproperty.adapter.SubinfoAdapter;
import cottee.myproperty.constant.HouseListBean;
import cottee.myproperty.constant.PropertyListBean;
import cottee.myproperty.constant.SubInfo;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.HealthMap;
import cottee.myproperty.uitils.Session;
import cottee.myproperty.widgets.SelectPicPopupWindow;
import cottee.myproperty.widgets.Title;

public class ControlSubActivity extends Activity {

    private SelectPicPopupWindow menuWindow;
    private Title title;
    private Button bt_add_sub;
    private Button btn_change_house,btn_host_phone;
    private boolean click=true;
    private TextView tvRight;
    private TextView tv_show_house;
    private TextView tv_show_property;
    private PopupWindow popRight;
    private View layoutRight;
    private ListView menulistRight;
    private  ArrayList<String> sub_remark_list;
    private  ArrayList<String> sub_phone_list;
    private  ArrayList<String> sub_id_list;
    private String sub_remark;
    private String sub_phone;
    private String sub_id;
    private int property_home_id;
    private int property_pro_id;
    private SubinfoAdapter sub_adapter;
    private List<Map<String, String>> listRight;
    private static ArrayList<String> address_list;
    private  ArrayList<String> home_id_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlsub);
        initTitle();
        initParam();

        address_list = (ArrayList<String>) HealthMap.get("address_list");
        home_id_list = (ArrayList<String>) HealthMap.get("home_id_list");
        final Intent intent = getIntent();
        String choosed_property_name = intent.getStringExtra("choosed_property_name");
        sub_remark_list = intent.getStringArrayListExtra("sub_remark_list");
        sub_phone_list = intent.getStringArrayListExtra("sub_phone_list");
        sub_id_list = intent.getStringArrayListExtra("sub_id_list");

        System.out.println("ControlSubActivity的sub_remark_list为"+sub_remark_list);
        String property_name = intent.getStringExtra("property_name");
        property_home_id = intent.getIntExtra("property_home_id", 0);
        property_pro_id = intent.getIntExtra("property_pro_id", 0);
        System.out.println("property_home_id"+property_home_id);
        bt_add_sub = (Button) findViewById(R.id.btn_delete_sub);
        btn_host_phone = (Button)findViewById(R.id.btn_host_phone);

        tv_show_property.setText(choosed_property_name);

        btn_host_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuWindow = new SelectPicPopupWindow(ControlSubActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        bt_add_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_show_house.getText().toString().trim().equals("未选择房屋")||tv_show_property.getText().toString().trim().equals("尚未选择物业")) {

                    Toast.makeText(ControlSubActivity.this, "您尚未选择添加子账户的物业或房屋", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ControlSubActivity.this, AddSubActivity.class);
                    intent.putExtra("home_id", property_home_id);
                    intent.putExtra("pro_id", property_pro_id);
                    startActivity(intent);
                    finish();
                    if (click) {
                        click = false;
                    }
                }
            }
        });

    }

    private List<HouseListBean> init() {
        List<HouseListBean> subList=new ArrayList<HouseListBean>();
        for(int i=0;i<address_list.size();i++){
            String s = address_list.get(i);
            HouseListBean houseInfo = new HouseListBean(s);
            subList.add(houseInfo);
        }
        return subList;
    }
    private List<SubInfo> initsub() {
        List<SubInfo> subList=new ArrayList<SubInfo>();
        for(int i=0;i<sub_remark_list.size();i++){
            String sub_remark = sub_remark_list.get(i);
            String sub_id = sub_id_list.get(i);
            String sub_phone = sub_phone_list.get(i);
            SubInfo subInfo = new SubInfo(sub_remark,sub_id,sub_phone);
            subList.add(subInfo);
        }
        return subList;
    }


    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("家庭成员管理");
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
    }
    private Title.OnTitleButtonClickListener onTitleButtonClickListener = new Title.OnTitleButtonClickListener() {
        @Override
        public void onClick(int id, Title.ButtonViewHolder viewHolder) {
            if (id == Title.BUTTON_LEFT){
                finish();
                if(click){
                    click=false;}
            }
        }
    };
    private void initParam() {
        tvRight = (TextView)findViewById(R.id.tv_right);
        tv_show_house = (TextView)findViewById(R.id.tv_show_house);
        tv_show_property = (TextView)findViewById(R.id.tv_show_property);
        tvRight.setOnClickListener(myListener);
        // 初始化数据项
        }

    @Override
    protected void onStart() {
        super.onStart();
        if (sub_remark_list!=null){
            List<SubInfo> subList = initsub();
            sub_adapter = new SubinfoAdapter(
                    ControlSubActivity.this, R.layout.layout_list_item,subList);

            //把R.layout.fruit_item和初始化完毕的ArrayList<Fruit>给FruitAdapter

            final ListView listView=(ListView)findViewById(R.id.sub_list);
            listView.setAdapter(sub_adapter);//把Subinfo给ListView
            sub_adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent1 = new Intent(ControlSubActivity.this, DelateSubActivity.class);
                    SubinfoAdapter adpter= (SubinfoAdapter) parent.getAdapter();
                    sub_remark = sub_remark_list.get(position);
                    intent1.putExtra("remark",sub_remark);
                    sub_id = sub_id_list.get(position);
                    intent1.putExtra("id",sub_id);
                    sub_phone = sub_phone_list.get(position);
                    intent1.putExtra("phone",sub_phone);
                    System.out.println("ControlSubActivtiy为sub_remark"+ sub_remark);
                    System.out.println("ControlSubActivtiy为sub_id"+ sub_id);
                    System.out.println("ControlSubActivtiy为sub_phone"+ sub_phone);
//                for (int i=0;i<adpter.getCount();i++) {
//                    SubInfo item = adpter.getItem(i);//拿到当前数据值并强转   adpter.getItem(i)即为当前数据对象
                    startActivity(intent1);
                    finish();
                }


            });
        }else{
            Toast.makeText(ControlSubActivity.this, "您尚未添加任何子账户，请点击添加", Toast.LENGTH_SHORT).show();
        }
        System.out.println("ControlSubActivity执行了onStart操作");
    }


    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_phone_one:
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18888888888"));
                    if (ActivityCompat.checkSelfPermission(ControlSubActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    ControlSubActivity.this.startActivity(intent);
                    break;
                case R.id.btn_phone_two:
                    break;
                default:
                    break;
            }
}

    };
    private View.OnClickListener myListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_right:
                    if (popRight != null && popRight.isShowing()) {
                        popRight.dismiss();
                    } else {
                        if (address_list==null) {
                            Toast.makeText(ControlSubActivity.this, "当前物业无房屋", Toast.LENGTH_SHORT).show();
                            System.out.println("房屋的列表信息"+address_list);
                        } else {
                            List<HouseListBean> subList = init();
                            layoutRight = getLayoutInflater().inflate(
                                    R.layout.pop_menulist, null);
                            menulistRight = (ListView) layoutRight
                                    .findViewById(R.id.menulist);
                            ChooseHouseAdapter listAdapter = new ChooseHouseAdapter(
//								getContext(), listRight, R.layout.pop_menuitem,
//								new String[] { "item" },
//								new int[] { R.id.menuitem }
                                    ControlSubActivity.this, R.layout.pop_menuitem, subList);
                            menulistRight.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();

                            // 点击listview中item的处理
                            menulistRight
                                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent,
                                                                View view, int position, long id) {
                                            String strItem = address_list.get(position);
                                            tv_show_house.setText(strItem);
                                            String session = Session.getSession();
                                            LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(ControlSubActivity.this, "", "");
                                            LoginRegisterManager loginRegisterManager = new LoginRegisterManager(ControlSubActivity.this, loginRegisterHandler);
                                            loginRegisterManager.ChooseHouse(session,home_id_list.get(position));

                                            if (popRight != null && popRight.isShowing()) {
                                                popRight.dismiss();
                                            }
                                        }
                                    });

                            popRight = new PopupWindow(layoutRight, 340,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);

                            ColorDrawable cd = new ColorDrawable(-0000);
                            popRight.setBackgroundDrawable(cd);
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

    };
}
