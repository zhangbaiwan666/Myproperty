package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;

import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

public class AddServerActivity extends Activity {
    private Title title = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);
        initTitle();
    }
    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("增值服务");
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
    }
    private Title.OnTitleButtonClickListener onTitleButtonClickListener = new Title.OnTitleButtonClickListener() {
        @Override
        public void onClick(int id, Title.ButtonViewHolder viewHolder) {
            if (id == Title.BUTTON_LEFT){
                finish();

            }else if (id == Title.BUTTON_RIGHT1){
            }
        }
    };
}
