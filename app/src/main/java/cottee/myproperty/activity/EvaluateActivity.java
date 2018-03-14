package cottee.myproperty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cottee.myproperty.R;
import cottee.myproperty.view.RatingBar;

public class EvaluateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        RatingBar star=(RatingBar)findViewById(R.id.star);
        star.setClickable(true);

    }
    public void  Sure(View view){
        Intent intent =new Intent(EvaluateActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void back(View view){
        finish();
    }
}
