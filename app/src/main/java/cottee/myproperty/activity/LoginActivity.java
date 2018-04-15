package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.manager.ActivityFinishManager;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.R;

public class LoginActivity extends Activity {
    //

    private TextView tv_emailRegister;
    private TextView tv_passwordForget;
    private Button bt_login;
    private TextView et_user_email;
    private TextView et_user_password;
    private String email;
    private String password;
    private SharedPreferences preferences;
    private boolean click=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        email = preferences.getString("name", "");
        password = preferences.getString("psword", "");
        init();
        inEvent();
        ActivityFinishManager.addDestoryActivity(this,"destroy");

    }

    private void inEvent() {
        et_user_email.setText(email);
        et_user_password.setText(password);
        preferences.edit().clear().commit();
        tv_emailRegister.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tv_passwordForget.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tv_emailRegister.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent=new Intent(LoginActivity.this,EmailRegisterActivity.class);
                startActivity(intent);
                if(click){
                    click=false;}
            }
            });

        bt_login.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                //提交
                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(LoginActivity.this,
                        et_user_email.getText().toString().trim(),et_user_password.getText().toString().trim());
                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(LoginActivity.this, loginRegisterHandler);
                loginRegisterManager.UserLogin(et_user_email.getText().toString().trim(),et_user_password.getText().toString().trim());
                if(click){
                    click=false;}
            }
        });
        tv_passwordForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                if(click){
                    click=false;}
            }
        });
    }

    private void init() {
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_emailRegister = (TextView)findViewById(R.id.tv_emailRegister);
        tv_passwordForget = (TextView)findViewById(R.id.tv_passwordForget);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        et_user_email = (EditText) findViewById(R.id.et_user_email);

    }

    public void forgetPassword(View view){
         Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
         startActivity(intent);
    }
}
