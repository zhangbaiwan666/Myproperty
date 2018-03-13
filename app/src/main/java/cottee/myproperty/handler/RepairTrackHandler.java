package cottee.myproperty.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.RepairTrack;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class RepairTrackHandler extends Handler {
    private TextView tv_trackOfWorker;
    private  TextView tv_serProjectOftrack;
    private  TextView tv_baoxiuxinxi;
    private  TextView repair_time;
    public RepairTrackHandler(Context context, TextView tv_trackOfWorker, TextView tv_serProjectOftrack, TextView tv_baoxiuxinxi,
                         TextView repair_time){
        this.tv_trackOfWorker=tv_trackOfWorker;
        this.tv_serProjectOftrack=tv_serProjectOftrack;
        this.tv_baoxiuxinxi=tv_baoxiuxinxi;
        this.repair_time=repair_time;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case  Properties.RepairTrack:{
                RepairTrack.IndentBean indentBean= (RepairTrack.IndentBean) msg.obj;
                System.out.println(indentBean.getPart());
                tv_baoxiuxinxi.setText(indentBean.getOrder().get(0).getState());
                repair_time.setText(indentBean.getOrder().get(0).getC_time());
                 tv_trackOfWorker.setText(indentBean.getPart());
                 tv_serProjectOftrack.setText(indentBean.getStaff_name());
            }


        }


    }
}
