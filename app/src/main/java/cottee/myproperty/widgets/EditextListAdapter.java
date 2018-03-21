package cottee.myproperty.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.ItemBean;

/**
 * Created by z on 2016/8/17 0017.
 */
public class EditextListAdapter extends BaseAdapter {

    private List<ItemBean> mData;
    private Context mContext;

    public EditextListAdapter(Context mContext, List<ItemBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_edittext, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ItemBean itemObj = mData.get(position);
        //This is important. Remove TextWatcher first.
        if (holder.editText.getTag() instanceof TextWatcher) {
            holder.editText.removeTextChangedListener((TextWatcher) holder.editText.getTag());
        }

        holder.editText.setText(itemObj.getText());
        if (position==0){
            holder.btn_delete_sub_phone.setVisibility(View.GONE);
        }
        final ViewHolder finalHolder = holder;
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.setText("");
                } else{
                    itemObj.setText(finalHolder.editText.getText().toString().trim());
                }

            }
        };
        holder.editText.addTextChangedListener(watcher);
        holder.editText.setTag(watcher);
        holder.btn_delete_sub_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(mData.get(position));
                notifyDataSetInvalidated();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private EditText editText;
        private Button btn_delete_sub_phone;

        public ViewHolder(View convertView) {
            editText = (EditText) convertView.findViewById(R.id.edit_text);
            btn_delete_sub_phone = (Button) convertView.findViewById(R.id.btn_delete_sub_phone);
        }
    }
}
