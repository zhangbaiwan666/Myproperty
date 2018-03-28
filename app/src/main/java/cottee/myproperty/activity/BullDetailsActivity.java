package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

/**
 * Created by Administrator on 2018/3/7.
 */

public class BullDetailsActivity extends Activity {
    private Title title = null;
    private boolean click=true;
    private String bullentin_time;
    private String bullentin_title;
    private String bullentin_outline;
    private String bullentin_id;
    private TextView notice_message;
    private TextView notice_time;
    private TextView notice_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        bullentin_time = intent.getStringExtra("bullentin_time");
        bullentin_title = intent.getStringExtra("bullentin_title");
        bullentin_outline = intent.getStringExtra("bullentin_outline");
        bullentin_id = intent.getStringExtra("bullentin_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldetail);
        initTitle();
        initEvent();
    }

    private void initEvent() {
        notice_message = findViewById(R.id.notice_message);
        notice_time = findViewById(R.id.notice_time);
        notice_title = findViewById(R.id.notice_title);
        notice_message.setText(bullentin_outline);
        notice_time.setText(bullentin_time);
        notice_title.setText(bullentin_title);
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr(bullentin_title);
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
