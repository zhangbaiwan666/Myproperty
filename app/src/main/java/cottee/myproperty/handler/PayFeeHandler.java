package cottee.myproperty.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.adapter.PayFeeRecordAdapter;
import cottee.myproperty.constant.PayFeeRecord;
import cottee.myproperty.constant.Properties;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class PayFeeHandler extends Handler {
    Context context;
    public ListView lv_PayFeeRecord;
    private List<PayFeeRecord.ListBean> listBeans;
    private TextView tv_feeType;
    public PayFeeHandler(Context context, ListView lv_PayFeeRecord, TextView
            tv_feeType){
        this.context=context;
        this.lv_PayFeeRecord=lv_PayFeeRecord;
        this.tv_feeType=tv_feeType;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Properties.PayFeeRecord: {
              listBeans= (List<PayFeeRecord.ListBean>) msg.obj;
              initPayFeeRecordView();

            }
        }
    }
    public void initPayFeeRecordView(){
        PayFeeRecordAdapter payFeeRecordAdapter=new PayFeeRecordAdapter(context,listBeans);
        lv_PayFeeRecord.setAdapter(payFeeRecordAdapter);
    }
}
