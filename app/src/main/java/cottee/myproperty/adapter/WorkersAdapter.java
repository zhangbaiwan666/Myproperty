package cottee.myproperty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.Mechanic;
import cottee.myproperty.uitils.NormalLoadPicture;
import cottee.myproperty.view.StarLinearLayout;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class WorkersAdapter extends BaseAdapter {
    Context context;
    List<Mechanic.ProjectStaffBean> list;
    public WorkersAdapter(Context context,List list) {
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        return count;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold=null;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout
                            .technician_information
                    , viewGroup, false);
            viewHold=new ViewHold();
            view.setTag(viewHold);
            viewHold.tv_jigong=(TextView)view.findViewById(R.id.tv_jigong);
            viewHold.imv_photo=(ImageView)view.findViewById(R.id.imv_photo);
            viewHold.starLinearLayout=(StarLinearLayout) view.findViewById(R.id.starBar);

        }else {
            viewHold=(ViewHold)view.getTag();
        }
        viewHold.tv_jigong.setText(list.get(i).getName());
        new NormalLoadPicture().getPicture(list.get(i).getPhoto(),viewHold.imv_photo);
        String grade= list.get(i).getGrade();
        String time=list.get(i).getTime();
       viewHold.starLinearLayout.setScore(Float.parseFloat(grade)/Float.parseFloat(time));
       if (list.size()==0){
           viewHold.tv_jigong.setText("此项目没有技工");
       }
        return view;
    }
    public class ViewHold {
        TextView tv_jigong;
        ImageView imv_photo;
        StarLinearLayout starLinearLayout;
    }
}
