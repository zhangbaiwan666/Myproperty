package cottee.myproperty.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.PropertyListBean;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ChoosePropertyAdapter extends ArrayAdapter<PropertyListBean> {

    class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
    {
        TextView property_name;
    }
    public ChoosePropertyAdapter(@NonNull Context context, int resource, List<PropertyListBean> objects) {
        super(context, resource,objects);
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PropertyListBean pro_Info = getItem(position);//实例化在ArrayList<SubListBean>中第“position”个当前Fruit对象

        View view;
        final ChoosePropertyAdapter.ViewHolder viewHolder;
        if (convertView == null)//如果布局从来没有被加载过
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pop_menuitem, null);//使用布局填充器来把fruit_item布局文件转为View
             viewHolder = new ChoosePropertyAdapter.ViewHolder();//新建一个ViewHolder
            viewHolder.property_name = (TextView) view.findViewById(R.id.menuitem);//从View中获取ImageView，并暂存新建的ViewHolder中
            //对于item得操作

            view.setTag(viewHolder);//使用setTag把查找的view缓存起来方便多次重用
        } else//布局被加载过
        {
            view = convertView;
            viewHolder = (ChoosePropertyAdapter.ViewHolder) view.getTag();//把之前暂存的ViewHolder赋给viewHolder
        }
        viewHolder.property_name.setText(pro_Info.getName());

        return view;
    }
}
