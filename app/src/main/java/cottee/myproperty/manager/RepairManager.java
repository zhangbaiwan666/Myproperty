package cottee.myproperty.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import cottee.myproperty.constant.Mechanic;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.Repair;
import cottee.myproperty.handler.RepairHandler;
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

    RepairHandler repairHandler;
    Context context;
    private List<Mechanic.ProjectStaffBean> projectStaffBeans;
    private String project_id;
   public RepairManager (RepairHandler repairHandler){
       this.repairHandler=repairHandler;
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
    private List<Repair.ProinfoBean> proinfo;
    public void parseJSONRepairProject(String responseData) {
        Gson gson = new Gson();
        proinfo = gson.fromJson(responseData, Repair
                .class).getProinfo();
        for (int i = 0; i < 7; i++) {
            System.out.println(proinfo.get(i).getKind());
            Message message = new Message();
            message.what = Properties.RepairProject;
            message.obj = proinfo;
            repairHandler.sendMessage(message);
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

}
