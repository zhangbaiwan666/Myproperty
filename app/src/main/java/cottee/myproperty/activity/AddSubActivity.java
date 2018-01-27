package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cottee.myproperty.R;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.Title;

public class AddSubActivity extends Activity {
    private Title title = null;
    private EditText et_sub_account,et_sub_remark,et_sub_phone;
    private Button bt_add_account;
    private boolean click=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
        init();
        inEvent();
        initTitle();

    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("添加家庭成员");
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
            }else if (id == Title.BUTTON_RIGHT1){
            }
        }
    };

    private void inEvent() {

        bt_add_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isEmail(et_sub_account.getText().toString().trim())) {
                final Intent intent = getIntent();
                int home_id = intent.getIntExtra("home_id", 0);
                int pro_id = intent.getIntExtra("pro_id", 0);
                    System.out.println("AddSubActivity的home_id"+home_id);
                    System.out.println("AddSubActivity的pro_id"+pro_id);
                    LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(AddSubActivity.this, "", "");
                    LoginRegisterManager loginRegisterManager = new LoginRegisterManager(AddSubActivity.this, loginRegisterHandler);
                    loginRegisterManager.AddSubAccount(et_sub_account.getText().toString().trim(), et_sub_remark.getText().toString().trim(), et_sub_phone.getText().toString().trim());

                    //
// }else {
                        Toast.makeText(AddSubActivity.this, "输入子账户信息有误", Toast.LENGTH_SHORT).show();
//                    }
                if(click){
                    click=false;}
            }
        });
    }

    private void init() {
        et_sub_account = (EditText) findViewById(R.id.et_sub_account);
        et_sub_remark = (EditText) findViewById(R.id.et_sub_remark);
        et_sub_phone = (EditText) findViewById(R.id.et_sub_phone);
        bt_add_account = (Button) findViewById(R.id.bt_add_account);
    }
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

}

