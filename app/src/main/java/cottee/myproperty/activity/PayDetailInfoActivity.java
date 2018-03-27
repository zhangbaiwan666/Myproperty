package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.handler.PayFeeDetailHandler;
import cottee.myproperty.manager.PayFeeManager;

public class PayDetailInfoActivity extends Activity {
    private String order_id;
    private Bundle bundle;
    private TextView tv_payFeeAmount;
    private TextView tv_payFeeState;
    private TextView tv_payProject;
    private TextView tv_payHouse;
    private TextView tv_payCycle;
    private TextView tv_payTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail_info);
        bundle = getIntent().getExtras();
        order_id = bundle.getString("order_id");
        tv_payFeeAmount = (TextView)findViewById(R.id.tv_payFeeAmount);
        tv_payFeeState = (TextView)findViewById(R.id.tv_payFeeState);
        tv_payProject = (TextView)findViewById(R.id.tv_payProject);
        tv_payHouse = (TextView)findViewById(R.id.tv_payHouse);
        tv_payCycle = (TextView)findViewById(R.id.tv_payCycle);
        tv_payTime = (TextView)findViewById(R.id.tv_payTime);
        PayFeeDetailHandler payFeeHandler=new PayFeeDetailHandler(this,tv_payFeeAmount,tv_payFeeState,tv_payProject,tv_payHouse
        ,tv_payCycle,tv_payTime);
        PayFeeManager payFeeManager=new PayFeeManager(payFeeHandler,order_id);
       payFeeManager.sendRequestPayFeeDetail();
    }
    public  void back(View view){
        finish();
    }
}
