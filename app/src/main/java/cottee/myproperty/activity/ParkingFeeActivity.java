package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cottee.myproperty.R;
import cottee.myproperty.manager.PayFeeManager;

public class ParkingFeeActivity extends Activity {
    Handler handler;
    private RadioGroup radioGroup_parking;
    private RadioButton rb;
    private RadioButton radioSix_parking;
    private RadioButton radioTwelve_parking;
    private TextView tv_parkingFee;
    String type="停车费";
    String address="明秀山庄";
    private String parkingfee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_fee);
        tv_parkingFee = (TextView)findViewById(R.id.tv_parkingFee);
        radioGroup_parking = (RadioGroup)findViewById(R.id.radioGroup_parking);
        radioSix_parking = (RadioButton)findViewById(R.id.radioSix_parking);
        radioTwelve_parking = (RadioButton)findViewById(R.id.radioTwelve_parking);
        radioGroup_parking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private String money;
            private String money2;
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                System.out.println( rb.getText());
                money = "1200";
                money2 = "2400";
                if (rb.getText().equals("6个月"))
                    tv_parkingFee.setText(money +"元");
                if (rb.getText().equals("12个月")){
                    tv_parkingFee.setText(money2 +"元");
                }
            }
        });
        handler = new Handler(){


            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        parkingfee = (String) msg.obj;

                      TextView  tv_fee = (TextView)findViewById(R.id.tv_ParkingFee);
                        tv_fee.setText(parkingfee);
                }
            }
        };
        PayFeeManager payFeeManager=new PayFeeManager(handler);
        payFeeManager.sendRequestParkingFee();
    }
    public  void back(View view){
        finish();
    }

    public  void  Sure(View view){
        if (TextUtils.isEmpty( tv_parkingFee.getText() )){
            Toast.makeText(this,"请选择要缴纳的月数",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, PayFeeFinishActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("payFeeAmount", tv_parkingFee.getText().toString());

            intent.putExtras(bundle);
            PayFeeManager payFeeManager = new PayFeeManager(type, address, parkingfee, rb.getText().toString(), tv_parkingFee.getText().toString(),"a");
            payFeeManager.sendRequestPayFee();
            startActivity(intent);
        }
    }
}
