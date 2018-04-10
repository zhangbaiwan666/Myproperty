package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cottee.myproperty.R;
import cottee.myproperty.adapter.SubinfoAdapter;
import cottee.myproperty.constant.HouseListBean;
import cottee.myproperty.constant.SubListBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.HealthMap;
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
    public static PopupWindow popRight;
    private View layoutRight;
    private ListView menulistRight;
    private  ArrayList<String> sub_remark_list;
    private  ArrayList<String> sub_phone_list;
    private  ArrayList<String> sub_id_list;
    private String sub_remark;
    private String sub_phone;
    private String sub_id;
    private static int property_home_id;
    private static int property_pro_id;
    public static SubinfoAdapter  sub_adapter;
    private List<Map<String, String>> listRight;
    private static ArrayList<String> address_list;
    private  ArrayList<String> home_id_list;
    private List<String> list;
    private static String choosed_property_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlsub);
        initTitle();
        initParam();

        address_list = (ArrayList<String>) HealthMap.get("address_list");
        home_id_list = (ArrayList<String>) HealthMap.get("home_id_list");
        final Intent intent = getIntent();
        choosed_property_name = intent.getStringExtra("choosed_property_name");
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
//                menuWindow = new SelectPicPopupWindow(ControlSubActivity.this, itemsOnClick);
//                //显示窗口
//                menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        bt_add_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(ControlSubActivity.this, AddSubActivity.class);
                    intent.putExtra("home_id", property_home_id);
                    intent.putExtra("pro_id", property_pro_id);
                    startActivity(intent);
                    finish();
                    if (click) {
                        click = false;

                }
            }
        });

    }

    private List<HouseListBean> init() {
        List<HouseListBean> subList=new ArrayList<HouseListBean>();
        if(address_list==null){}else {
            for (int i = 0; i < address_list.size(); i++) {
                String s = address_list.get(i);
                HouseListBean houseInfo = new HouseListBean(s);
                subList.add(houseInfo);
            }
        }
        return subList;
    }
    //-----------------------------------加载list中item的数据---------------------------------STA
    private List<SubListBean> initsub() {
        List<SubListBean> subList1 =( List<SubListBean>) HealthMap.get("sub_list");
//        List<SubInfo> subList=new ArrayList<SubInfo>();
//        for(int i=0;i<sub_remark_list.size();i++){
//            String sub_remark = sub_remark_list.get(i);
//            String sub_id = sub_id_list.get(i);
//            String sub_phone = sub_phone_list.get(i);
//            String str2=sub_phone.replace(" ", "");//去掉所用空格
//            list =  Arrays.asList(str2.split(","));
////list的结果就是[113,123,123,123]
//            SubInfo subInfo = new SubInfo(sub_remark,sub_id,list);
//            subList.add(subInfo);
//        }
        return subList1;
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
            List<SubListBean> subList = initsub();
            sub_adapter = new SubinfoAdapter(
                    ControlSubActivity.this, R.layout.layout_list_item,subList);
            //把R.layout.fruit_item和初始化完毕的ArrayList<Fruit>给FruitAdapter
//------------------------------子账户listview的item点击事件----------------------------------------STA
            final ListView listView=(ListView)findViewById(R.id.sub_list);
            if (listView==null){}
//            listView.setAdapter(sub_adapter);//把Subinfo给ListView
//            sub_adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent1 = new Intent(ControlSubActivity.this, DelateSubActivity.class);
                    SubinfoAdapter adpter= (SubinfoAdapter) parent.getAdapter();
                    SubListBean item = (SubListBean)listView.getAdapter().getItem(position);
                    intent1.putExtra("remark",item.getRemark());
                    intent1.putExtra("id",item.getUser_id());
                    sub_phone = item.getPhone_num();
                    if (sub_phone.toString().trim()==null){
                        sub_phone="";
                    }
                    list =  Arrays.asList(sub_phone.split(","));
                    intent1.putExtra("phone",(Serializable)list);
                    System.out.println("ControlSubActivtiy为sub_remark"+ sub_remark);
                    System.out.println("ControlSubActivtiy为sub_id"+ sub_id);
                    System.out.println("ControlSubActivtiy为sub_phone"+ sub_phone);
//                for (int i=0;i<adpter.getCount();i++) {
//                    SubInfo item = adpter.getItem(i);//拿到当前数据值并强转   adpter.getItem(i)即为当前数据对象
                    startActivity(intent1);
                    finish();
                }


            });

        System.out.println("ControlSubActivity执行了onStart操作");
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
//                case R.id.btn_phone_one:
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18888888888"));
//                    if (ActivityCompat.checkSelfPermission(ControlSubActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    ControlSubActivity.this.startActivity(intent);
//                    break;
//                case R.id.btn_phone_two:

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
                        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(ControlSubActivity.this, "", "");
                        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(ControlSubActivity.this, loginRegisterHandler);
                        loginRegisterManager.ShowAllHouseForSub();
//                            final ChooseHouseAdapter listAdapter = new ChooseHouseAdapter(
////								getContext(), listRight, R.layout.pop_menuitem,
////								new String[] { "item" },
////								new int[] { R.id.menuitem }
//                                    ControlSubActivity.this, R.layout.pop_menuitem, subList);
//                            menulistRight.setAdapter(listAdapter);
//                            listAdapter.notifyDataSetChanged();

                            //----------------------- 点击listview中item的处理-------------------------------------STA
                        View layoutRight = ControlSubActivity.this.getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        final ListView menulistRight = layoutRight.findViewById(R.id.menulist);
//                        ChooseHouseAdapter listAdapter = new ChooseHouseAdapter(
//                                ControlSubActivity.this, R.layout.pop_menuitem, T());
//                        menulistRight.setAdapter(listAdapter);
//                        listAdapter.notifyDataSetChanged();

                        break;


                default:
                    break;
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

    }
}
