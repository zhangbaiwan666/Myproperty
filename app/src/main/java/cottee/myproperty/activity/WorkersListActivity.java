package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.adapter.WorkersAdapter;
import cottee.myproperty.constant.Mechanic;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WorkersListActivity extends Activity {
    //
    private ListView listView;
    String project_id;
    Handler handler;
    private List<Mechanic.ProjectStaffBean> projectStaffBeans;
    String bigProject;
    String smallProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_list);
        sendRequestOkHttp();
        Bundle bundle=getIntent().getExtras();
        project_id=bundle.getString("id");
        bigProject=bundle.getString("title");
        smallProject=bundle.getString("name");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        initData();

                }
            }
        };

    }
    public  void  initData(){
        listView = (ListView)findViewById(R.id.lv);
        WorkersAdapter workersAdapter=new WorkersAdapter(this,projectStaffBeans);
        listView.setAdapter(workersAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                Intent intent=new Intent(WorkersListActivity.this,WorkersInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("photo",projectStaffBeans.get(i).getPhoto());
                bundle.putString("name",projectStaffBeans.get(i).getName());
                bundle.putString("id",projectStaffBeans.get(i).getId());
                bundle.putString("bigProject",bigProject);
                bundle.putString("smallProject",smallProject);
                bundle.putString("phone",projectStaffBeans.get(i).getPhone());
                bundle.putString("grade",projectStaffBeans.get(i).getGrade());
                bundle.putString("time",projectStaffBeans.get(i).getTime());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public  void  back(View view){
        finish();
    }
    private void sendRequestOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("project_id",project_id).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/application/maintain/staff_show.php").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    private void parseJSONObject(String responseData) {
        Gson gson = new Gson();
        projectStaffBeans = gson.fromJson(responseData,Mechanic.class).getProject_staff();
        Message message = new Message();
        message.what = 0;
        message.obj = projectStaffBeans;
        handler.sendMessage(message);

        System.out.println(projectStaffBeans.get(1).getGrade());
        System.out.println(projectStaffBeans.get(1).getId());
        System.out.println(projectStaffBeans.get(1).getName());
        System.out.println(projectStaffBeans.get(1).getPhone());
        System.out.println(projectStaffBeans.get(1).getPhoto());
        System.out.println(projectStaffBeans.get(1).getTime());

    }

}
