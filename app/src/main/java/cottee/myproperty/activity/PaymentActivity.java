package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cottee.myproperty.R;

public class PaymentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
    public void Sure(View view){
        Intent intent=new Intent(PaymentActivity.this,EvaluateActivity.class);
        startActivity(intent);
    }
    public void back(View view){
        finish();
    }
}
