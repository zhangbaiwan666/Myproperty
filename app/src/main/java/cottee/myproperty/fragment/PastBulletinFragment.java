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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.BullentinBean;
import cottee.myproperty.constant.BullentinInfo;
import cottee.myproperty.constant.SubInfo;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.listener.OnRefreshListener;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.widgets.RefreshListView;


public class PastBulletinFragment extends Fragment implements OnRefreshListener {

    private List<BullentinInfo> textList;
    private TabFragmentAdapter adapter;
    private RefreshListView rListView;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_pastbulletin, null);
        rListView = (RefreshListView) rootView.findViewById(R.id.past_refreshlv);
        //假数据
        textList = new ArrayList<BullentinInfo>();
        BullentinInfo bullentinBean1 = new BullentinInfo();
        bullentinBean1.setTitle("一周前");
        bullentinBean1.setCreate_time("12:00");
        bullentinBean1.setOutline("库鲁猛谁，库鲁懵哈，库鲁懵谁懵谁哈啊");
        textList.add(bullentinBean1);
        BullentinInfo bullentinBean2 = new BullentinInfo();
        bullentinBean2.setTitle("一周前");
        bullentinBean2.setCreate_time("12:00");
        bullentinBean2.setOutline("库鲁猛谁，库鲁懵哈，库鲁懵谁懵谁哈啊");
        textList.add(bullentinBean2);
        BullentinInfo bullentinBean3 = new BullentinInfo();
        bullentinBean3.setTitle("一周前");
        bullentinBean3.setCreate_time("12:00");
        bullentinBean3.setOutline("库鲁猛谁，库鲁懵哈，库鲁懵谁懵谁哈啊");
        textList.add(bullentinBean3);
        adapter = new TabFragmentAdapter(getActivity(),R.layout.layout_bulletin_list,textList);
        rListView.setAdapter(adapter);
        rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "你点击了Listview"+(i-1), Toast.LENGTH_SHORT).show();
            }
        });
        rListView.setOnRefreshListener(this);
        return rootView;
    }
    public static class TabFragmentAdapter extends ArrayAdapter<BullentinInfo> {

        class ViewHolder//用来暂存，避免每次都重新加载布局，优化程序的流畅度
        {
            TextView title;
            TextView time;
            TextView message;

        }
        public TabFragmentAdapter(Context context, int textViewResourceId, List<BullentinInfo> objects){
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final BullentinInfo bullentinBean = getItem(position);
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

//            TextView textView = new TextView(getContext());
//            textView.setText(textList.get(position));
//            textView.setTextColor(Color.BLACK);
//            textView.setTextSize(18.0f);
            return view;
        }

    }

    @Override
    public void onDownPullRefresh() {
        rListView.setOnItemClickListener(null);
        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(getActivity(), "", "");
        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(getActivity(), loginRegisterHandler);
        loginRegisterManager.ShowExceptNotice("0");
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                for (int i = 0; i < 2; i++) {
                    BullentinInfo bullentinBean1 = new BullentinInfo();
                    bullentinBean1.setTitle("最新公告");
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
                rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "你点击了Listview"+(i-1), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.execute(new Void[] {});
    }


    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(3000);
                BullentinInfo bullentinBean1 = new BullentinInfo();
                bullentinBean1.setTitle("更多公告");
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
