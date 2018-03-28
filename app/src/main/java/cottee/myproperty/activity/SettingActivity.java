package cottee.myproperty.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import cottee.myproperty.R;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.widgets.Title;

public class SettingActivity extends Activity {
    private Title title = null;
    private boolean click=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initTitle();
        init();
    }

    private void init() {
        View ll_login_out = findViewById(R.id.ll_login_out);
        View ll_new_message = findViewById(R.id.ll_new_message);
        ll_login_out.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setMessage("确定要退出吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences=SettingActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                                String email=preferences.getString("name", "");
                                String password=preferences.getString("psword", "");
                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                intent.putExtra("name",email);
                                intent.putExtra("psword",password);
                                startActivity(intent);
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
        ll_new_message.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(SettingActivity.this, PersonNoticeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("设置");
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
