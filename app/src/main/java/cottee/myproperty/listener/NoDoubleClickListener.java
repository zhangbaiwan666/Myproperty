package cottee.myproperty.listener;


import android.content.DialogInterface;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/1/4.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);

        }
    }
    protected abstract void onNoDoubleClick(View v);
}
