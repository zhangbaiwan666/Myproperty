package cottee.myproperty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.uitils.NormalLoadPicture;
import cottee.myproperty.view.StarLinearLayout;

public class WorkersInfoActivity extends Activity {
    private ImageView imv_photo;
    private TextView tv_name;
    private TextView tv_id;
    private TextView tv_project;
    private TextView tv_phone;
    private Bundle bundle;
    private StarLinearLayout starLinearLayout;
    private ImageView imv_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_info);
        initview();
    }

    public void initview() {
        imv_photo = (ImageView) findViewById(R.id.imv_photo);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_project = (TextView) findViewById(R.id.tv_project);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        imv_call = (ImageView) findViewById(R.id.imv_call);
        starLinearLayout = (StarLinearLayout) findViewById(R.id.star_pingjia);
        bundle = getIntent().getExtras();
        starLinearLayout.setScore(Float.parseFloat(bundle.getString("grade")) /
                Float.parseFloat(bundle.getString("time")));
        tv_name.setText(bundle.getString("name"));
        tv_id.setText(bundle.getString("id"));
        tv_project.setText(bundle.getString("bigProject") + "的" + bundle.getString("smallProject"));
        tv_phone.setText(bundle.getString("phone"));
        final String phone=bundle.getString("phone");
        new NormalLoadPicture().getPicture(bundle.getString("photo"), imv_photo);
        imv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));

                if (ActivityCompat.checkSelfPermission(WorkersInfoActivity.this, Manifest
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
        });
    }


    public  void  back(View view){
        finish();
    }
    public  void  repair(View view){
        Intent intent=new Intent(WorkersInfoActivity.this,RepairSubmitActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
