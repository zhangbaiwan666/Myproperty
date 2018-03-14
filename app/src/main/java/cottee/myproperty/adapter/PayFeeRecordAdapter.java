package cottee.myproperty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cottee.myproperty.R;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class PayFeeRecordAdapter extends BaseAdapter {
    Context context;
 public    PayFeeRecordAdapter(Context context){
     this.context=context;
 }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout
                            .payfee_record, viewGroup, false);

        }
        return view;
    }

}
