package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.handler.PayFeeHandler;
import cottee.myproperty.manager.PayFeeManager;

public class PayFeeRecordActivity extends Activity {

    private ListView lv_payFeeRecord;
    private TextView tv_payTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee_record);
        lv_payFeeRecord = (ListView)findViewById(R.id.lv_payFeeRecord);
        tv_payTime = (TextView)findViewById(R.id.tv_payTime);
        PayFeeHandler payFeeHandler=new PayFeeHandler(this,lv_payFeeRecord, tv_payTime);
        PayFeeManager payFeeManager=new PayFeeManager(payFeeHandler);
        payFeeManager.sendRequestPayFeeRecord();
    }
    public  void  back(View view){
        finish();
    }
}
