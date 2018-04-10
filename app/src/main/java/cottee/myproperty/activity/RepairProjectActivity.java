package cottee.myproperty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.handler.RepairHandler;
import cottee.myproperty.manager.RepairManager;

public class RepairProjectActivity extends Activity {
    private ListView lv_left;
    private ListView lv_Right;
    private TextView tv_title;
    private ProgressBar pb_repairProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_project);
        lv_left=(ListView)findViewById(R.id.lv_left) ;
        lv_Right=(ListView)findViewById(R.id.lv_Right) ;
        tv_title=(TextView)findViewById(R.id.tv_title) ;
        try {
            pb_repairProject = (ProgressBar) findViewById(R.id.pb_repairProject);

            RepairHandler repairHandler = new RepairHandler(this, lv_left, lv_Right, tv_title, pb_repairProject);
            RepairManager repairManager = new RepairManager(repairHandler);
            repairManager.sendRequestRepairProject();
            if (lv_Right == null || lv_left == null) {
                pb_repairProject.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  public void back(View view){
        finish();
}


}
