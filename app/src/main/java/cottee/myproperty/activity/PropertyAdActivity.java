package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cottee.myproperty.R;
import cottee.myproperty.widgets.Title;

/**
 * Created by Administrator on 2018/3/7.
 */

public class PropertyAdActivity extends Activity{
    private int imageIds[];
    private int ad_numb;
    private Title title = null;
    private ImageView img_property_ad;
    private boolean click=true;
    private String ad_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertyad);
        Intent intent = getIntent();
        ad_numb = intent.getIntExtra("Ad_numb", 0);
        ad_name = intent.getStringExtra("Ad_name");
        imageIds = new int[]{
                R.mipmap.ad5,
                R.mipmap.ad2,
                R.mipmap.ad3,

                R.mipmap.ad4,
                R.mipmap.splash,
                R.mipmap.ad3,
                R.mipmap.ad5,
                R.mipmap.ad2
        };
        init();
        initTitle();
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr(ad_name);
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
    }

    private void init() {
        img_property_ad = findViewById(R.id.img_property_ad);
        img_property_ad.setBackgroundResource(imageIds[ad_numb]);

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
