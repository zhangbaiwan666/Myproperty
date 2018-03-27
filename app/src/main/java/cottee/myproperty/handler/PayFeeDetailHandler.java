package cottee.myproperty.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import cottee.myproperty.constant.PayDetailInfo;
import cottee.myproperty.constant.Properties;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class PayFeeDetailHandler extends Handler {
    PayDetailInfo.PaymentinfoBean paymentinfo;
    private TextView tv_payFeeAmount;
    private TextView tv_payFeeState;
    private TextView tv_payProject;
    private TextView tv_payHouse;
    private TextView tv_payCycle;
    private TextView tv_payTime;
    Context context;
    public  PayFeeDetailHandler(Context context,
                          TextView tv_payFeeAmount,
                          TextView tv_payFeeState
            ,TextView tv_payProject,TextView tv_payHouse,
                          TextView tv_payCycle,TextView tv_payTime){
        this.context=context;
        this.tv_payFeeAmount=tv_payFeeAmount;
        this.tv_payFeeState=tv_payFeeState;
        this.tv_payProject=tv_payProject;
        this.tv_payHouse=tv_payHouse;
        this.tv_payCycle=tv_payCycle;
        this.tv_payTime=tv_payTime;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Properties.PayFeeDetail:{
                paymentinfo= (PayDetailInfo.PaymentinfoBean) msg.obj;
                System.out.println(paymentinfo.getAddress());
                initPayFeeDetailView();
            }
        }
    }
    public  void  initPayFeeDetailView(){
        tv_payCycle.setText(paymentinfo.getCost_time());
        tv_payFeeAmount.setText(paymentinfo.getMoney());
        tv_payFeeState.setText(paymentinfo.getState());
        tv_payHouse.setText(paymentinfo.getAddress());
        tv_payProject.setText(paymentinfo.getType());
        tv_payTime.setText(paymentinfo.getC_time());
    }
}
