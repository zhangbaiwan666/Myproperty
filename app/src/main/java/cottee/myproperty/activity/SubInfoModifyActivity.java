package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;

import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

public class SubInfoModifyActivity extends Activity {
    private Title title;
    private boolean click=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_info_modify);
        initTitle();
    }
    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("资料设置");
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_RIGHT2,0,"完成"));

    } private Title.OnTitleButtonClickListener onTitleButtonClickListener = new Title.OnTitleButtonClickListener() {
        @Override
        public void onClick(int id, Title.ButtonViewHolder viewHolder) {
            if (id == Title.BUTTON_LEFT){
                finish();
                if(click){
                    click=false;}
            }else if (id == Title.BUTTON_RIGHT1){
                finish();
            }
        }
    };

}
