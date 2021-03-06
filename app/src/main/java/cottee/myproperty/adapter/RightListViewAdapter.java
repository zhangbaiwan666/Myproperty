package cottee.myproperty.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.BaseData;
import cottee.myproperty.constant.RepairProject;
import cottee.myproperty.uitils.AsyncImageLoader;
import cottee.myproperty.uitils.NormalLoadPicture;


public class RightListViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<BaseData> data = new ArrayList<BaseData>();
	List<RepairProject.ProinfoBean> list;
	private ListView lv_right;
	private AsyncImageLoader asyncImageLoader;
	private String image;

	public RightListViewAdapter(Context context, List list,ListView lv_right) {
		super();
		this.context = context;
		this.list=list;
		this.lv_right=lv_right;
		asyncImageLoader = new AsyncImageLoader();

	}



	public void updateData(ArrayList<BaseData> lists) {
		data.clear();
		data.addAll(lists);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Object getItem(int i) {
		return data.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout
							.item_right
					, parent, false);
			vh = new ViewHold();

			vh.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			vh.tv_right = (TextView) convertView.findViewById(R.id.tv_right);
			vh.imv_right=(ImageView)convertView.findViewById(R.id.imv);
			convertView.setTag(vh);
			image = data.get(position).getUrl();

		}
			vh = (ViewHold) convertView.getTag();
			System.out.println("rrrrrrrrrrrrrrrrrright");

		vh.imv_right.setTag(image);
		new NormalLoadPicture().getPicture(image,vh.imv_right);
//		Drawable cachedImage = asyncImageLoader.loadDrawable(image, new
//				AsyncImageLoader.ImageCallback() {
//					public void imageLoaded(Drawable imageDrawable, String
//							imageUrl) {
//						ImageView imageViewByTag = (ImageView) lv_right
//								.findViewWithTag(imageUrl);
//						if (imageViewByTag != null) {
//							imageViewByTag.setImageDrawable(imageDrawable);
//						}
//					}
//				});
//		vh.imv_right.setImageDrawable(cachedImage);

		vh.tv_content.setText(data.get(position).getName());
		if (position == 0) {
			vh.tv_right.setVisibility(View.VISIBLE);
			vh.tv_right.setText(data.get(position).getTitle());
		} else if (!TextUtils.equals(data.get(position).getTitle(),
				data.get(position - 1).getTitle())) {
			vh.tv_right.setVisibility(View.VISIBLE);
			vh.tv_right.setText(data.get(position).getTitle());
		} else {
			vh.tv_right.setVisibility(View.GONE);
		}


		return convertView;
	}

	public class ViewHold {
		TextView tv_content;
		TextView tv_right;
		ImageView imv_right;
	}

}
