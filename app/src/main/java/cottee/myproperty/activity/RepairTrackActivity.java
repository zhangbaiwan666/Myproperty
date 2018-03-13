package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.handler.RepairTrackHandler;
import cottee.myproperty.manager.RepairManager;

public class RepairTrackActivity extends Activity {
    private ImageView imv_photo;
    private TextView tv_trackOfWorker;
    private TextView tv_serProjectOftrack;
    private Bundle bundle;
    private String order_id;
    private TextView tv_baoxiuxinxi;
    private TextView repair_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_track);
        initView();
        bundle=getIntent().getExtras();
        order_id = bundle.getString("order_id");
       // RepairManager.sendRequestRepairTrack(order_id);
        RepairTrackHandler repairTrackHandler=new RepairTrackHandler(this,tv_serProjectOftrack,tv_trackOfWorker,tv_baoxiuxinxi,repair_time);
        RepairManager repairManager=new RepairManager(repairTrackHandler,order_id);
        repairManager.sendRequestRepairTrack();

    }
    public  void initView(){
        imv_photo = (ImageView)findViewById(R.id.imv_photo);
        tv_trackOfWorker = (TextView)findViewById(R.id.tv_trackOfworker);
        tv_serProjectOftrack = (TextView)findViewById(R.id.tv_serProjectOftrack);
//        Bundle bundle=getIntent().getExtras();
//        tv_trackOfWorker.setText(bundle.getString("name"));
//        tv_serProjectOftrack.setText(bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"));
//        new NormalLoadPicture().getPicture(bundle.getString("photo"),imv_photo);
        tv_baoxiuxinxi = (TextView)findViewById(R.id.tv_baoxiuxinxi2);
        repair_time = (TextView)findViewById(R.id.repair_time);
    }

    public void back(View view){
        finish();
    }
    public  void  payFee(View view){
        Intent intent=new Intent(RepairTrackActivity.this,RepairPaymentActivity.class);
        startActivity(intent);
    }
    public  void repairFinish(View view){
        Intent intent=new Intent(RepairTrackActivity.this,RepairPaymentActivity.class);
        startActivity(intent);
    }
}
