package cottee.myproperty.server;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cottee.myproperty.uitils.StreamTool;

public class ImageService {
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    @SuppressLint("NewApi")
    public static byte[] getImage(String path) throws IOException {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");  //设置请求方法为GET
        conn.setReadTimeout(5*1000);  //设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream();  //通过输入流获得图片数据
        byte[] data = StreamTool.readInputStream(inputStream);   //获得图片的二进制数据
        return data;

    }
}