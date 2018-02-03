package cottee.myproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cottee.myproperty.R;
import cottee.myproperty.activity.RepairRecordActivity;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.listener.NoDoubleClickListener;
import cottee.myproperty.manager.LoginRegisterManager;

public class SettingFragment extends Fragment {

	private View rl_house_control;
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

		rl_house_control.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(getActivity(), "","");
				LoginRegisterManager loginRegisterManager = new LoginRegisterManager(getActivity(), loginRegisterHandler);
//				loginRegisterManager.GsonProperyt();
				loginRegisterManager.GsonSubList();
			}
		});
		return inflate;

	}


}