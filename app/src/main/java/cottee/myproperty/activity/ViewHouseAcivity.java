package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;


import cottee.myproperty.R;
import cottee.myproperty.adapter.ChooseHouseAdapter;
import cottee.myproperty.adapter.ViewHouseApater;
import cottee.myproperty.constant.HouseListBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.Title;

public class ViewHouseAcivity extends Activity {
    private Title title = null;
    private ListView lv_show_house;
    private ProgressBar pb_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_house_acivity);
        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(this, "", "");
        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(this, loginRegisterHandler);
        loginRegisterManager.ViewAllHouse();
        initTitle();
        initEvent();

    }

    private void initEvent() {
        List<HouseListBean> listBeans=null;
        lv_show_house = (ListView)findViewById(R.id.lv_show_house);
//        ChooseHouseAdapter houseListAdapter = new ChooseHouseAdapter(
//                this, R.layout.pop_menuitem, null);
//        lv_show_house.setAdapter(houseListAdapter);
//        houseListAdapter.notifyDataSetChanged();
        lv_show_house.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewHouseAcivity.this, PaymentActivity.class);
                HouseListBean item = (HouseListBean) lv_show_house.getAdapter().getItem(i);
                intent.putExtra("house_id",item.getHome_id());
                intent.putExtra("house_access",item.getAddress());
                startActivity(intent);
            }
        });
        if(lv_show_house==null){
            pb_listview.setVisibility(View.VISIBLE);
        }
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("我的房屋");
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
