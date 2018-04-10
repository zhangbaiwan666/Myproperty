package cottee.myproperty.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cottee.myproperty.R;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.uitils.CustomDialog;

public class RepairPaymentActivity extends Activity {

    private Button btn_repairPay;
    private EditText et_inputFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairpayment);
        btn_repairPay = (Button) findViewById(R.id.btn_repairPay);
        et_inputFee = (EditText)findViewById(R.id.et_inputFee);
        try {
            btn_repairPay.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (TextUtils.isEmpty(et_inputFee.getText())){
                        Toast.makeText(RepairPaymentActivity.this,"金额不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        CustomDialog.Builder builder = new CustomDialog.Builder(RepairPaymentActivity.this);
                        builder.setMessage("您确定要缴费吗");
                        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                            }
                        });

                        builder.setNegativeButton("是",
                                new android.content.DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Toast.makeText(RepairPaymentActivity.this,"缴费成功",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RepairPaymentActivity.this, EvaluateActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });

                        builder.create().show();

                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



    }

//    public void Sure(View view){
//
//    }
    public void back(View view){
        finish();
    }
}
