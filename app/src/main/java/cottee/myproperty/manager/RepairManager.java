package cottee.myproperty.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import cottee.myproperty.constant.Mechanic;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.RepairProject;
import cottee.myproperty.constant.RepairRecord;
import cottee.myproperty.handler.RepairHandler;
import cottee.myproperty.handler.RepairRecordHandler;
import cottee.myproperty.uitils.Session;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class RepairManager {
    private TextView tv_address;

    RepairHandler repairProjectHandler;
    Context context;
    private List<Mechanic.ProjectStaffBean> projectStaffBeans;
    private String project_id;
    RepairRecordHandler repairRecordHandler;
    ListView lv_RepairRecord;
    private Message message;

    public RepairManager (RepairHandler repairProjectHandler){
       this.repairProjectHandler = repairProjectHandler;
   }
   public  RepairManager (RepairRecordHandler repairRecordHandler,ListView lv_RepairRecord){
       this.repairRecordHandler=repairRecordHandler;
       this.lv_RepairRecord=lv_RepairRecord;

   }
   Handler handler;
   public RepairManager(Handler handler,String project_id){
       this.project_id=project_id;
       this.handler=handler;
   }
    public void sendRequestRepairProject() {
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
                    parseJSONRepairProject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
    private List<RepairProject.ProinfoBean> proinfo;
    public void parseJSONRepairProject(String responseData) {
        Gson gson = new Gson();
        proinfo = gson.fromJson(responseData, RepairProject
                .class).getProinfo();
        for (int i = 0; i < 7; i++) {
            System.out.println(proinfo.get(i).getKind());
            message = new Message();
            message.what = Properties.RepairProject;
            message.obj = proinfo;
            repairProjectHandler.sendMessage(message);
        }


    }
    public void sendRequestWorkersList() {
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
                   parseJSONWorkersList(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    private void parseJSONWorkersList(String responseData) {
        Gson gson = new Gson();
        projectStaffBeans = gson.fromJson(responseData,Mechanic.class).getProject_staff();
        Message message = new Message();
        message.what = Properties.WorkersList;
        message.obj = projectStaffBeans;
        handler.sendMessage(message);



    }
    public  void sendRequestRepairRecord() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession()).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/indent/list").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                   parseJSONRepairRecord(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
    public void parseJSONRepairRecord(String responseData) {
        Gson gson = new Gson();
        List<RepairRecord.ListBean> listBeans=gson.fromJson(responseData,RepairRecord.class).getList();
        System.out.println(listBeans.get(0).getOrder_id()+listBeans.get(0).getC_time());
        Message message = new Message();
        message.what = Properties.RepairRecord;
        message.obj = listBeans;
        repairRecordHandler.sendMessage(message);
    }
}
