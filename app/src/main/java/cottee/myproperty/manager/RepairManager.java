package cottee.myproperty.manager;

import android.os.Message;

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
    RepairHandler handler;
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
}
