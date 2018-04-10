package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import cottee.myproperty.R;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.Session;

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 500;
    private static String email;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //登录容易跳两遍，写存session方法就好
        new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("user", Context.MODE_PRIVATE |Context.MODE_MULTI_PROCESS);
                email = pref.getString("name", "");
                password = pref.getString("psword", "");
                if (email ==""|| password ==""){
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }else {
                    LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(SplashScreenActivity.this, "", "");
                    LoginRegisterManager loginRegisterManager = new LoginRegisterManager(SplashScreenActivity.this, loginRegisterHandler);
                    loginRegisterManager.ReUserLogin(email.toString().trim(), password.toString().trim());
                    password = pref.getString("psword", "");
                }
            }
        }, SPLASH_TIME_OUT);
    }
    }

