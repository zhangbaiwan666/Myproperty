package cottee.myproperty.handler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.activity.PayDetailInfoActivity;
import cottee.myproperty.adapter.PayFeeRecordAdapter;
import cottee.myproperty.constant.PayDetailInfo;
import cottee.myproperty.constant.PayFeeRecord;
import cottee.myproperty.constant.Properties;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class PayFeeHandler extends Handler {
    Context context;
    public ListView lv_PayFeeRecord;
    private List<PayFeeRecord.ListBean> listBeans;
    PayDetailInfo.PaymentinfoBean paymentinfo;
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
        lv_PayFeeRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                Intent intent=new Intent(context,PayDetailInfoActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("order_id",listBeans.get(i).getOrder_id());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

}
