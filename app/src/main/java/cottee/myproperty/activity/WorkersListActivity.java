package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.adapter.WorkersAdapter;
import cottee.myproperty.constant.Mechanic;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.manager.RepairManager;

public class WorkersListActivity extends Activity {
    String project_id;
    Handler handler;
    private List<Mechanic.ProjectStaffBean> projectStaffBeans;
    String bigProject;
    String smallProject;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_list);
        Bundle bundle=getIntent().getExtras();
        project_id=bundle.getString("id");
        bigProject=bundle.getString("title");
        smallProject=bundle.getString("name");
        listView = (ListView)findViewById(R.id.lv_workerList);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Properties.WorkersList:
                        projectStaffBeans= (List<Mechanic.ProjectStaffBean>) msg.obj;
                        initData();

                }
            }
        };
//        RepairHandler repairHandler=new RepairHandler(this,listView,bigProject,smallProject);
        RepairManager repairManager=new RepairManager(handler,project_id);
        repairManager.sendRequestWorkersList();
    }
    public  void  initData(){

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


}
