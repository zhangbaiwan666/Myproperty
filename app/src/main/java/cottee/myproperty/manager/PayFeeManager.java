package cottee.myproperty.manager;

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
    String area;
   public PayFeeManager(String type,String address,String cost,String cost_time,String money,
    String area){
        this.type=type;
        this.address=address;
        this.cost=cost;
        this.cost_time=cost_time;
        this.money=money;
        this.area=area;

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
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/order_manage/payment/create").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                   String responseData = response.body().string();

                    System.out.println("rrrrrrrrrrrrrPAY"+ responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }


}
