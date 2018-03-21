package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

public class EmailRegisterActivity extends Activity {
    Button btn_send_code;
    Button iv_emailjudge;
    private EditText et_input_email;
    private Title title = null;
    private boolean click=true;
//    private CheckBox chk_agreement;
    private EditText et_input_verificationCode;
    private TextView tv_agreement;
    private Button btn_register;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);
        init();

    }
    private  void  init(){
        view = (View)findViewById(R.id.view);
        et_input_email = (EditText)findViewById(R.id.et_input_email);
        iv_emailjudge =(Button)findViewById(R.id.btn_emailJudge) ;
//        chk_agreement = (CheckBox)findViewById(R.id.chk_agreement);
        et_input_verificationCode = (EditText)findViewById(R.id.et_input_VerificationCode);
        tv_agreement = (TextView)findViewById(R.id.tv_agreement);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_send_code = (Button) findViewById(R.id.btn_send_code);

        this.btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_input_email.getText().toString().trim())){
                    if (isEmail(et_input_email.getText().toString().trim())){
                        Toast.makeText(EmailRegisterActivity.this,"correct",Toast.LENGTH_SHORT).show();
                         iv_emailjudge.setVisibility(View.VISIBLE);
                          iv_emailjudge.setBackgroundResource(R.drawable.email_correct);
                        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(EmailRegisterActivity.this,et_input_email.getText().toString().trim(),"");
                        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(EmailRegisterActivity.this, loginRegisterHandler);
                        loginRegisterManager.CheckOutEmail(et_input_email.getText().toString().trim());
                        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("name", et_input_email.getText().toString().trim());
                        editor.commit();
                        if(click){
                            click=false;}
//                        if (chk_agreement.isChecked()==true){
//                            et_input_verificationCode.setVisibility(View.VISIBLE);
//                            tv_agreement.setVisibility(View.GONE);
//                            EmailRegisterActivity.this.btn_send_code.setVisibility(View.GONE);
//                            btn_register.setVisibility(View.VISIBLE);
//                            view.setVisibility(View.VISIBLE);
//                        }else {
//                            Toast.makeText(EmailRegisterActivity.this,"请勾选协议",Toast.LENGTH_SHORT);
//                        }
                    }
                    else {
                        Toast.makeText(EmailRegisterActivity.this,"输入的邮箱格式不正确",Toast.LENGTH_SHORT).show();
                        iv_emailjudge.setVisibility(View.VISIBLE);
                         iv_emailjudge.setBackgroundResource(R.drawable.email_error);
                    }
                }
                else {
                    Toast.makeText(EmailRegisterActivity.this,"邮箱账号不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(EmailRegisterActivity.this,et_input_email.getText().toString().trim(),"");
                                                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(EmailRegisterActivity.this, loginRegisterHandler);
                                                loginRegisterManager.CheckOutEmailFirPsw(et_input_email.getText().toString().trim(),et_input_verificationCode.getText().toString().trim());
                                            }
                                        }
        );

    }
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
    public void back(View view){
        finish();
    }
    public void submitCode(View view){
        Intent intent=new Intent(EmailRegisterActivity.this,SetPasswordActivity.class);
        startActivity(intent);
    }
}
