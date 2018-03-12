package cottee.myproperty.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.RepairProject;


public class LeftAdapter extends BaseAdapter {

	private Context context;
	List<RepairProject.ProinfoBean> list;
	public LeftAdapter(Context context, List list) {
		super();
		this.context = context;
		this.list=list;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (list != null) {
			count = list.size();
		}
		return count;
	}

	@Override
	public Object getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHold vh = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_left, null);
			vh = new ViewHold();
			convertView.setTag(vh);
			vh.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
		} else {
			vh = (ViewHold) convertView.getTag();
		}
		vh.tv_left.setTag(position);
		vh.tv_left.setText(list.get(position).getKind());
		return convertView;
	}

	public class ViewHold {
		TextView tv_left;
		
	}

}
