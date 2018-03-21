package cottee.myproperty.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cottee.myproperty.constant.Mechanic;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.RepairProject;
import cottee.myproperty.constant.RepairRecord;
import cottee.myproperty.constant.RepairTrack;
import cottee.myproperty.handler.RepairHandler;
import cottee.myproperty.handler.RepairRecordHandler;
import cottee.myproperty.handler.RepairTrackHandler;
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
     private  Handler repairAddresshandler;
    Handler handler;
    String order_id;
    RepairTrackHandler repairTrackhandler;
    public RepairManager(RepairTrackHandler repairTrackhandler,String order_id){
        this.repairTrackhandler=repairTrackhandler;
        this.order_id=order_id;
    }
    public RepairManager (RepairHandler repairProjectHandler){
       this.repairProjectHandler = repairProjectHandler;
   }
   public  RepairManager (RepairRecordHandler repairRecordHandler,ListView lv_RepairRecord){
       this.repairRecordHandler=repairRecordHandler;
       this.lv_RepairRecord=lv_RepairRecord;

   }
   public  RepairManager(Handler repairAddresshandler){
       this.repairAddresshandler=repairAddresshandler;
   }

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
    public static void  SubmissionToWeb(final String photo_url, final String remark, final String part, final String staff_id
    , final String staff_name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session",Session.getSession()).add("staff_name",staff_name)
                            .add("photo_url",photo_url).add("remark",remark)
                            .add("part",part)
                            .add("staff_id",staff_id).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/indent/submit").post(requestBody)
                            .build();
                    System.out.println("staff_id"+staff_id);
                    Response response = client.newCall(request).execute();
                 String   responseData = response.body().string();

                    System.out.println("rrrrrrrrrrrrr"+ responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
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
    public  void sendRequestRepairTrack(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(order_id);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession()).add("order_id",order_id).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/indent/show").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println(responseData);
                    parseJSONRepairTrack(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
    public  void parseJSONRepairTrack(String responseData) {
        Gson gson = new Gson();
        Message message = new Message();
        message.what = Properties.RepairTrack;
        message.obj = gson.fromJson(responseData,RepairTrack.class).getIndent();
        repairTrackhandler.sendMessage(message);

    }
    public    void sendRequestRepairAddress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession()).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/user/address").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("AAAAAAAAA"+responseData);
                    parseJSONRepairAddress(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }
    private void parseJSONRepairAddress(String responseData) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseData);
      String  address = jsonObject.getString("address");
        Message message = new Message();
        message.what = Properties.RepairAddress;
        message.obj = address;
        repairAddresshandler.sendMessage(message);
    }
    String RepairFee;
    public    void sendRequestRepairPayment() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession())
                            .add("repairFee",RepairFee).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/user/repairFee").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("AAAAAAAAA"+responseData);
                    parseJSONRepairAddress(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }
}
