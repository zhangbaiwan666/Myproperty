package cottee.myproperty.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.Session;
import cottee.myproperty.widgets.EditextListAdapter;
import cottee.myproperty.widgets.ItemBean;
import cottee.myproperty.widgets.Title;

public class DelateSubActivity extends Activity {

    private Title title;
    private boolean click=true;
    private TextView tv_sub_id;
    private TextView tv_sub_phone;
    private TextView tv_sub_remark;
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
    private String phone;
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

    private void initEven() {
        final Intent intent = getIntent();
        remark = intent.getStringExtra("remark");
        phone = intent.getStringExtra("phone");
        id = intent.getStringExtra("id");
        System.out.println("DelateSubActivity得备注" + remark);
        System.out.println("DelateSubActivity得phone" + phone);
        System.out.println("DelateSubActivity得id" + id);
        mListView = (ListView) findViewById(R.id.list_view);
        mButton = (Button) findViewById(R.id.button);
        tv_sub_id.setText(id);
        tv_sub_phone.setText(phone);
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
        ItemBean itemBean = new ItemBean();
        itemBean.setText(phone);
        mData.add(itemBean);

        mAdapter = new EditextListAdapter(this, mData);
        mListView.setAdapter(mAdapter);

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
            @Override
            public void onClick(View view) {
//                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(DelateSubActivity.this, "", "");
//                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(DelateSubActivity.this, loginRegisterHandler);
//                String session = Session.getSession();
//                loginRegisterManager.UpdateSubAccount(
//                        et_sub_id.getText().toString().trim(),
//                        session,
//                        et_sub_remark.getText().toString().trim(),
//                        );
                new AlertDialog.Builder(DelateSubActivity.this)
                        .setMessage("确定修改吗")
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
                                finish();
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
        tv_sub_phone = (TextView)findViewById(R.id.tv_sub_phone);
        tv_sub_remark = (TextView)findViewById(R.id.tv_sub_remark);
        ll_show_sub_info = (LinearLayout) findViewById(R.id.ll_show_sub_info);
        ll_modify_sub_info = (LinearLayout) findViewById(R.id.ll_modify_sub_info);
        et_sub_remark = (EditText) findViewById(R.id.et_sub_remark);
        et_sub_id = (EditText) findViewById(R.id.et_sub_id);
        btn_delete_sub = (Button) findViewById(R.id.btn_delete_sub);
        btn_modify_sub = (Button) findViewById(R.id.btn_modify_sub);
        et_sub_phone = (EditText)findViewById(R.id.edit_text);
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
