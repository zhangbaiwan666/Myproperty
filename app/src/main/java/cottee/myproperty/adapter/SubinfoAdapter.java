package cottee.myproperty.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.SubInfo;
import cottee.myproperty.constant.SubListBean;
import cottee.myproperty.constant.SubPhoneBean;
import cottee.myproperty.widgets.SelectPicPopupWindow;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SubinfoAdapter extends ArrayAdapter<SubListBean> {

    private SelectPicPopupWindow menuWindow;

    class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
    {
        TextView sub_name;
        TextView sub_phone;
        Button btn_call_phone;
    }

    public SubinfoAdapter(Context context, int textViewResourceId, List<SubListBean>
            objects) {
        super(context,textViewResourceId,objects);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final SubListBean subInfo = getItem(position);//实例化在ArrayList<SubListBean>中第“position”个当前Fruit对象
        View view;
        final ViewHolder viewHolder;
        if (convertView == null)//如果布局从来没有被加载过
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item, null);//使用布局填充器来把fruit_item布局文件转为View
            viewHolder = new ViewHolder();//新建一个ViewHolder
            viewHolder.sub_name = (TextView) view.findViewById(R.id.tv_sub_name);//从View中获取ImageView，并暂存新建的ViewHolder中
            viewHolder.sub_phone = (TextView) view.findViewById(R.id.tv_sub_phone);//从View中获取TextView，并暂存新建的ViewHolder中
            viewHolder.btn_call_phone = view.findViewById(R.id.btn_call_phone);
            //对于item得操作

            view.setTag(viewHolder);//使用setTag把查找的view缓存起来方便多次重用
        } else//布局被加载过
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//把之前暂存的ViewHolder赋给viewHolder
        }

        viewHolder.sub_name.setText(subInfo.getRemark());
        String phone_num = subInfo.getPhone_num();
        String str2=phone_num.replace(" ", "");//去掉所用空格
        List list = Arrays.asList(str2.split(","));
        viewHolder.sub_phone.setText((String)list.get(0));
        viewHolder.btn_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_num = subInfo.getPhone_num();
                String str2=phone_num.replace(" ", "");//去掉所用空格
                List list = Arrays.asList(str2.split(","));
                if (list.size()>1) {
                    menuWindow = new SelectPicPopupWindow((Activity) getContext(), itemsOnClick, list);
                    //显示窗口
                    menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+(String)list.get(0)));
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
            }
        });


        return view;
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                default:
                    break;
            }


        }

    };

}

