package cottee.myproperty.handler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import cottee.myproperty.activity.RepairTrackActivity;
import cottee.myproperty.adapter.RepairRecordAdapter;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.RepairRecord;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class RepairRecordHandler extends Handler {
    private Context RepairRecordcontext;
    public ListView lv_RepairRecord;
    private List<RepairRecord.ListBean> listBeans;
     ProgressBar pb_repairRecord;
    public RepairRecordHandler(Context context, ListView lv_RepairRecord,  ProgressBar pb_repairRecord) {
        this.RepairRecordcontext = context;
        this.lv_RepairRecord = lv_RepairRecord;
        this.pb_repairRecord=pb_repairRecord;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {

            case Properties.RepairRecord: {
                listBeans = (List<RepairRecord.ListBean>) msg.obj;
                initRepairRecordView();
            }


        }
    }

    public void initRepairRecordView() {
        RepairRecordAdapter repairRecordAdapter = new RepairRecordAdapter(RepairRecordcontext, listBeans,this);
        lv_RepairRecord.setAdapter(repairRecordAdapter);
        if (repairRecordAdapter!=null){
            pb_repairRecord.setVisibility(View.GONE);
        }
        lv_RepairRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                Intent intent=new Intent(RepairRecordcontext,RepairTrackActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("order_id",listBeans.get(i).getOrder_id());
                intent.putExtras(bundle);
                RepairRecordcontext.startActivity(intent);
            }
        });
    }
}

