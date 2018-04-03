package cottee.myproperty.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import cottee.myproperty.R;
import cottee.myproperty.manager.RepairManager;
import cottee.myproperty.uitils.CustomDialog;
import cottee.myproperty.uitils.NormalLoadPicture;
import cottee.myproperty.uitils.oss.ConfigOfOssClient;
import cottee.myproperty.uitils.oss.InitOssClient;
import cottee.myproperty.uitils.oss.UploadUtils;

public class RepairSubmitActivity extends Activity {
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
    private String upload_objectKey;
    private String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_confirm);
        initview();
        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){

//                   case Properties.RepairAddress:
//
//                        tv_address = (TextView)findViewById(R.id.tv_address);
//                        tv_address.setText(address);
                    case ConfigOfOssClient.WHAT_SUCCESS_UPLOAD:
                        String success_upload = (String) msg.obj;
                        Toast.makeText(RepairSubmitActivity.this, success_upload, Toast.LENGTH_SHORT).show();
                        break;
                    case ConfigOfOssClient.WHAT_FAILED_UPLOAD:
                        String failed_upload = (String) msg.obj;
                        Toast.makeText(RepairSubmitActivity.this, failed_upload, Toast.LENGTH_SHORT).show();
                        break;
                    case ConfigOfOssClient.WHAT_SUCCESS_DOWNLOAD:
                        Bitmap bitmap = (Bitmap) msg.obj;
                        //iv_download.setImageBitmap(bitmap);
                        String success_download = "成功下载";
                        Toast.makeText(RepairSubmitActivity.this, success_download, Toast.LENGTH_SHORT).show();
                        break;
                    case ConfigOfOssClient.WHAT_FAILED_DOWNLOAD:
                        String failed_download = (String) msg.obj;
                        Toast.makeText(RepairSubmitActivity.this, failed_download, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
//        RepairManager repairManager=new RepairManager(handler);
//        repairManager.sendRequestRepairAddress();
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
        Intent intent=new Intent(RepairSubmitActivity.this,CameraActivity.class);
        startActivityForResult(intent,1);
    }
    public  void takePhotoTwo(View view){
        Intent intent=new Intent(RepairSubmitActivity.this,CameraActivity.class);
        startActivityForResult(intent,2);
    }
    public  void takePhotoThree(View view){
        Intent intent=new Intent(RepairSubmitActivity.this,CameraActivity.class);
        startActivityForResult(intent,3);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK)
                {
                    filepath = data.getStringExtra( "filepath" );
                    System.out.println("filepath"+filepath);
                    bitmap = BitmapFactory.decodeFile(filepath);
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
        builder.setMessage("您还没有拍照，是否要拍照");
        // builder.setTitle("温馨小提示");
        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });

        builder.setNegativeButton("是",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        upload_objectKey = "property/"+ "xujingjing"+"/item"
                                +"/"+new DateFormat().format( "yyyyMMdd_hhmmss",
                                Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
                        UploadUtils.uploadFileToOss(handler, ConfigOfOssClient.BUCKET_NAME, upload_objectKey, filepath);
                        RepairManager.SubmissionToWeb(upload_objectKey,et_inputInfo.getText().toString(),bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"), bundle.getString("id"),bundle.getString("name")
                                 );
                        Intent intent=new Intent(RepairSubmitActivity.this,RepairDetailInfoActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

        builder.create().show();
    }





}
