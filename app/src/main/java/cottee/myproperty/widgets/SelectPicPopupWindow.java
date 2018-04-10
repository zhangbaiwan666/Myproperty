package cottee.myproperty.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.adapter.PopSubPhoneAdapter;
import cottee.myproperty.adapter.SubPhoneAdapter;

public class SelectPicPopupWindow extends PopupWindow {


    private Button  btn_cancel;
    private ListView lv_call_sub_phone;
    private View mMenuView;

    public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick, List list) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_layout,null);
        lv_call_sub_phone = (ListView) mMenuView.findViewById(R.id.lv_call_sub_phone);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        //取消按钮  
        btn_cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框  
                dismiss();
            }
        });
        PopSubPhoneAdapter popSubPhoneAdapter = new PopSubPhoneAdapter(context, R.layout.layout_item_sub_phone, list);
        lv_call_sub_phone.setAdapter(popSubPhoneAdapter);
        popSubPhoneAdapter.notifyDataSetChanged();
        //设置按钮监听
        //设置SelectPicPopupWindow的View  
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        int height = btn_cancel.getHeight();
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果  
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景  
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

}