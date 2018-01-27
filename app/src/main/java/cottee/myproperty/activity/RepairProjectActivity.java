package cottee.myproperty.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.adapter.LeftAdapter;
import cottee.myproperty.adapter.RightAdapter;
import cottee.myproperty.constant.BaseData;
import cottee.myproperty.constant.Repair;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RepairProjectActivity extends Activity {
    private ListView lv_left;
    private ListView lv_Right;
    private TextView tv_title;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private Context context;
    private ArrayList<BaseData> lists;
    private ArrayList<String> showTitle;
    private int lastPosition;
    private List<Repair.ProinfoBean> proinfo;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_project);

        lv_left=(ListView)findViewById(R.id.lv_left) ;
        lv_Right=(ListView)findViewById(R.id.lv_Right) ;
        tv_title=(TextView)findViewById(R.id.tv_title) ;
//        LoginRegisterManager loginRegisterManager=new LoginRegisterManager(handler);
//        loginRegisterManager.sendRequestOkHttp();
        sendRequestOkHttp();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        initData();
                        initView();
                }
            }
        };

    }
    private void initData() {
        lists = new ArrayList<BaseData>();

        for (int i=0;i<proinfo.get(0).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(0).getDetailed().get(i).getName(),i,proinfo.get(0).getKind(),proinfo.get(0).getDetailed().get(i).getUrl(),proinfo.get(0).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(1).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(1).getDetailed().get(i).getName(),i,proinfo.get(1).getKind(),proinfo.get(1).getDetailed().get(i).getUrl(),proinfo.get(1).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(2).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(2).getDetailed().get(i).getName(),i,proinfo.get(2).getKind(),proinfo.get(2).getDetailed().get(i).getUrl(),proinfo.get(2).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(3).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(3).getDetailed().get(i).getName(),i,proinfo.get(3).getKind(),proinfo.get(3).getDetailed().get(i).getUrl(),proinfo.get(3).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(4).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(4).getDetailed().get(i).getName(),i,proinfo.get(4).getKind(),proinfo.get(4).getDetailed().get(i).getUrl(),proinfo.get(4).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(5).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(5).getDetailed().get(i).getName(),i,proinfo.get(5).getKind(),proinfo.get(5).getDetailed().get(i).getUrl(),proinfo.get(5).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(6).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(6).getDetailed().get(i).getName(),i,proinfo.get(6).getKind(),proinfo.get(6).getDetailed().get(i).getUrl(),proinfo.get(6).getDetailed().get(i).getId()));
        }



        showTitle = new ArrayList<String>();
        for (int i = 0; i < lists.size(); i++) {
            if (i == 0) {
                showTitle.add(i + "");
            } else if (!TextUtils.equals(lists.get(i).getTitle(),
                    lists.get(i - 1).getTitle())) {
                showTitle.add(i + "");
            }
        }
    }

    private void initView() {
        leftAdapter = new LeftAdapter(this,proinfo);
        lv_left.setAdapter(leftAdapter);

        rightAdapter = new RightAdapter(this,proinfo);
        lv_Right.setAdapter(rightAdapter);
        rightAdapter.updateData(lists);

        tv_title.setText(lists.get(0).getTitle());

        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                System.out.println("arg2=" + arg2);
                int firstVisibleItem = lv_Right.getFirstVisiblePosition();
                updateLeftListview(firstVisibleItem, arg2);
                lv_Right.setSelection(Integer.parseInt(showTitle.get(arg2)));

            }
        });
        lv_Right.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long l) {

                Intent intent= new Intent(RepairProjectActivity.this,WorkersListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",lists.get(position).getId());
                bundle.putString("title",lists.get(position).getTitle());
                bundle.putString("name",lists.get(position).getName());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        lv_Right.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int currentPosition = showTitle.indexOf(firstVisibleItem + "");
                updateLeftListview(firstVisibleItem, currentPosition);

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateLeftListview(int firstVisibleItem, int currentPosition) {
        if (showTitle.contains(firstVisibleItem + "")) {
            tv_title.setText(lists.get(firstVisibleItem).getTitle());
            TextView lasTextView = (TextView) lv_left
                    .findViewWithTag(lastPosition);
            if (lasTextView != null) {
                lasTextView.setTextColor(Color.BLACK);
                lasTextView.setBackgroundColor(Color.TRANSPARENT);
            }
            TextView currenTextView = (TextView) lv_left
                    .findViewWithTag(currentPosition);
            if (currenTextView != null) {
                currenTextView.setTextColor(Color.argb(255,255,97,0));
                currenTextView.setBackgroundColor(Color.rgb(255, 255, 255));



            }
            lastPosition = currentPosition;

        }
    }
    public void sendRequestOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("http://120.25.96.141/temp/file/project_static.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println(response);
                    parseJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    public void parseJSONObject(String responseData) {
        Gson gson = new Gson();
        proinfo = gson.fromJson(responseData, Repair
                .class).getProinfo();
        for (int i = 0; i < 7; i++) {
            System.out.println(proinfo.get(i).getKind());
            Message message = new Message();
            message.what = 0;
            message.obj = proinfo.get(i).getKind();
            handler.sendMessage(message);
        }


    }
}
