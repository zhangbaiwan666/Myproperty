package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cottee.myproperty.R;

public class ElevatorFeeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_fee);
    }
    public  void  back(View view){
        finish();
    }
}
