package cottee.myproperty.uitils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class Properties extends Application {
    private  static Context context;

    @Override
    public void onCreate() {
       context=getApplicationContext();
    }
    public static Context getContext()
    {
        return  context;
    }
}
