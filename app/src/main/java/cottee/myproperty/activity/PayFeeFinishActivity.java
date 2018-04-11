package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cottee.myproperty.R;

public class PayFeeFinishActivity extends Activity {

    private TextView tv_payFeeMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee_finish);
        Bundle bundle = getIntent().getExtras();
        tv_payFeeMoney = (TextView)findViewById(R.id.tv_payFeeMoney);
        tv_payFeeMoney.setText(bundle.getString("payFeeAmount"));
    }
    public  void  back(View view){
        finish();
    }
    public  void  Sure(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
