package cottee.myproperty.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.BullentinBean;
import cottee.myproperty.constant.BullentinFindInfo;
import cottee.myproperty.listener.OnRefreshListener;
import cottee.myproperty.widgets.RefreshListView;

/**
 * Created by Administrator on 2018/1/19.
 */

public class SearchBulletionFragment extends Fragment implements OnRefreshListener {
    private List<BullentinFindInfo> textList;
    private TabFragmentAdapter adapter;
    private RefreshListView rListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_searchbulletin, null);
        rListView = (RefreshListView) rootView.findViewById(R.id.search_refreshlv);
        //假数据
        textList = new ArrayList<BullentinFindInfo>();
//        for (int i = 0; i < 5; i++) {
//            BullentinFindInfo bullentinBean1 = new BullentinFindInfo();
//            bullentinBean1.setTitle("查询结果公告");
//            bullentinBean1.setCreate_time("12:00");
//            bullentinBean1.setOutline("库鲁猛谁，库鲁懵哈，库鲁懵谁懵谁哈啊");
//            textList.add(bullentinBean1);
//        }
        adapter = new TabFragmentAdapter(getActivity(),R.layout.layout_bulletin_list,textList);
        rListView.setAdapter(adapter);
//        rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "你点击了Listview"+(i-1), Toast.LENGTH_SHORT).show();
//            }
//        });
        rListView.setOnRefreshListener(this);
        return rootView;
    }
    public static class TabFragmentAdapter extends ArrayAdapter<BullentinFindInfo> {

        public TabFragmentAdapter(Context context, int textViewResourceId, List<BullentinFindInfo>
                objects) {
            super(context, textViewResourceId, objects);
        }
        class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
        {
            TextView title;
            TextView time;
            TextView message;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final BullentinFindInfo bullentinBean = getItem(position);
            View view;
            final ViewHolder viewHolder;
            // TODO Auto-generated method stub
            if (convertView == null)//如果布局从来没有被加载过
            {
                view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bulletin_list, null);//使用布局填充器来把fruit_item布局文件转为View
                viewHolder = new ViewHolder();//新建一个ViewHolder
                viewHolder.title = view.findViewById(R.id.tv_bulletin_title);//从View中获取ImageView，并暂存新建的ViewHolder中
                viewHolder.time = view.findViewById(R.id.tv_bulletin_time);//从View中获取TextView，并暂存新建的ViewHolder中
                viewHolder.message = view.findViewById(R.id.tv_bulletin_message);
                //对于item得操作

                view.setTag(viewHolder);//使用setTag把查找的view缓存起来方便多次重用
            } else//布局被加载过
            {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();//把之前暂存的ViewHolder赋给viewHolder
            }
            viewHolder.time.setText(bullentinBean.getCreate_time());
            viewHolder.message.setText(bullentinBean.getOutline());
            viewHolder.title.setText(bullentinBean.getTitle());

            return view;
        }

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                for (int i = 0; i < 2; i++) {
                    BullentinFindInfo bullentinBean1 = new BullentinFindInfo();
                    bullentinBean1.setTitle("新加载");
                    bullentinBean1.setCreate_time("12:00");
                    bullentinBean1.setOutline("库鲁猛谁，库鲁懵哈，库鲁懵谁懵谁哈啊");
                    textList.add(bullentinBean1);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                rListView.hideHeaderView();
            }
        }.execute(new Void[] {});
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(5000);

                BullentinFindInfo bullentinBean1 = new BullentinFindInfo();
                bullentinBean1.setTitle("更多");
                bullentinBean1.setCreate_time("12:00");
                bullentinBean1.setOutline("库鲁猛谁，库鲁懵哈，库鲁懵谁懵谁哈啊");
                textList.add(bullentinBean1);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();

                rListView.hideFooterView();
            }
        }.execute(new Void[] {});
    }
}
