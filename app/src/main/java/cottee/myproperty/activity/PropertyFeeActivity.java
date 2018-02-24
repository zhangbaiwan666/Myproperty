package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;

import cottee.myproperty.R;
import cottee.myproperty.manager.PayFeeManager;

public class PropertyFeeActivity extends Activity {
        String type="物业费";
        String address="明秀山庄";
        String  cost="500元";
        String cost_time="6个月";
        String money="3000";
        String area="100平方米";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_fee);
        PayFeeManager payFeeManager=new PayFeeManager(type,address,cost,cost_time,money,area);
        payFeeManager.sendRequestPayFee();
    }
}
