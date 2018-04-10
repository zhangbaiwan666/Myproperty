package cottee.myproperty.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import cottee.myproperty.R;
import cottee.myproperty.fragment.GroupBuyFragment;
import cottee.myproperty.fragment.MainFragment;
import cottee.myproperty.fragment.SettingFragment;
import cottee.myproperty.manager.ActivityFinishManager;
import cottee.myproperty.receiver.NetBroadCastReciver;
import cottee.myproperty.server.ReceiveMsgService;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private boolean click=true;
    public ReceiveMsgService mService = null;
//    private MServiceConnection mServiceConnection =
//            new MServiceConnection();


    private LayoutInflater layoutInflater;

    private Class fragmentArray[] = {MainFragment.class, GroupBuyFragment.class,SettingFragment.class};

    private int mImageViewArray[] = {R.drawable.selector_tab_home_btn,R.drawable.selector_tab_groupby_btn,R.drawable.selector_tab_setting_btn};

    private String mTextviewArray[] = {"首页","小区团购","我的"};
    private BroadcastReceiver receiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityFinishManager.destoryActivity("destroy");
        setBreoadcast();
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
     class MServiceConnection implements ServiceConnection {


         @Override
         public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
             ReceiveMsgService.MyBinder myBinder = (ReceiveMsgService.MyBinder)iBinder;
             //调用公有方法就能获得服务类的实例
             mService = myBinder.getService();

         }

         @Override
         public void onServiceDisconnected(ComponentName componentName) {
             Toast.makeText(mService, "当前网络异常，建议移动至信号良好区域", Toast.LENGTH_SHORT).show();
         }
     }
    private void setBreoadcast() {
        receiver = new NetBroadCastReciver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);//LS:重点！
    }
}





