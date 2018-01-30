package cottee.myproperty.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.Repair;
import cottee.myproperty.handler.RepairHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class RepairManager {
    private TextView tv_address;
    private ImageView imv_workerphoto;
    private TextView tv_confirmOfworker;
    private TextView tv_serverProject;
    private EditText et_inputInfo;
    private ImageView imv_takePhoto;
    private Bitmap bitmap;
    private Bundle bundle;

    private String address;
    private String responseData;
    public String photo_url="haha";
    RepairHandler handler;
    Context context;
   public RepairManager (RepairHandler handler){
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
                    parseJSONORepairProject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
    private List<Repair.ProinfoBean> proinfo;
    public void parseJSONORepairProject(String responseData) {
        Gson gson = new Gson();
        proinfo = gson.fromJson(responseData, Repair
                .class).getProinfo();
        for (int i = 0; i < 7; i++) {
            System.out.println(proinfo.get(i).getKind());
            Message message = new Message();
            message.what = Properties.RepairProject;
            message.obj = proinfo;
            handler.sendMessage(message);
        }


    }
//    public void SubmissionToWeb(){
//        new Thread(new Runnable() {
//
//
//
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    RequestBody requestBody = new FormBody.Builder().add
//                            ("session", Session.getSession()).add("staff_name",bundle.getString("name"))
//                            .add("photo_url",photo_url).add("remark",et_inputInfo.getText().toString())
//                            .add("part",bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"))
//                            .add("staff_id",bundle.getString("id")).build();
//                    Request request = new Request.Builder()
//                            .url("https://thethreestooges.cn:5210/maintain/indent/submit").post(requestBody)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    responseData = response.body().string();
//                    if (responseData=="1"){
//                        Toast.makeText(context,"上传成功", LENGTH_SHORT).show();
//                    }
//                    System.out.println("rrrrrrrrrrrrr"+ responseData);
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//
//
//
//        }).start();
//    }
//    public    void sendRequestAddress() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    RequestBody requestBody = new FormBody.Builder().add
//                            ("session", Session.getSession()).build();
//                    Request request = new Request.Builder()
//                            .url("https://thethreestooges.cn:5210/maintain/user/address").post(requestBody)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//                    parseJSONOAddress(responseData);
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//
//
//
//        }).start();
//    }
//    public void parseJSONOAddress(String responseData) throws JSONException {
//        JSONObject jsonObject = new JSONObject(responseData);
//        address = jsonObject.getString("address");
//        System.out.println("-----------"+ address);
//        Message message = new Message();
//        message.what = 1;
//        message.obj = address;
//        handler.sendMessage(message);
//    }
}
