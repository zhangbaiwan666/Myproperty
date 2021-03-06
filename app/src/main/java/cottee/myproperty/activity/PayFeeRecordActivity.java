package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.handler.PayFeeHandler;
import cottee.myproperty.manager.PayFeeManager;

public class PayFeeRecordActivity extends Activity {

    private ListView lv_payFeeRecord;
    private TextView tv_payTime;
    private ProgressBar pb_payRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee_record);
        lv_payFeeRecord = (ListView)findViewById(R.id.lv_payFeeRecord);
        tv_payTime = (TextView)findViewById(R.id.tv_payTime);
        pb_payRecord=(ProgressBar)findViewById(R.id.pb_payRecord);
        PayFeeHandler payFeeHandler=new PayFeeHandler(this,lv_payFeeRecord, tv_payTime,pb_payRecord);
        PayFeeManager payFeeManager=new PayFeeManager(payFeeHandler);
        payFeeManager.sendRequestPayFeeRecord();
        if (lv_payFeeRecord==null){
            pb_payRecord.setVisibility(View.VISIBLE);
        }

    }
    public  void  back(View view){
        finish();
    }
}
