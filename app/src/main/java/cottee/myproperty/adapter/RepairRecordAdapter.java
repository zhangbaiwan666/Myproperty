package cottee.myproperty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cottee.myproperty.R;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class RepairRecordAdapter extends BaseAdapter {
    Context context;

    public RepairRecordAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 8;
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
                            .repair_record
                    , viewGroup, false);


        }
        return view;
    }
}


