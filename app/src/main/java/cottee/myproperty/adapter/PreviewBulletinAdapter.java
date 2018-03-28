package cottee.myproperty.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.activity.BullDetailsActivity;
import cottee.myproperty.constant.BullentinBean;
import cottee.myproperty.constant.BullentinInfo;
import cottee.myproperty.constant.SubInfo;
import cottee.myproperty.listener.NoDoubleClickListener;

/**
 * Created by Administrator on 2018/3/7.
 */

public class PreviewBulletinAdapter extends ArrayAdapter<BullentinInfo> {
    public PreviewBulletinAdapter(Context context, int textViewResourceId, List<BullentinInfo>
            objects) {
        super(context,textViewResourceId,objects);

    }
    class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
    {
        TextView bulletin_title;
        TextView bulletin_time;
        TextView bulletin_message;
        TextView bulletin_flags;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BullentinInfo bullentinBean = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null)//如果布局从来没有被加载过
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bulletin_list, null);//使用布局填充器来把fruit_item布局文件转为View
            viewHolder = new ViewHolder();//新建一个ViewHolder
            viewHolder.bulletin_title = view.findViewById(R.id.tv_bulletin_title);//从View中获取ImageView，并暂存新建的ViewHolder中
            viewHolder.bulletin_time = view.findViewById(R.id.tv_bulletin_time);//从View中获取TextView，并暂存新建的ViewHolder中
            viewHolder.bulletin_message = view.findViewById(R.id.tv_bulletin_message);
            viewHolder.bulletin_flags = view.findViewById(R.id.tv_bulletin_flags);
            //对于item得操作
            view.setTag(viewHolder);//使用setTag把查找的view缓存起来方便多次重用
        } else//布局被加载过
        {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();//把之前暂存的ViewHolder赋给viewHolder
        }
        viewHolder.bulletin_title.setText(bullentinBean.getTitle());
        viewHolder.bulletin_time.setText(bullentinBean.getCreate_time());
        viewHolder.bulletin_message.setText(bullentinBean.getOutline());
        viewHolder.bulletin_flags.setText(bullentinBean.getNotice_id());
        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(getContext(), BullDetailsActivity.class);
                intent.putExtra("bullentin_time",getItem(position).getCreate_time());
                intent.putExtra("bullentin_title",getItem(position).getTitle());
                intent.putExtra("bullentin_outline",getItem(position).getOutline());
                intent.putExtra("bullentin_id",getItem(position).getNotice_id());
                getContext().startActivity(intent);
            }
        });
        return view;
    }

}
