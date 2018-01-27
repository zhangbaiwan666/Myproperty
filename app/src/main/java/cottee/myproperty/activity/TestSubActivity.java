package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.PropertyBean;
import cottee.myproperty.constant.SubListBean;

public class TestSubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub);
        final Intent intent = getIntent();
        ArrayList<String> sub_remark_list = intent.getStringArrayListExtra("sub_remark_list");
        System.out.println("TestSubActivity"+sub_remark_list);
//        for (SubListBean app : list) {
//            String user_id = app.getUser_id();
//            System.out.println("用户id测试"+user_id);
//        }
        }
    }

