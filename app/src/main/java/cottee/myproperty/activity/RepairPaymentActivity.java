package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cottee.myproperty.R;
import cottee.myproperty.listener.NoDoubleClickListener;

public class RepairPaymentActivity extends Activity {

    private Button btn_repairPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairpayment);
        btn_repairPay = (Button) findViewById(R.id.btn_repairPay);
        try {
            btn_repairPay.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent=new Intent(RepairPaymentActivity.this,EvaluateActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



    }

//    public void Sure(View view){
//
//    }
    public void back(View view){
        finish();
    }
}
