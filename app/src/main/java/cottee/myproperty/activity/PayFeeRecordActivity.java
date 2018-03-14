package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cottee.myproperty.R;
import cottee.myproperty.adapter.PayFeeRecordAdapter;

public class PayFeeRecordActivity extends Activity {

    private ListView lv_payFeeRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee_record);
        lv_payFeeRecord = (ListView)findViewById(R.id.lv_payFeeRecord);
        PayFeeRecordAdapter recordAdapter=new PayFeeRecordAdapter(this);
        lv_payFeeRecord.setAdapter(recordAdapter);
     lv_payFeeRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {


         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int
                 i, long l) {
             Intent intent=new Intent(PayFeeRecordActivity.this,PayDetailInfoActivity.class);
             startActivity(intent);
         }

     });
    }
    public  void  back(View view){
        finish();
    }
}
