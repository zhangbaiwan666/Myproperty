package cottee.myproperty.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.fragment.GroupBuyFragment;
import cottee.myproperty.fragment.MainFragment;
import cottee.myproperty.fragment.SettingFragment;
import cottee.myproperty.manager.ActivityFinishManager;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private boolean click=true;

    private LayoutInflater layoutInflater;

    private Class fragmentArray[] = {MainFragment.class, GroupBuyFragment.class,SettingFragment.class};

    private int mImageViewArray[] = {R.drawable.selector_tab_home_btn,R.drawable.selector_tab_groupby_btn,R.drawable.selector_tab_setting_btn};


    private String mTextviewArray[] = {"首页","小区团购","我的"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityFinishManager.destoryActivity("destroy");
        initView();
    }


    private void initView(){

        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


        int count = fragmentArray.length;

        for(int i = 0; i < count; i++){

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));

            mTabHost.addTab(tabSpec, fragmentArray[i], null);

            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
        }
    }


    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

        }





