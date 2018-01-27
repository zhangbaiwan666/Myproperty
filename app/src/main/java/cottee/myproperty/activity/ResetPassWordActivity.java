package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cottee.myproperty.R;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.Title;

public class ResetPassWordActivity extends Activity {
    private Title title = null;
    private EditText input_password;
    private Button set_password;
    private boolean click=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        init();
        inEvent();

    }

    private void inEvent() {
        set_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("forgetmail");
                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(ResetPassWordActivity.this,email,"");
                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(ResetPassWordActivity.this, loginRegisterHandler);
                loginRegisterManager.ResetPassWord(email,input_password.getText().toString().trim());
                SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("psword", input_password.getText().toString().trim());
                editor.commit();
                if(click){
                    click=false;}
            }
        });
    }

    private void init() {
        input_password = (EditText) findViewById(R.id.et_set_password);
        set_password = (Button) findViewById(R.id.btn_register_password);
    }
}
