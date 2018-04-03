package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import cottee.myproperty.R;
import cottee.myproperty.handler.RepairRecordHandler;
import cottee.myproperty.manager.RepairManager;

public class RepairRecordActivity extends Activity {
    private ImageView imageview_item;
    private ListView lv_repairRecord;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record);
        imageview_item = (ImageView)findViewById(R.id.imageview_item);
        //RepairRecordAdapter repairRecordAdapter=new RepairRecordAdapter(this);
        lv_repairRecord = (ListView)findViewById(R.id.lv_repairRecord);
        //lv_repairRecord.setAdapter(repairRecordAdapter);
        RepairRecordHandler repairHandler=new RepairRecordHandler(this,lv_repairRecord);
        RepairManager repairManager=new RepairManager(repairHandler,lv_repairRecord);
        repairManager.sendRequestRepairRecord();
        lv_repairRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i,
                            long l) {
        Intent intent=new Intent(RepairRecordActivity.this,RepairTrackActivity.class);
        startActivity(intent);
    }
});
    }
    public  void  back(View view){
        finish();
    }


}
