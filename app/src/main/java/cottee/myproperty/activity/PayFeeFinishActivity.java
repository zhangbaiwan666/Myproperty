package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cottee.myproperty.R;

public class PayFeeFinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee_finish);
    }
    public  void  back(View view){
        finish();
    }
    public  void  Sure(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
