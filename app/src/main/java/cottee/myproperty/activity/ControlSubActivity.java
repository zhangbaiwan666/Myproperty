package cottee.myproperty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.adapter.SubinfoAdapter;
import cottee.myproperty.constant.SubInfo;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.SelectPicPopupWindow;
import cottee.myproperty.widgets.Title;

public class ControlSubActivity extends Activity {

    private SelectPicPopupWindow menuWindow;
    private Title title;
    private LinearLayout ll_sub_item;
    private Button bt_add_sub;
    private Button btn_change_house,btn_host_phone;
    private boolean click=true;
    private View open_pop;
    private  ArrayList<String> sub_remark_list;
    private  ArrayList<String> sub_phone_list;
    private  ArrayList<String> sub_id_list;
    private String sub_remark;
    private String sub_phone;
    private String sub_id;
    private int property_home_id;
    private int property_pro_id;
    private SubinfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlsub);
        initTitle();
        final Intent intent = getIntent();
        sub_remark_list = intent.getStringArrayListExtra("sub_remark_list");
        sub_phone_list = intent.getStringArrayListExtra("sub_phone_list");
        sub_id_list = intent.getStringArrayListExtra("sub_id_list");

        System.out.println("ControlSubActivity的sub_remark_list为"+sub_remark_list);
        String property_name = intent.getStringExtra("property_name");
        property_home_id = intent.getIntExtra("property_home_id", 0);
        property_pro_id = intent.getIntExtra("property_pro_id", 0);
        System.out.println("property_home_id"+property_home_id);
        bt_add_sub = (Button) findViewById(R.id.btn_delete_sub);
        btn_change_house = (Button) findViewById(R.id.btn_change_house);
        btn_host_phone = (Button)findViewById(R.id.btn_host_phone);
        btn_change_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(btn_change_house);
            }
        });
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
                Intent intent = new Intent(ControlSubActivity.this, AddSubActivity.class);
               
                intent.putExtra("home_id", property_home_id);
                intent.putExtra("pro_id",property_pro_id);
                startActivity(intent);
                if(click){
                    click=false;}
            }
        });
    }

    private List<SubInfo> init() {
        List<SubInfo> subList=new ArrayList<SubInfo>();
        for(int i=0;i<sub_remark_list.size();i++){
            sub_remark = sub_remark_list.get(i);
            sub_phone = sub_phone_list.get(i);
            sub_id = sub_remark_list.get(i);
            SubInfo subInfo = new SubInfo(sub_remark, sub_id,sub_phone);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (sub_remark_list.size()>0){
            List<SubInfo> subList = init();
            adapter = new SubinfoAdapter(
                    ControlSubActivity.this, R.layout.layout_list_item,subList);

            //把R.layout.fruit_item和初始化完毕的ArrayList<Fruit>给FruitAdapter

            final ListView listView=(ListView)findViewById(R.id.sub_list);
            listView.setAdapter(adapter);//把Subinfo给ListView

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
                }


            });
        }else{
            Toast.makeText(ControlSubActivity.this, "申请子账户列表失败，请检查您的网络连接", Toast.LENGTH_SHORT).show();
        }
        System.out.println("ControlSubActivity执行了onStart操作");
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(ControlSubActivity.this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(ControlSubActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(ControlSubActivity.this, "切换", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
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
}
