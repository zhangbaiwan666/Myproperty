package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cottee.myproperty.R;
import cottee.myproperty.adapter.RepairRecordAdapter;

public class RepairRecordActivity extends Activity {

    private ListView lv_repairRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record);
        RepairRecordAdapter repairRecordAdapter=new RepairRecordAdapter(this);
        lv_repairRecord = (ListView)findViewById(R.id.lv_repairRecord);
        lv_repairRecord.setAdapter(repairRecordAdapter);
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
