package cottee.myproperty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.SubPhoneBean;

/**
 * Created by Administrator on 2018/3/14.
 */

public class SubPhoneAdapter extends ArrayAdapter<SubPhoneBean> {
    public SubPhoneAdapter(Context context, int textViewResourceId, List<SubPhoneBean>
            objects) {
        super(context,textViewResourceId,objects);

    }
    class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
    {
        TextView sub_phone;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SubPhoneBean subPhoneBean = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null)//如果布局从来没有被加载过
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_sub_phone, null);//使用布局填充器来把fruit_item布局文件转为View
            viewHolder = new ViewHolder();//新建一个ViewHolder
            viewHolder.sub_phone = view.findViewById(R.id.tv_item_sub_phone);//从View中获取ImageView，并暂存新建的ViewHolder中
            //对于item得操作

            view.setTag(viewHolder);//使用setTag把查找的view缓存起来方便多次重用
        } else//布局被加载过
        {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();//把之前暂存的ViewHolder赋给viewHolder

        }
        viewHolder.sub_phone.setText(subPhoneBean.getPhone());

        return view;
    }
}
