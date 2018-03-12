package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.manager.PayFeeManager;

public class ParkingFeeActivity extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_fee);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                     String fee= (String) msg.obj;

                      TextView  tv_fee = (TextView)findViewById(R.id.tv_ParkingFee);
                        tv_fee.setText(fee);
                }
            }
        };
        PayFeeManager payFeeManager=new PayFeeManager(handler);
        payFeeManager.sendRequestParkingFee();
    }
    public  void back(View view){
        finish();
    }

}
