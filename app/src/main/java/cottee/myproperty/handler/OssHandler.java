package cottee.myproperty.handler;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import cottee.myproperty.uitils.oss.ConfigOfOssClient;

/**
 * Created by Administrator on 2018-03-24.
 */

public class OssHandler extends Handler {
    private Context context;
    private ImageView imv;
    public OssHandler(Context context,ImageView imv)
    {
        this.context=context;
        this.imv=imv;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what)
        {

            case ConfigOfOssClient.WHAT_SUCCESS_DOWNLOAD:
                //Bitmap bitmap = (Bitmap) msg.obj;
                 try {
                    Bitmap bitmap  = BitmapFactory.decodeFile((String) msg.obj);
                     imv.setImageBitmap(bitmap);
                     String success_download = "成功下载";
                     Toast.makeText(context, success_download, Toast.LENGTH_SHORT).show();
                 }catch (Exception e){
                     Toast.makeText(context, "服务器繁忙", Toast.LENGTH_SHORT).show();
                 }


                break;
            case ConfigOfOssClient.WHAT_FAILED_DOWNLOAD:
                String failed_download = (String) msg.obj;
                Toast.makeText(context, failed_download, Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }
        super.handleMessage(msg);
    }
}

