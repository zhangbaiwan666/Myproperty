package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import cottee.myproperty.R;
import cottee.myproperty.adapter.ChooseHouseAdapter;
import cottee.myproperty.constant.HouseListBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.Title;

public class RepairChooeseHouseActivity extends Activity {
    private Title title;
    private ListView lv_show_house;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_chooese_house);
        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(this, "", "");
        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(this, loginRegisterHandler);
        loginRegisterManager.ViewAllHouse();
        initTitle();
        initView();
        initEvent();
    }
    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("我的房屋");
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
    }

    private void initEvent() {
        ChooseHouseAdapter chooseHouseAdapter = new ChooseHouseAdapter(this, R.layout.pop_menuitem, null);
//        lv_show_house.setAdapter(chooseHouseAdapter);
        chooseHouseAdapter.notifyDataSetChanged();
        lv_show_house.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(RepairChooeseHouseActivity.this, "", "");
                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(RepairChooeseHouseActivity.this, loginRegisterHandler);
                HouseListBean item = (HouseListBean) lv_show_house.getAdapter().getItem(i);
                loginRegisterManager.ChooseHouseForRepair(item.getHome_id());
            }
        });
    }

    private void initView() {
        ProgressBar pb_listview =findViewById(R.id.pb_listview);
        lv_show_house = findViewById(R.id.lv_show_house);
    }

    private Title.OnTitleButtonClickListener onTitleButtonClickListener = new Title.OnTitleButtonClickListener() {
        @Override
        public void onClick(int id, Title.ButtonViewHolder viewHolder) {
            if (id == Title.BUTTON_LEFT){
                finish();
            }
        }
    };
}
