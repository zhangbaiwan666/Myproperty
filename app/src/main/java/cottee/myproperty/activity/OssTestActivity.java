package cottee.myproperty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import cottee.myproperty.R;
import cottee.myproperty.uitils.oss.ConfigOfOssClient;
import cottee.myproperty.uitils.oss.DownloadUtils;
import cottee.myproperty.uitils.oss.InitOssClient;
import cottee.myproperty.uitils.oss.UploadUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by z on 2018/4/3.
 */

public class OssTestActivity extends Activity {

    private ImageView imgShow = null;
    private TextView imgPath = null;
    private final int IMAGE_CODE = 0;
    Uri bitmapUri = null;
    private final String IMAGE_TYPE = "image/*";
    private ImageView iv_download;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String success_upload = (String) msg.obj;
                    Toast.makeText(OssTestActivity.this, success_upload, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String failed_upload = (String) msg.obj;
                    Toast.makeText(OssTestActivity.this, failed_upload, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    iv_download.setImageBitmap(bitmap);
                    String success_download = "成功下载";
                    Toast.makeText(OssTestActivity.this, success_download, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    String failed_download = (String) msg.obj;
                    Toast.makeText(OssTestActivity.this, failed_download, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };
    private EditText et_filepath;
    private EditText et_upload_key;
    private EditText et_download_key;
    private Uri photoUri;
    private static final int TAKE_PHOTO=0;
    private static final int PICK_PHOTO=1;
    private Button albumButton;
    private String upload_objectKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oss_test);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        et_filepath = (EditText) findViewById(R.id.et_filepath);//上传的文件本地地址（手机里的）
        et_upload_key = (EditText) findViewById(R.id.et_upload_key);//上传文件的key
        et_download_key = (EditText) findViewById(R.id.et_download_key);//要下载文件的key
        albumButton=(Button)findViewById(R.id.album);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        //先初始化oss客户端  //必备  //报空说明线程没执行完
        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(Intent.ACTION_PICK);
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_PHOTO);
            }
        });

    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public void upload(View v) {

        upload_objectKey = "property/"+ "zhangxingyu"+"/item"
                +"/"+new DateFormat().format( "yyyyMMdd_hhmmss",
                Calendar.getInstance( Locale.CHINA ) ) + ".jpg";//CHINA 是随机给的数值
        String uploadFilePath = et_filepath.getText().toString().trim();
        UploadUtils.uploadFileToOss(handler, ConfigOfOssClient.BUCKET_NAME, upload_objectKey, uploadFilePath);
    }

    public void download(View v) {
        new Thread() {
            @Override
            public void run() {
//                String download_objectKey = "property/"+ "zhangxingyu"+"/item"
//                        +"/"+new DateFormat().format( "yyyyMMdd_hhmmss",
//                        Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
                final File cache_image = new File(getCacheDir(), Base64.encodeToString(upload_objectKey.getBytes(), Base64.DEFAULT));
                DownloadUtils.downloadFileFromOss(cache_image, handler, ConfigOfOssClient.BUCKET_NAME, upload_objectKey);
            }
        }.start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case TAKE_PHOTO:
////                    try {
////                        Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
////                        photoImageView.setImageBitmap(bitmap);
////                    }catch (IOException e){
////                        e.printStackTrace();
////                    }
//                    setImageBitmap();
//                    galleryAddPic();
                    break;
                case PICK_PHOTO:
                    //data中自带有返回的uri
                    photoUri=data.getData();
                    //获取照片路径
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(photoUri,
                            filePathColumn, null, null, null);
                    String mImgPath;
                    if(cursor!=null)
                    {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        mImgPath = picturePath;
                    }
                    else
                    {
                        mImgPath = photoUri.getPath();
                    }
                    et_filepath.setText(mImgPath);
                    break;
            }
        }
    }

}
