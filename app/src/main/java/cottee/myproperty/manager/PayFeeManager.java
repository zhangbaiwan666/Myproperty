package cottee.myproperty.manager;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cottee.myproperty.constant.PayDetailInfo;
import cottee.myproperty.constant.PayFeeRecord;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.handler.PayFeeDetailHandler;
import cottee.myproperty.handler.PayFeeHandler;
import cottee.myproperty.uitils.Session;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/24 0024.
 */

public class PayFeeManager {
    String type;
    String address;
    String cost;
    String  cost_time;
    String money;
    Handler PayFeeHandler;
    private String area;
    private String property_fee;
    private PayFeeHandler payFeeRecordHandler;
    private PayFeeDetailHandler payFeeDetailHandler;
    private  String order_id;
    public PayFeeManager(String type, String address, String cost, String cost_time, String money,
                         String area){
        this.type=type;
        this.address=address;
        this.cost=cost;
        this.cost_time=cost_time;
        this.money=money;
        this.area=area;


    }
    public PayFeeManager(Handler payFeeRecordHandler){
        this.PayFeeHandler= payFeeRecordHandler;
    }

    public PayFeeManager(PayFeeHandler payFeeRecordHandler){
        this.payFeeRecordHandler = payFeeRecordHandler;
    }
     public PayFeeManager(PayFeeDetailHandler payFeeDetailHandler,String order_id)
    {
    this.payFeeDetailHandler=payFeeDetailHandler;
    this.order_id=order_id;
    }

    public void sendRequestPayFee(){
        new Thread(new Runnable() {



            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession())
                            .add("type",type).add("address",address)
                            .add("cost",cost)
                            .add("cost_time",cost_time)
                            .add("money",money).add("area",area).build();
                    System.out.println("钱钱钱"+money+" "+type+" "+address+" "+cost+" "+cost_time+area);
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/order_manage/payment/create").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                   String responseData = response.body().string();
                    System.out.println("我的缴费订单阿阿阿阿阿阿阿阿阿阿阿阿阿" +responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }
    public  void  sendRequestPropertyFee(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession().trim())
                           .build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/order_manage/property/free").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("物业费"+responseData);
                    parsePropertyFee(responseData);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        }).start();
    }
    public void parsePropertyFee(String responseData) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseData);
        String area1 = jsonObject.getString("area");
        property_fee = jsonObject.getString("property_free");
        System.out.println(area1+"-------"+property_fee);
        Message message = new Message();
        message.what = 0;
          message.arg1=Integer.parseInt(area1);
          message.arg2=Integer.parseInt(property_fee);
        PayFeeHandler.sendMessage(message);


    }

    public  void sendRequestParkingFee(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession().trim())
                            .build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/order_manage/car/free").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("Session"+Session.getSession());
                    System.out.println( "Parking"+responseData);
                    Message message = new Message();
                    message.what = 1;
                    message.obj=responseData;
                    PayFeeHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }

    public  void  sendRequestPayFeeRecord(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String start="1";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession()).add("start",start)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/order_manage/payment/show").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("记录"+responseData);
                    parseJSONPayFeeRecord(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public  void parseJSONPayFeeRecord(String responseData) {
        Gson gson = new Gson();
        List<PayFeeRecord.ListBean> listBeans=gson.fromJson(responseData,PayFeeRecord.class).getList();
        System.out.println(listBeans.get(0).getC_time()+listBeans.get(0).getMoney()+listBeans.get(0).getOrder_id()
                +listBeans.get(0).getType());
        Message message = new Message();
        message.what = Properties.PayFeeRecord;
       message.obj = listBeans;
       payFeeRecordHandler.sendMessage(message);

    }
    public  void  sendRequestPayFeeDetail(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession()).add("order_id",order_id)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/order_manage/payment/info").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println("详细"+responseData);
                    parseJSONPayFeeDetail(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public  void parseJSONPayFeeDetail(String responseData) {
        Gson gson = new Gson();
        PayDetailInfo.PaymentinfoBean paymentinfo=gson.fromJson(responseData,PayDetailInfo.class).getPaymentinfo();
        System.out.println(paymentinfo.getAddress()+paymentinfo.getArea());
        Message message = new Message();
        message.what = Properties.PayFeeDetail;
        message.obj =paymentinfo;
        payFeeDetailHandler.sendMessage(message);

    }


}
