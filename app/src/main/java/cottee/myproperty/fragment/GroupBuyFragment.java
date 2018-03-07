package cottee.myproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cottee.myproperty.R;
import cottee.myproperty.activity.PropertyAdActivity;
import cottee.myproperty.listener.NoDoubleClickListener;

/**
 * Created by Administrator on 2018/1/16.
 */

public class GroupBuyFragment extends Fragment {
    private View inflate;
    private ScheduledExecutorService scheduledExecutorService;
    private int imageIds[];
    private String[] titles;
    private ArrayList<ImageView> images;
    private ArrayList<View> dots;
    private TextView title;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View rootView;
    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem; //当前页面

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_groupby, null);
            pop();
        }ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }
    private void pop() {
        //TODO 增加切换图片数量，来自服务器；在小区团购中增加物业pop用于物业宣传
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        //每隔5秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);
        imageIds = new int[]{
                R.mipmap.splash,
                R.mipmap.ad2,
                R.mipmap.ad3,
                R.mipmap.ad4,
                R.mipmap.ad5,
                R.mipmap.ad2,
                R.mipmap.ad3,
                R.mipmap.ad4

        };
        //图片标题
        titles = new String[]{
                "全新版本爱尚家上线啦",
                "物业广告位1",
                "物业广告位2",
                "物业广告位3",
                "物业广告位4",
                "物业广告位5",
                "物业广告位6",
                "物业广告位7"

        };

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i =0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(imageIds[i]);
            final int finalI = i;
            imageView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent = new Intent(getContext(), PropertyAdActivity.class);
                    intent.putExtra("Ad_numb",finalI);
                    intent.putExtra("Ad_name",titles[finalI]);
                    startActivity(intent);
                }
            });
            images.add(imageView);
        }

        //显示的点
        dots = new ArrayList<View>();
        dots.add(rootView.findViewById(R.id.dot_0));
        dots.add(rootView.findViewById(R.id.dot_1));
        dots.add(rootView.findViewById(R.id.dot_2));
        dots.add(rootView.findViewById(R.id.dot_3));
        dots.add(rootView.findViewById(R.id.dot_4));
        dots.add(rootView.findViewById(R.id.dot_5));
        dots.add(rootView.findViewById(R.id.dot_6));
        dots.add(rootView.findViewById(R.id.dot_7));

        title = (TextView) rootView.findViewById(R.id.title);
        title.setText(titles[0]);

        mViewPager = (ViewPager)rootView.findViewById(R.id.vp);

        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                title.setText(titles[position]);

                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.size();
        }

        //是否是同一张图片
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//            super.destroyItem(container, position, object);
//            view.removeViewAt(position);
            view.removeView(images.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));

            return images.get(position);
        }
    }


    //切换图片
    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            currentItem = (currentItem +1) % imageIds.length;
            //更新界面
            handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
        }

    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //设置当前页面
            mViewPager.setCurrentItem(currentItem);
        }

    };
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }
}

