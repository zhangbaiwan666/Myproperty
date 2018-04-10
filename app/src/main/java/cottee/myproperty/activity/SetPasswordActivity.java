package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

public class SetPasswordActivity extends Activity {
    private Title title = null;
    private Button btn_register_password;
    private EditText et_set_password;
    private EditText et_set_second_password;
    private boolean click=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        init();
        inEven();
        initTitle();
    }

    private void inEven() {
        if (et_set_password.length()>10){
            Toast.makeText(this, "最多输入10个字符", Toast.LENGTH_SHORT).show();
        }

        btn_register_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(SetPasswordActivity.this,email,"");
                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(SetPasswordActivity.this, loginRegisterHandler);
                loginRegisterManager.RegestUser(email,et_set_password.getText().toString().trim());
                SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("psword", et_set_password.getText().toString().trim());
                editor.commit();}


        });

    }

    private void init() {
        btn_register_password = (Button) findViewById(R.id.btn_register_password);
        et_set_password = (EditText) findViewById(R.id.et_set_password);
        et_set_second_password = (EditText) findViewById(R.id.et_set_second_password);
    }

    public  void  back(View view){
        finish();
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("设置密码");
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
