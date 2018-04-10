package cottee.myproperty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import cottee.myproperty.R;

public class RepairDetailInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_detail_info);
    }
    public void sure(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public  void telePhone(View view){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"0312-345678"));

        if (ActivityCompat.checkSelfPermission(RepairDetailInfoActivity.this, Manifest
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
    public  void mobilePhone(View view){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"13942089345"));

        if (ActivityCompat.checkSelfPermission(RepairDetailInfoActivity.this, Manifest
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
