package cottee.myproperty.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.listener.OnRefreshListener;
import cottee.myproperty.widgets.RefreshListView;


public class RecentBulletinFragment extends Fragment implements OnRefreshListener {

    private List<String> textList;
    private TabFragmentAdapter adapter;
    private RefreshListView rListView;

    public static RecentBulletinFragment newInstance() {
        RecentBulletinFragment fragment = new RecentBulletinFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recentbulletin, null);
        rListView = (RefreshListView) rootView.findViewById(R.id.refreshlistview);
        //假数据
        textList = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            textList.add("Listview" + i);
        }
        adapter = new RecentBulletinFragment.TabFragmentAdapter();
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
    private class TabFragmentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView textView = new TextView(getContext());
            textView.setText(textList.get(position));
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18.0f);
            return textView;
        }

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                for (int i = 0; i < 2; i++) {
                    textList.add(0, "ok" + i);
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

                textList.add("11");
                textList.add("22");
                textList.add("33");
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
