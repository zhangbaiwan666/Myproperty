package cottee.myproperty.fragment;

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
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.activity.RepairRecordActivity;
import cottee.myproperty.activity.ControlSubActivity;
import cottee.myproperty.activity.LoginActivity;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.Session;

public class SettingFragment extends Fragment {

	private View rl_house_control;
	private View rl_login_out;
	private boolean click=true;
	private TextView repair_record;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_setting, null);
		Intent intent = getActivity().getIntent();
		int property_name = intent.getIntExtra("property_name", 0);
		System.out.println("传到FragmentSetting的property_name为"+property_name);
		repair_record = (TextView)inflate.findViewById(R.id.repair_record);
		repair_record.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(getContext(), RepairRecordActivity.class);
				getActivity().startActivity(intent);

			}
		});
		rl_house_control = inflate.findViewById(R.id.rl_house_control);
		rl_login_out = inflate.findViewById(R.id.rl_login_out);

		rl_house_control.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(getActivity(), "","");
				LoginRegisterManager loginRegisterManager = new LoginRegisterManager(getActivity(), loginRegisterHandler);
				loginRegisterManager.GsonSubList();

			}
		});
		rl_login_out.setOnClickListener(new NoDoubleClickListener() {
			@Override
			protected void onNoDoubleClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setMessage("确定要退出吗")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SharedPreferences preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
								String email=preferences.getString("name", "");
								String password=preferences.getString("psword", "");
								Intent intent = new Intent(getActivity(), LoginActivity.class);
								intent.putExtra("name",email);
								intent.putExtra("psword",password);
								startActivity(intent);
								dialog.dismiss();
//                                    ((BaseActivity)getActivity()).goNextAnim();
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.create()
						.show();
			}
		});
		return inflate;

	}


}