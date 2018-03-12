package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.manager.PayFeeManager;

public class PropertyFeeActivity extends Activity {
        String type="物业费";
        String address="明秀山庄";
        String  cost="500元";
        String cost_time="6个月";
        String money="3000";
        String area="100平方米";

     Handler handler;
    private TextView tv_area;
    private TextView tv_fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_fee);


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        System.out.println("aaaaaaaaaaaaa"+msg.arg1);
                        String area= String.valueOf(msg.arg1);
                        tv_area = (TextView)findViewById(R.id.tv_areaProperty);
                        tv_fee = (TextView)findViewById(R.id.tv_feeProperty);
                        tv_area.setText(area);
                        tv_fee.setText(area);
                }
            }
        };
        PayFeeManager payFeeManager=new PayFeeManager(type,address,cost,cost_time,money,area);
        payFeeManager.sendRequestPayFee();
        PayFeeManager payFeeManager1=new PayFeeManager(handler);
        payFeeManager1.sendRequestPropertyFee();
    }
    public void back(View view){
        finish();
    }

}
