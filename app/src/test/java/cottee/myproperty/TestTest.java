package cottee.myproperty;

import org.junit.*;

import java.io.IOException;

import cottee.myproperty.constant.Properties;
import cottee.myproperty.uitils.Session;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/3/18.
 */
public class TestTest {
    @org.junit.Test
    public void test1() throws Exception {
        new Thread(){
            @Override
            public void run() {
                String session = Session.getSession();
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder().add("session",session).build();
                Request request = new Request.Builder().url(Properties.SUB_LIST_PATH).post(requestBody).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        };

    }

}