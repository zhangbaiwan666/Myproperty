package cottee.myproperty.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioGroup radioGroup;
    private RadioButton radioSix;
    private RadioButton radioTwelve;
    private RadioButton rb;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_fee);
        final TextView tv_propertyFee=(TextView)findViewById(R.id.tv_propertyFee);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioSix = (RadioButton)findViewById(R.id.radioSix);
        radioTwelve = (RadioButton)findViewById(R.id.radioTwelve);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                System.out.println( rb.getText());
                String money="240000";
                String money2="480000";
                if (rb.getText()=="6个月"){
                    tv_propertyFee.setText(money2);
                }
            }
        });


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        System.out.println("aaaaaaaaaaaaa"+msg.arg1);
                        String area= String.valueOf(msg.arg1);
                        String property_fee=String.valueOf(msg.arg2);
                        tv_area = (TextView)findViewById(R.id.tv_areaProperty);
                        tv_fee = (TextView)findViewById(R.id.tv_feeProperty);
                        tv_area.setText(area);
                        tv_fee.setText(property_fee);

//                        if (rb.getText()=="6个月"){
//                            tv_propertyFee.setText(200*200*6);
//                        }else {
//                            tv_propertyFee.setText(200*200*12);
//                        }
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
    public  void  Sure(View view){
        Intent intent=new Intent(this,PayFeeFinishActivity.class);
        startActivity(intent);
    }

}
