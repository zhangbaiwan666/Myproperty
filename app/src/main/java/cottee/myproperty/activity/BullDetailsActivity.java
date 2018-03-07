package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;

import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

/**
 * Created by Administrator on 2018/3/7.
 */

public class BullDetailsActivity extends Activity {
    private Title title = null;
    private boolean click=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldetail);
        initTitle();
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("公告详情");
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
