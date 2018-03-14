package cottee.myproperty.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.manager.RepairManager;
import cottee.myproperty.uitils.CustomDialog;
import cottee.myproperty.uitils.NormalLoadPicture;

public class RepairConfirmActivity extends Activity {
    private TextView tv_address;
    private ImageView imv_workerphoto;
    private TextView tv_confirmOfworker;
    private TextView tv_serverProject;
    private EditText et_inputInfo;
    private ImageView imv_takePhotoOne;
    private Bitmap bitmap;
    private Bundle bundle;
    public String photo_url="haha";
    Handler handler;
    private String address;
    private String responseData;
    private ImageView imv_takePhotoTwo;
    private ImageView imv_takePhotoThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_confirm);
       // sendRequestOkHttp();
        initview();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Properties.RepairAddress:

                        tv_address = (TextView)findViewById(R.id.tv_address);
                        tv_address.setText(address);

                }
            }
        };
        RepairManager repairManager=new RepairManager(handler);
        repairManager.sendRequestRepairAddress();
    }
    public void initview(){
        bundle = getIntent().getExtras();

        imv_workerphoto = (ImageView)findViewById(R.id.imv_workerphoto);
        tv_confirmOfworker = (TextView)findViewById(R.id.tv_confirmOfworker);
        tv_serverProject = (TextView)findViewById(R.id.tv_serverProject);
        et_inputInfo = (EditText)findViewById(R.id.et_inputInfo);
        imv_takePhotoOne = (ImageView)findViewById(R.id.imv_takePhotoOne);
        tv_confirmOfworker.setText(bundle.getString("name"));
        tv_serverProject.setText(bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"));
        new NormalLoadPicture().getPicture(bundle.getString("photo"),imv_workerphoto);
        imv_takePhotoTwo = (ImageView)findViewById(R.id.imv_takePhotoTwo);
        imv_takePhotoThree = (ImageView)findViewById(R.id.imv_takePhotoThree);
    }
    public  void takePhotoOne(View view){
        Intent intent=new Intent(RepairConfirmActivity.this,CameraActivity.class);
        startActivityForResult(intent,1);
    }
    public  void takePhotoTwo(View view){
        Intent intent=new Intent(RepairConfirmActivity.this,CameraActivity.class);
        startActivityForResult(intent,2);
    }
    public  void takePhotoThree(View view){
        Intent intent=new Intent(RepairConfirmActivity.this,CameraActivity.class);
        startActivityForResult(intent,3);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK)
                {

                    String filepath = data.getStringExtra( "filepath" );
                    bitmap = BitmapFactory.decodeFile( filepath );
                    imv_takePhotoOne.setImageBitmap(bitmap);

                }
            case 2:
                if(resultCode == RESULT_OK)
            {

                String filepath = data.getStringExtra( "filepath" );
                bitmap = BitmapFactory.decodeFile( filepath );
                imv_takePhotoTwo.setImageBitmap(bitmap);

            }
            case 3:
                if(resultCode == RESULT_OK)
                {

                    String filepath = data.getStringExtra( "filepath" );
                    bitmap = BitmapFactory.decodeFile( filepath );

                    imv_takePhotoThree.setImageBitmap(bitmap);
                }
        }
    }

    public void  back(View view){
        finish();
    }
    public  void  Sure(View view){



        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("确定要提交报修单吗");
        // builder.setTitle("温馨小提示");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        RepairManager.SubmissionToWeb(photo_url,et_inputInfo.getText().toString(),bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"), bundle.getString("id"),bundle.getString("name")
                                 );
                        Intent intent=new Intent(RepairConfirmActivity.this,RepairDetailInfoActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

        builder.create().show();
    }





}
