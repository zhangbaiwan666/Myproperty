package cottee.myproperty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.RepairRecord;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class RepairRecordAdapter extends BaseAdapter {
    Context context;
    List<RepairRecord.ListBean> listBeans;
    public RepairRecordAdapter(Context context,List list) {
        this.context = context;
        this.listBeans =list;
    }


    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout
                            .repair_record
                    , viewGroup, false);
            vh = new ViewHold();
            view.setTag(vh);
            vh.repair_order=(TextView)view.findViewById(R.id.repair_order);
            vh.order_time=(TextView)view.findViewById(R.id.order_time);
        }else {
            vh = (ViewHold) view.getTag();
        }
        vh.repair_order.setText(listBeans.get(i).getOrder_id());
        vh.order_time.setText(listBeans.get(i).getC_time());
        return view;
    }
    public class ViewHold{
        TextView repair_order;
        TextView order_time;

    }
}


