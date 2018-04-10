package cottee.myproperty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cottee.myproperty.R;
import cottee.myproperty.handler.RepairTrackHandler;
import cottee.myproperty.manager.RepairManager;
import cottee.myproperty.uitils.CustomDialog;

public class RepairTrackActivity extends Activity {
    private ImageView imv_photo;
    private TextView tv_trackOfWorker;
    private TextView tv_serProjectOftrack;
    private Bundle bundle;
    private String order_id;
    private TextView tv_baoxiuxinxi;
    private TextView repair_time;
    private ProgressBar pb_repairTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_track);
        initView();
        bundle=getIntent().getExtras();
        order_id = bundle.getString("order_id");
       // RepairManager.sendRequestRepairTrack(order_id);
        RepairTrackHandler repairTrackHandler=new RepairTrackHandler(this,tv_serProjectOftrack,tv_trackOfWorker,tv_baoxiuxinxi,repair_time,pb_repairTrack);
        RepairManager repairManager=new RepairManager(repairTrackHandler,order_id);
        repairManager.sendRequestRepairTrack();
//        if (tv_trackOfWorker==null||repair_time==null){
//            pb_repairTrack.setVisibility(View.VISIBLE);
//        }

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
        pb_repairTrack = (ProgressBar)findViewById(R.id.pb_repairTrack);

    }

    public void back(View view){
        finish();
    }
    public  void  payFee(View view){
        Intent intent=new Intent(RepairTrackActivity.this,RepairPaymentActivity.class);
        startActivity(intent);
        finish();
    }
    public  void repairFinish(View view){
        Intent intent=new Intent(RepairTrackActivity.this,RepairPaymentActivity.class);
        startActivity(intent);
    }
    public  void  requestCancle(View view){
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("您确定要取消报修订单吗");
        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });

        builder.setNegativeButton("是",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(RepairTrackActivity.this,"此功能正在开发,敬请期待",Toast.LENGTH_SHORT).show();

                    }
                });

        builder.create().show();
    }
    public void contactWorker(View view){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"13942037302"));

        if (ActivityCompat.checkSelfPermission(RepairTrackActivity.this, Manifest
                .permission.CALL_PHONE) != PackageManager
                .PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then
            // overriding
            //   public void onRequestPermissionsResult(int
            // requestCode, String[] permissions,
            //                                          int[]
            // grantResults)
            // to handle the case where the user grants the
            // permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);

    }
}
