package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.uitils.NormalLoadPicture;

public class RepairTrackActivity extends Activity {
    private ImageView imv_photo;
    private TextView tv_trackOfWorker;
    private TextView tv_serProjectOftrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_track);

    }
    public  void initView(){
        imv_photo = (ImageView)findViewById(R.id.imv_photo);
        tv_trackOfWorker = (TextView)findViewById(R.id.tv_trackOfworker);
        tv_serProjectOftrack = (TextView)findViewById(R.id.tv_serProjectOftrack);
        Bundle bundle=getIntent().getExtras();
        tv_trackOfWorker.setText(bundle.getString("name"));
        tv_serProjectOftrack.setText(bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"));
        new NormalLoadPicture().getPicture(bundle.getString("photo"),imv_photo);
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
