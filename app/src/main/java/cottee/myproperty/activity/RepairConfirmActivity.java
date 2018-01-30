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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cottee.myproperty.R;
import cottee.myproperty.uitils.CustomDialog;
import cottee.myproperty.uitils.NormalLoadPicture;
import cottee.myproperty.uitils.Session;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class RepairConfirmActivity extends Activity {
    private TextView tv_address;
    private ImageView imv_workerphoto;
    private TextView tv_confirmOfworker;
    private TextView tv_serverProject;
    private EditText et_inputInfo;
    private ImageView imv_takePhotoOne;
    private Bitmap bitmap;
    private Bundle bundle;
    private String session ="5eb305b40deaa3c99b19d6807ee6b332";
    Handler handler;
    private String address;
    private String responseData;
    private ImageView imv_takePhotoTwo;
    private ImageView imv_takePhotoThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_confirm);
        sendRequestOkHttp();
        System.out.println("ssssssssssssssssssss"+Session.getSession());
        initview();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:

                        tv_address = (TextView)findViewById(R.id.tv_address);
                        tv_address.setText(address);

                }
            }
        };
    }
    public    void sendRequestOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session", Session.getSession()).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/user/address").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }
    private void parseJSONObject(String responseData) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseData);
        address = jsonObject.getString("address");
        System.out.println("-----------"+ address);
        Message message = new Message();
        message.what = 0;
        message.obj = address;
        handler.sendMessage(message);
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
                        Intent intent=new Intent(RepairConfirmActivity.this,RepairDetailInfo.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

        builder.create().show();
    }



    public String photo_url="haha";
    public void SubmissionToWeb(){
        new Thread(new Runnable() {



            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("session",Session.getSession()).add("staff_name",bundle.getString("name"))
                            .add("photo_url",photo_url).add("remark",et_inputInfo.getText().toString())
                            .add("part",bundle.getString("bigProject")+"的"+ bundle.getString("smallProject"))
                            .add("staff_id",bundle.getString("id")).build();
                    Request request = new Request.Builder()
                            .url("https://thethreestooges.cn:5210/maintain/indent/submit").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    if (responseData=="1"){
                        Toast.makeText(RepairConfirmActivity.this,"上传成功", LENGTH_SHORT).show();
                    }
                    System.out.println("rrrrrrrrrrrrr"+ responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }



        }).start();
    }
}
