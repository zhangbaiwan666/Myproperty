package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cottee.myproperty.R;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.view.RatingBar;

public class EvaluateActivity extends Activity {

    private Button btn_evalute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        RatingBar star=(RatingBar)findViewById(R.id.star);
        star.setClickable(true);
        try {
            btn_evalute = (Button)findViewById(R.id.btn_evalute);
            btn_evalute.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent =new Intent(EvaluateActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
//    public void  Sure(View view){
//
//    }
    public void back(View view){
        finish();
    }
}
