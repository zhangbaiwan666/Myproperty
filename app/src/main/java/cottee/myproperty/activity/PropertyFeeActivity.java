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
    private String money2;
    private String area1;
    private String property_fee;
    private TextView tv_propertyFee;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_fee);
        tv_propertyFee = (TextView)findViewById(R.id.tv_propertyFee);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioSix = (RadioButton)findViewById(R.id.radioSix);
        radioTwelve = (RadioButton)findViewById(R.id.radioTwelve);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private String money;

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                System.out.println( rb.getText());
                money = "240000";
                money2 = "480000";
                if (rb.getText().equals("6个月"))
                   tv_propertyFee.setText(money +"元");
                if (rb.getText().equals("12个月")){
                    tv_propertyFee.setText(money2 +"元");
                }

            }
        });


        handler = new Handler(){



            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        System.out.println("aaaaaaaaaaaaa"+msg.arg1);
                        area1 = String.valueOf(msg.arg1);
                        property_fee = String.valueOf(msg.arg2);
                        tv_area = (TextView)findViewById(R.id.tv_areaProperty);
                        tv_fee = (TextView)findViewById(R.id.tv_feeProperty);
                        tv_area.setText(area1);
                        tv_fee.setText(property_fee);



                }
            }
        };

        PayFeeManager payFeeManager1=new PayFeeManager(handler);
        payFeeManager1.sendRequestPropertyFee();
    }
    public void back(View view){
        finish();
    }
    public  void  Sure(View view){
        Intent intent=new Intent(this,PayFeeFinishActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("payFeeAmount",tv_propertyFee.getText().toString());
        intent.putExtras(bundle);
        PayFeeManager payFeeManager=new PayFeeManager(type,address,property_fee,rb.getText().toString(),tv_propertyFee.getText().toString(),area1);
        payFeeManager.sendRequestPayFee();
        startActivity(intent);
    }

}
