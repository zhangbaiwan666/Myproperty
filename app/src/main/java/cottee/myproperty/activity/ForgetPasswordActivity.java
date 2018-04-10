package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cottee.myproperty.R;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.Title;

public class ForgetPasswordActivity extends Activity {

    private Title title = null;
    private EditText et_email;
    private EditText et_psword;
    private Button bt_verificationrd;
    private Button bt_register;
    private boolean click=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
        inEvent();
        initTitle();
    }

    private void inEvent() {
        bt_verificationrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(ForgetPasswordActivity.this,"","");
                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(ForgetPasswordActivity.this, loginRegisterHandler);
                loginRegisterManager.CheckForgetedEmail(et_email.getText().toString().trim());
                if(click){
                    click=false;}

            }
        }); bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(ForgetPasswordActivity.this,et_email.getText().toString().trim(),"");
                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(ForgetPasswordActivity.this, loginRegisterHandler);
                System.out.println("email为"+et_email.getText().toString().trim()+"验证码为"+et_psword.getText().toString().trim());
                loginRegisterManager.CheckForgetEmailPsw(et_email.getText().toString().trim(),et_psword.getText().toString().trim());
                if(click){
                    click=false;}
            }
        });

    }

    private  void  init() {
        et_email = (EditText) findViewById(R.id.et_forget_input_email);
        et_psword = (EditText) findViewById(R.id.et_input_Verification);
        bt_verificationrd = (Button) findViewById(R.id.get_Verification);
       bt_register = (Button) findViewById(R.id.btn_forgetr_register);

    }
    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("身份验证");
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


    }