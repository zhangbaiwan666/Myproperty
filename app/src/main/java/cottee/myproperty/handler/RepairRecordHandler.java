package cottee.myproperty.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import java.util.List;

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
    public RepairRecordHandler(Context context,ListView lv_RepairRecord){
        this.RepairRecordcontext=context;
        this.lv_RepairRecord=lv_RepairRecord;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){

            case Properties.RepairRecord:{
                listBeans= (List<RepairRecord.ListBean>) msg.obj;
                initRepairRecordView();
            }

        }
    }
    public  void  initRepairRecordView(){
        RepairRecordAdapter repairRecordAdapter=new RepairRecordAdapter(RepairRecordcontext,listBeans);
        lv_RepairRecord.setAdapter(repairRecordAdapter);

    }
}
