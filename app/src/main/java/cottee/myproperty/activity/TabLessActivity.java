package cottee.myproperty.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.fragment.RecentBulletinFragment;
import cottee.myproperty.fragment.PastBulletinFragment;
import cottee.myproperty.fragment.SearchBulletionFragment;
import cottee.myproperty.widgets.Title;

/**
 * Created by zq on 2017/1/12.
 */

public class TabLessActivity extends Activity implements View.OnClickListener {
    private Button  tvBtn, varietyBtn ;
    private TextView btn_bulletin_search;
    private List<Button> btnList = new ArrayList<Button>();
    private android.app.FragmentManager fm;
    private FragmentTransaction ft;
    private Title title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        findById();
        initTitle();
        // 進入系統默認為movie
        fm = getFragmentManager();
        ft = fm.beginTransaction();

        setBackgroundColorById(R.id.tv_btn);
        ft.replace(R.id.fragment_content, new RecentBulletinFragment());
        ft.commit();
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("公告");
        title.setOnTitleButtonClickListener(onTitleButtonClickListener);
        title.mSetButtonInfo(new Title.ButtonInfo(true,Title.BUTTON_LEFT,R.drawable.img_back,null));
    }

    private Title.OnTitleButtonClickListener onTitleButtonClickListener = new Title.OnTitleButtonClickListener() {
        @Override
        public void onClick(int id, Title.ButtonViewHolder viewHolder) {
            if (id == Title.BUTTON_LEFT){
                finish();
            }
        }
    };
    private void findById() {
        tvBtn = (Button) this.findViewById(R.id.tv_btn);
        varietyBtn = (Button) this.findViewById(R.id.variety_btn);
        btn_bulletin_search = (TextView) this.findViewById(R.id.btn_bulletin_search);

        tvBtn.setOnClickListener(this);
        varietyBtn.setOnClickListener(this);
        btn_bulletin_search.setOnClickListener(this);

        btnList.add(tvBtn);
        btnList.add(varietyBtn);
    }
    @SuppressLint("ResourceAsColor")
    private void setBackgroundColorById(int btnId) {
        for (Button btn : btnList) {
            //用background改切图
            if (btn.getId() == btnId) {
                btn.setBackgroundColor(Color.rgb(252, 135, 68));
            } else {
                btn.setBackgroundColor(Color.rgb(200, 200, 200));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        switch (v.getId()) {

            case R.id.tv_btn:
                setBackgroundColorById(R.id.tv_btn);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btn_bulletin_search.getWindowToken(), 0);
                ft.replace(R.id.fragment_content, new RecentBulletinFragment());
                break;


            case R.id.variety_btn:
                setBackgroundColorById(R.id.variety_btn);
                InputMethodManager imm1 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(btn_bulletin_search.getWindowToken(), 0);
                ft.replace(R.id.fragment_content, new PastBulletinFragment());
                break;

            case R.id.btn_bulletin_search:
                InputMethodManager imm2 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(btn_bulletin_search.getWindowToken(), 0);
                ft.replace(R.id.fragment_content,new SearchBulletionFragment());
                break;
            default:
                break;
        }
        // 不要忘记提交
        ft.commit();
    }


}
