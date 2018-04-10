package cottee.myproperty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.PayFeeRecord;
import cottee.myproperty.view.MyListview;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class PayFeeRecordAdapter extends BaseAdapter {
    Context context;
    List<PayFeeRecord.ListBean> listBeans;
 public    PayFeeRecordAdapter(Context context,List listBeans){
     this.context=context;
     this.listBeans=listBeans;
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
                                .payfee_record
                        , viewGroup, false);
                vh = new ViewHold();
                view.setTag(vh);
                vh.tv_feeType = (TextView) view.findViewById(R.id.tv_feeType);
                vh.tv_feeAmount = (TextView) view.findViewById(R.id.tv_feeAmount);
                vh.tv_payTime = (TextView) view.findViewById(R.id.tv_payTime);

            } else {
                vh = (ViewHold) view.getTag();
            }
        if(((MyListview) viewGroup).isOnMeasure){
            //如果是onMeasure调用的就立即返回

            return view;
        }
        System.out.println("bbbbbbbbbbbbbbbbbbb getview");
        vh.tv_feeAmount.setText("￥"+listBeans.get(i).getMoney());
            vh.tv_feeType.setText(listBeans.get(i).getType());
            vh.tv_payTime.setText(listBeans.get(i).getC_time());

            return view;

    }
    public class ViewHold{
        TextView tv_feeType;
        TextView tv_feeAmount;
        TextView tv_payTime;

    }
}
