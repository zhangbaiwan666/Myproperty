package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    }
    public  void telePhone(View view){

    }
    public  void mobilePhone(View view){

    }
}
