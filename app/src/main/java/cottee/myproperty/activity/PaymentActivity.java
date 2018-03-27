package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import cottee.myproperty.R;

public class PaymentActivity extends Activity {

    private LinearLayout ll_payRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        android.app.ActionBar actionBar=getActionBar();
        if(actionBar!=null)
        {
            actionBar.hide();
        }
        ll_payRecord = (LinearLayout)findViewById(R.id.ll_payRecord);
        ll_payRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaymentActivity.this,PayFeeRecordActivity.class);
                startActivity(intent);
            }
        });
    }
    public void  back(View view){
        finish();
    }
    public  void  propertyFee(View view){
        Intent intent=new Intent(PaymentActivity.this,PropertyFeeActivity.class);
        startActivity(intent);
    }
    public  void  garbageFee(View view){
        Intent intent=new Intent(PaymentActivity.this,GarbageFeeActivity.class);
        startActivity(intent);
    }
    public void parkingFee(View view){
        Intent intent=new Intent(PaymentActivity.this,ParkingFeeActivity.class);
        startActivity(intent);
    }
}
