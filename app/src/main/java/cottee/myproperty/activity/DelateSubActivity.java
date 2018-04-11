package cottee.myproperty.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.adapter.SubPhoneAdapter;
import cottee.myproperty.constant.SubPhoneBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.Session;
import cottee.myproperty.widgets.EditextListAdapter;
import cottee.myproperty.constant.ItemBean;
import cottee.myproperty.widgets.Title;

public class DelateSubActivity extends Activity {

    private Title title;
    private boolean click=true;
    private TextView tv_sub_id;
    private ListView list_sub_phone;
    private TextView tv_sub_remark;
    private TextView tv_house_name;
    private LinearLayout ll_show_sub_info;
    private LinearLayout ll_modify_sub_info;
    private EditText et_sub_remark;
    private EditText et_sub_id;
    private EditText et_sub_phone;
    private Button btn_delete_sub;
    private Button btn_modify_sub;
    private Button btn_sub_phone_add;
    private ListView mListView;
    private Button mButton;
    private List<ItemBean> mData;
    private EditextListAdapter mAdapter;
    private String id;
    private String house_name;
    private List<String> list_phone;
    private String remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delate_sub);
        initTitle();
        init();
        initEven();

    }
    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("家庭成员管理");
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_RIGHT1,R.mipmap.sub_info_modify,null));
    }
    private List<SubPhoneBean> initsubphonelist() {
        List<SubPhoneBean> bullentinlist=new ArrayList<SubPhoneBean>();
        return bullentinlist;
    }

    @SuppressLint("ResourceType")
    private void initEven() {
        final Intent intent = getIntent();
        remark = intent.getStringExtra("remark");
        list_phone = (List<String>) getIntent().getSerializableExtra("phone");
        id = intent.getStringExtra("id");
        house_name = intent.getStringExtra("house_name");
        System.out.println("DelateSubActivity得备注" + remark);
        System.out.println("DelateSubActivity得phone" + list_phone);
        System.out.println("DelateSubActivity得id" + id);
        mListView = (ListView) findViewById(R.id.list_view);
        mButton = (Button) findViewById(R.id.button);
        tv_sub_id.setText(id);
        tv_house_name.setText(house_name);
        List<SubPhoneBean> bullentinlist = initsubphonelist();
        for (int i=0;i<list_phone.size();i++){
            SubPhoneBean subPhoneBean = new SubPhoneBean();
            subPhoneBean.setPhone(list_phone.get(i));
            bullentinlist.add(subPhoneBean);
        }
        SubPhoneAdapter previewBulletinAdapter = new SubPhoneAdapter(this, R.layout.layout_item_sub_phone,bullentinlist);
        list_sub_phone.setAdapter(previewBulletinAdapter);
        previewBulletinAdapter.notifyDataSetChanged();
        tv_sub_remark.setText(remark);
        et_sub_id.setText(id);
//        et_sub_phone.setText(phone);
        et_sub_remark.setText(remark);


//        String sub_phones;
//        for (int i=0;i<=mData.size();i++){
//            String phone = mData.get(i).getText();
//            sub_phones=phone+"#";
//        }
        mData = new ArrayList<ItemBean>();
        for (int i=0;i<list_phone.size();i++){
            ItemBean itemBean = new ItemBean();
            itemBean.setText(list_phone.get(i));
            mData.add(itemBean);
        }
//取每个item得值并进行整合成字符串
        mAdapter = new EditextListAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mData.size()<3){
                    mData.add(new ItemBean());
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(DelateSubActivity.this, "最多只能添加三个电话号码哦", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_modify_sub.setOnClickListener(new View.OnClickListener() {
            //todo 判断电话的位数是否正确，将电话整合成字符串进行提交
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DelateSubActivity.this)
                        .setMessage("确定修改吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String result = "";
                                for (int i=0;i < mListView.getChildCount(); i++) {
                                    LinearLayout childAt = (LinearLayout) mListView.getChildAt(i);
                                    TextView viewById = (TextView)childAt.findViewById(R.id.edit_text);
                                    String trim = viewById.getText().toString().trim();
                                    result=result+trim+",";
                                }

                                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(DelateSubActivity.this, "", "");
                                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(DelateSubActivity.this, loginRegisterHandler);
                                loginRegisterManager.UpdateSubAccount(et_sub_id.getText().toString().trim(),
                                        et_sub_remark.getText().toString().trim(),result);
                                System.out.println("你张繁爸爸想看你得输出结果"+et_sub_id.getText().toString().trim());
                                System.out.println("你张繁爸爸想看你得输出结果"+et_sub_remark.getText().toString().trim());
                                System.out.println("你张繁爸爸想看你得输出结果"+result);
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
        btn_delete_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DelateSubActivity.this)
                        .setMessage("确定删除该子成员吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity) DelateSubActivity.this).finish();
                                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(DelateSubActivity.this, "", "");
                                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(DelateSubActivity.this, loginRegisterHandler);
                                String session = Session.getSession();
                                loginRegisterManager.DeleteSubAccount(id.toString().trim(),session);
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

    private void init() {

        tv_sub_id = (TextView)findViewById(R.id.tv_sub_email);
        list_sub_phone = (ListView)findViewById(R.id.list_sub_phone);
        tv_sub_remark = (TextView)findViewById(R.id.tv_sub_remark);
        ll_show_sub_info = (LinearLayout) findViewById(R.id.ll_show_sub_info);
        ll_modify_sub_info = (LinearLayout) findViewById(R.id.ll_modify_sub_info);
        et_sub_remark = (EditText) findViewById(R.id.et_sub_remark);
        et_sub_id = (EditText) findViewById(R.id.et_sub_id);
        btn_delete_sub = (Button) findViewById(R.id.btn_delete_sub);
        btn_modify_sub = (Button) findViewById(R.id.btn_modify_sub);
        et_sub_phone = (EditText)findViewById(R.id.edit_text);
        tv_house_name = (TextView)findViewById(R.id.tv_house_name);
    }

    private Title.OnTitleButtonClickListener onTitleButtonClickListener = new Title.OnTitleButtonClickListener() {
        @Override
        public void onClick(int id, Title.ButtonViewHolder viewHolder) {
            if(click){
                click=false;}
            if (id == Title.BUTTON_LEFT){
                if (btn_modify_sub.getVisibility()==View.VISIBLE){
                    new AlertDialog.Builder(DelateSubActivity.this)
                            .setMessage("要放弃更改吗")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
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
                else{
                    finish();
                }
            }else if (id == Title.BUTTON_RIGHT1) {
                ll_show_sub_info.setVisibility(View.GONE);
                ll_modify_sub_info.setVisibility(View.VISIBLE);
                btn_delete_sub.setVisibility(View.GONE);
                btn_modify_sub.setVisibility(View.VISIBLE);


            }
        }
    };
}
