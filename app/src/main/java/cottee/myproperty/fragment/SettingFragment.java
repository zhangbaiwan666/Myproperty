package cottee.myproperty.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.activity.ControlSubActivity;
import cottee.myproperty.activity.LoginActivity;
import cottee.myproperty.activity.PayFeeRecordActivity;
import cottee.myproperty.activity.RepairRecordActivity;
import cottee.myproperty.activity.SettingActivity;
import cottee.myproperty.activity.ViewHouseAcivity;
import cottee.myproperty.constant.BullentinBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.HealthMap;

public class SettingFragment extends Fragment {

	private View rl_house_control;
	private View rl_login_out;
	private View rl_view_house;
	private boolean click=true;
	private TextView repair_record;
	private RelativeLayout rl_repair_record;
	private RelativeLayout rl_pay_record;
	private boolean clicked=true;
	private static  List<BullentinBean> bullentin_list;


	@SuppressLint("WrongViewCast")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View inflate = inflater.inflate(R.layout.fragment_setting, null);
		Intent intent = getActivity().getIntent();


		int property_name = intent.getIntExtra("property_name", 0);
		System.out.println("传到FragmentSetting的property_name为"+property_name);
		repair_record = (TextView)inflate.findViewById(R.id.repair_record);
		rl_pay_record = inflate.findViewById(R.id.rl_pay_record);
		rl_pay_record.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent1=new Intent(getContext(), PayFeeRecordActivity.class);
				startActivity(intent1);
			}
		});
		rl_repair_record = inflate.findViewById(R.id.rl_repair_record);
		rl_repair_record.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(getContext(), RepairRecordActivity.class);
				getActivity().startActivity(intent);

			}
		});
		rl_house_control = inflate.findViewById(R.id.rl_house_control);
		rl_view_house = inflate.findViewById(R.id.rl_view_house);
		rl_login_out = inflate.findViewById(R.id.rl_login_out);

		rl_house_control.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (clicked) {
					Intent intent = new Intent(getContext(), ControlSubActivity.class);
					startActivity(intent);
					clicked=false;

				}
			}
		});
//		rl_view_house.setOnClickListener(new NoDoubleClickListener() {
//			@Override
//			protected void onNoDoubleClick(View v) {
//				Intent intent1 = new Intent(getActivity(), ViewHouseAcivity.class);
//				startActivity(intent1);
//			}
//		});
		rl_login_out.setOnClickListener(new NoDoubleClickListener() {
			@Override
			protected void onNoDoubleClick(View v) {
				Intent intent1 = new Intent(getContext(), SettingActivity.class);
				getActivity().startActivity(intent1);
			}
		});
		return inflate;

	}
	private static volatile SettingFragment instance=null;
	private static SettingFragment getInstance(){
		synchronized(Intent.class){
			if(instance==null){

			}
		}
		return instance;
	}

	@Override
	public void onPause() {
		super.onPause();
		clicked=true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		clicked=true;
	}
}