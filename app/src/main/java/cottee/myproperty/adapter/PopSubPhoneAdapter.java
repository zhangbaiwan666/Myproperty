package cottee.myproperty.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.listener.NoDoubleClickListener;

/**
 * Created by Administrator on 2018/3/14.
 */

public class PopSubPhoneAdapter extends ArrayAdapter<String> {
    private List<String> sub_phone_list;
    public PopSubPhoneAdapter(Context context, int textViewResourceId, List<String>
            objects) {
        super(context,textViewResourceId,objects);
        sub_phone_list=objects;

    } class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
    {
        TextView tv_sub_phone;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String phone = sub_phone_list.get(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null)//如果布局从来没有被加载过
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_call_sub_phone_item, null);//使用布局填充器来把fruit_item布局文件转为View
            viewHolder = new ViewHolder();//新建一个ViewHolder
            viewHolder.tv_sub_phone = view.findViewById(R.id.tv_call_sub_phone);//从View中获取ImageView，并暂存新建的ViewHolder中
            //对于item得操作

            view.setTag(viewHolder);//使用setTag把查找的view缓存起来方便多次重用
        } else//布局被加载过
        {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();//把之前暂存的ViewHolder赋给viewHolder

        }
        viewHolder.tv_sub_phone.setText(phone);

        view.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+sub_phone_list.get(position).trim()));
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                  getContext().startActivity(intent);
            }
        });
        return view;
    }
}
