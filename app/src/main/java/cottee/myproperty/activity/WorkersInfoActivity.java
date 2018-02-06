package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_info);
        initview();
    }
    public  void  initview(){
        imv_photo = (ImageView)findViewById(R.id.imv_photo);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_project = (TextView)findViewById(R.id.tv_project);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        starLinearLayout = (StarLinearLayout)findViewById(R.id.star_pingjia);

        bundle = getIntent().getExtras();
        starLinearLayout.setScore(Float.parseFloat(bundle.getString("grade"))/
                Float.parseFloat(bundle.getString("time")));
        tv_name.setText(bundle.getString("name"));
        tv_id.setText(bundle.getString("id"));
        tv_project.setText(bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"));
        tv_phone.setText(bundle.getString("phone"));
        new NormalLoadPicture().getPicture(bundle.getString("photo"),imv_photo);

    }


    public  void  back(View view){
        finish();
    }
    public  void  repair(View view){
        Intent intent=new Intent(WorkersInfoActivity.this,RepairConfirmActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
