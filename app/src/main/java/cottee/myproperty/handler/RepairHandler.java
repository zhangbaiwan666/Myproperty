package cottee.myproperty.handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cottee.myproperty.activity.WorkersListActivity;
import cottee.myproperty.adapter.LeftListViewAdapter;
import cottee.myproperty.adapter.RightListViewAdapter;
import cottee.myproperty.constant.BaseData;
import cottee.myproperty.constant.Mechanic;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.RepairProject;
import cottee.myproperty.constant.RepairRecord;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class RepairHandler extends Handler {
    private ListView lv_left;
    private ListView lv_Right;
    private TextView tv_title;
    private LeftListViewAdapter leftAdapter;
    private RightListViewAdapter rightAdapter;
    private Context context;

    private ArrayList<BaseData> lists;
    private ArrayList<String> showTitle;
    private int lastPosition;
    private List<RepairProject.ProinfoBean> proinfo;
    private List<Mechanic.ProjectStaffBean> projectStaffBeans;
    private TextView tv_address;
    private String address;
      private  TextView tv_trackOfWorker;
      private  TextView tv_serProjectOftrack;
      private  TextView tv_baoxiuxinxi;
      private  TextView repair_time;
    private  List<RepairRecord.ListBean> listBeans;
    private ProgressBar pb_repairProject;
    public  RepairHandler(Context context,ListView lv_left,ListView lv_Right,TextView tv_title, ProgressBar pb_repairProject){
        this.context=context;
        this.lv_left=lv_left;
        this.lv_Right=lv_Right;
        this.tv_title=tv_title;
        this.pb_repairProject=pb_repairProject;
    }
//    public RepairHandler(Context context,ListView listView,String bigProject,String smallProject){
//        this.context1=context;
//        this.listView=listView;
//        this.bigProject=bigProject;
//        this.smallProject=smallProject;
//    }
//    public RepairHandler(Context context,TextView tv_address){
//        this.tv_address=tv_address;
//    }
      public RepairHandler(Context context,TextView tv_trackOfWorker,TextView tv_serProjectOftrack,TextView tv_baoxiuxinxi,
                           TextView repair_time){
        this.tv_trackOfWorker=tv_trackOfWorker;
        this.tv_serProjectOftrack=tv_serProjectOftrack;
        this.tv_baoxiuxinxi=tv_baoxiuxinxi;
        this.repair_time=repair_time;
      }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case Properties.RepairProject: {
                proinfo = (List<RepairProject.ProinfoBean>) msg.obj;
                initRepairProjectData();
                initRepairProjectView();

            }
//            case  Properties.WorkersList:{
//                projectStaffBeans= (List<Mechanic.ProjectStaffBean>) msg.obj;
////                WorkersAdapter workersAdapter=new WorkersAdapter(context,projectStaffBeans);
////                listView.setAdapter(workersAdapter);
//
//            }



        }


    }

    public  List<Mechanic.ProjectStaffBean> getProjectStaffBeans(){
        return projectStaffBeans;
    }
    public void initRepairProjectData() {
        lists = new ArrayList<BaseData>();

        for (int i=0;i<proinfo.get(0).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(0).getDetailed().get(i).getName(),i,proinfo.get(0).getKind(),proinfo.get(0).getDetailed().get(i).getUrl(),proinfo.get(0).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(1).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(1).getDetailed().get(i).getName(),i,proinfo.get(1).getKind(),proinfo.get(1).getDetailed().get(i).getUrl(),proinfo.get(1).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(2).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(2).getDetailed().get(i).getName(),i,proinfo.get(2).getKind(),proinfo.get(2).getDetailed().get(i).getUrl(),proinfo.get(2).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(3).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(3).getDetailed().get(i).getName(),i,proinfo.get(3).getKind(),proinfo.get(3).getDetailed().get(i).getUrl(),proinfo.get(3).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(4).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(4).getDetailed().get(i).getName(),i,proinfo.get(4).getKind(),proinfo.get(4).getDetailed().get(i).getUrl(),proinfo.get(4).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(5).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(5).getDetailed().get(i).getName(),i,proinfo.get(5).getKind(),proinfo.get(5).getDetailed().get(i).getUrl(),proinfo.get(5).getDetailed().get(i).getId()));
        }
        for (int i=0;i<proinfo.get(6).getDetailed().size();i++){
            lists.add(new BaseData(proinfo.get(6).getDetailed().get(i).getName(),i,proinfo.get(6).getKind(),proinfo.get(6).getDetailed().get(i).getUrl(),proinfo.get(6).getDetailed().get(i).getId()));
        }



        showTitle = new ArrayList<String>();
        for (int i = 0; i < lists.size(); i++) {
            if (i == 0) {
                showTitle.add(i + "");
            } else if (!TextUtils.equals(lists.get(i).getTitle(),
                    lists.get(i - 1).getTitle())) {
                showTitle.add(i + "");
            }
        }
    }

    public void initRepairProjectView() {
        leftAdapter = new LeftListViewAdapter(context,proinfo);
        lv_left.setAdapter(leftAdapter);
        rightAdapter = new RightListViewAdapter(context,proinfo,lv_Right);
        lv_Right.setAdapter(rightAdapter);
        if (lv_Right!=null&&lv_left!=null){
            pb_repairProject.setVisibility(View.GONE);

        }
        rightAdapter.updateData(lists);
        tv_title.setText(lists.get(0).getTitle());
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                System.out.println("arg2=" + arg2);
                int firstVisibleItem = lv_Right.getFirstVisiblePosition();
                updateLeftListview(firstVisibleItem, arg2);
                lv_Right.setSelection(Integer.parseInt(showTitle.get(arg2)));

            }
        });
        lv_Right.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long l) {

                Intent intent= new Intent(context,WorkersListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",lists.get(position).getId());
                bundle.putString("title",lists.get(position).getTitle());
                bundle.putString("name",lists.get(position).getName());
                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });
        lv_Right.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int currentPosition = showTitle.indexOf(firstVisibleItem + "");
                updateLeftListview(firstVisibleItem, currentPosition);

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateLeftListview(int firstVisibleItem, int currentPosition) {
        if (showTitle.contains(firstVisibleItem + "")) {
            tv_title.setText(lists.get(firstVisibleItem).getTitle());
            TextView lasTextView = (TextView) lv_left
                    .findViewWithTag(lastPosition);
            if (lasTextView != null) {
                lasTextView.setTextColor(Color.BLACK);
                lasTextView.setBackgroundColor(Color.TRANSPARENT);
            }
            TextView currenTextView = (TextView) lv_left
                    .findViewWithTag(currentPosition);
            if (currenTextView != null) {
                currenTextView.setTextColor(Color.argb(255,255,97,0));
                currenTextView.setBackgroundColor(Color.rgb(255, 255, 255));



            }
            lastPosition = currentPosition;

        }
    }
//    public  void  initDataWorkersLists(){
//
////        WorkersAdapter workersAdapter=new WorkersAdapter(context1,projectStaffBeans);
////        listView.setAdapter(workersAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view,
//                                    int i, long l) {
//                Intent intent=new Intent(context1,WorkersInfoActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("photo",projectStaffBeans.get(i).getPhoto());
//                bundle.putString("name",projectStaffBeans.get(i).getName());
//                bundle.putString("id",projectStaffBeans.get(i).getId());
//                bundle.putString("bigProject",bigProject);
//                bundle.putString("smallProject",smallProject);
//                bundle.putString("phone",projectStaffBeans.get(i).getPhone());
//                bundle.putString("grade",projectStaffBeans.get(i).getGrade());
//                bundle.putString("time",projectStaffBeans.get(i).getTime());
//                intent.putExtras(bundle);
//               context.startActivity(intent);
//            }
//        });
//    }
}
