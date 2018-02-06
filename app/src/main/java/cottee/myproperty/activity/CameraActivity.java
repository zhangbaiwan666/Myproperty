package cottee.myproperty.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.uitils.Properties;

public class CameraActivity extends Activity implements SurfaceHolder.Callback, Camera.AutoFocusCallback{

    SurfaceHolder holder;
    Camera myCamera;
    SurfaceView mySurfaceView;
    boolean isClicked = false;
    Bitmap uploadBitmap;
    Bitmap originalBitmap;
    private RelativeLayout rel_shutter;
    private RelativeLayout rel_photoOk;
    private int cameraPosition=1;
    String filepath= Properties.getContext().getFilesDir()+"/myImage";
    private float ratio;
    private Camera.Size mBestPictureSizes;
    private Camera.Size mBestPreviewSize;
    private LinearLayout ll_takePhoto;
    private LinearLayout ll_retakePhoto;
    private Bitmap bmp;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mySurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        holder = mySurfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        rel_shutter = (RelativeLayout)findViewById(R.id.rel_shutter);
        rel_photoOk = (RelativeLayout)findViewById(R.id.rel_photook);


    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ratio = (float)mySurfaceView.getWidth()/mySurfaceView.getHeight();
        mBestPictureSizes = null;
        mBestPreviewSize = null;
        if(myCamera == null)
        {
            if (cameraPosition==1){
                myCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
            Camera.Parameters parameters = myCamera.getParameters();
            List pictureSizes=parameters.getSupportedPictureSizes();
            if (mBestPictureSizes ==null){
                mBestPictureSizes =findBestPictureSize(pictureSizes,parameters.getPictureSize(), ratio);

            }
            parameters.setPictureSize(mBestPictureSizes.width, mBestPictureSizes.height);
            List previewSizes=parameters.getSupportedPreviewSizes();
            if (mBestPreviewSize ==null)
            {
                mBestPreviewSize =findBestPreviewSize(previewSizes,parameters.getPreviewSize(),

                        mBestPictureSizes, ratio);

            }
            parameters.setPreviewSize(mBestPreviewSize.width, mBestPreviewSize.height);
            myCamera.setParameters(parameters);
            try {
                myCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Camera.Parameters params = myCamera.getParameters();
        params.setPictureFormat(PixelFormat.JPEG);
        myCamera.setDisplayOrientation(90);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        myCamera.setParameters(params);
        myCamera.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myCamera.stopPreview();
        myCamera.release();
        myCamera = null;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        Camera.Parameters params = myCamera.getParameters();
        params.setPictureFormat(PixelFormat.JPEG);
        myCamera.takePicture(null, null, jpeg);
    }
    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            originalBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            if (cameraPosition==1){
                matrix.setRotate(90);
            }else {
                matrix.setRotate(270);
            }
            uploadBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap
                    .getWidth(), originalBitmap.getHeight(), matrix, true);
            rel_shutter.setVisibility(View.GONE);
            rel_photoOk.setVisibility(View.VISIBLE);
            System.out.println("--------"+originalBitmap.getWidth()+"------"+originalBitmap.getHeight());
        }
    };
    public void convertCamera(View view){
        //释放当前的相机资源
        myCamera.stopPreview();
        myCamera.release();
        myCamera=null;
        //1代表后置摄像头，0代表前置摄像头
        if (cameraPosition==1){
            myCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            cameraPosition=0;
        }
        else {
            myCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            cameraPosition=1;
        }
        Camera.Parameters parameters = myCamera.getParameters();
        List pictureSizes=parameters.getSupportedPictureSizes();
        if (mBestPictureSizes==null){
            mBestPictureSizes=findBestPictureSize(pictureSizes,parameters.getPictureSize(),ratio);

        }
        parameters.setPictureSize(mBestPictureSizes.width,mBestPictureSizes.height);
        List previewSizes=parameters.getSupportedPreviewSizes();
        if (mBestPreviewSize==null)
        {
            mBestPreviewSize=findBestPreviewSize(previewSizes,parameters.getPreviewSize(),
                    mBestPictureSizes,ratio);

        }
        parameters.setPreviewSize(mBestPreviewSize.width,mBestPreviewSize.height);
        myCamera.setParameters(parameters);
        try {
            myCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myCamera.setDisplayOrientation(90);
        myCamera.startPreview();


    }

    public  void  shutter(View view){
        if (!isClicked) {
            isClicked = true;
        }else {
            myCamera.startPreview();
        }
        myCamera.autoFocus(this);
    }
    public  void savePhoto(){
        File file = new File(filepath);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream
                    (filepath);
            uploadBitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void usePhoto(View view) {
        new Thread() {
            @Override
            public void run() {
                savePhoto();
                Intent intent = new Intent();
                intent.putExtra("filepath", filepath);
                setResult(RESULT_OK, intent);
                finish();

            }
        }.start();

    }
    public  void cancel(View view){
        finish();
    }
    public  void  retakePhoto(View view){
        uploadBitmap.recycle();
        myCamera.startPreview();
        rel_shutter.setVisibility(View.VISIBLE);
        rel_photoOk.setVisibility(View.GONE);
    }
    //获取相机支持的照片尺寸，找出最适合的尺寸
    private  Camera.Size findBestPictureSize(List sizes, Camera.Size defaultSize,
                                             float minRatio){
        final  int MIN_PIXELS=320*480;
        Iterator it=sizes.iterator();
        while (it.hasNext()){
            Camera.Size size= (Camera.Size) it.next();
            if ((float)size.height/size.width<=minRatio){
                it.remove();
                continue;
            }
            if (size.width*size.height<MIN_PIXELS){
                it.remove();
            }
        }
        if (!sizes.isEmpty()){
            return (Camera.Size) sizes.get(4);
        }
        return  defaultSize;
    }
    //设置预览图片的尺寸
    private  Camera.Size findBestPreviewSize(List sizes,Camera.Size defaultSize,
                                             Camera.Size pictureSize,float minRatio)
    {
        final int pictureWidth=pictureSize.width;
        final  int pictureHeight=pictureSize.height;
        boolean isBestSize=(pictureHeight/(float)pictureWidth)>minRatio;
        Iterator it=sizes.iterator();
        while (it.hasNext()){
            Camera.Size size= (Camera.Size) it.next();
            if ((float)size.height/size.width<=minRatio){
                it.remove();
                continue;
            }
            if (isBestSize&&size.width*pictureHeight==size.height*pictureWidth){
                return  size;
            }
        }
        if (!sizes.isEmpty()){
            return (Camera.Size) sizes.get(4);
        }
        return  defaultSize;
    }
}
