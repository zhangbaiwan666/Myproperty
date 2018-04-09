package cottee.myproperty.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.constant.RepairRecord;
import cottee.myproperty.handler.OssHandler;
import cottee.myproperty.handler.RepairRecordHandler;
import cottee.myproperty.uitils.oss.ConfigOfOssClient;
import cottee.myproperty.uitils.oss.DownloadUtils;

/**
 * Created by Administrator on 2018/1/31 0031.
 */
public class RepairRecordAdapter extends BaseAdapter {
    Context context;
    List<RepairRecord.ListBean> listBeans;
    RepairRecordHandler repairRecordHandler;
    List<Bitmap> bitmapList;
    Bitmap bitmap;
    private String image;

    public RepairRecordAdapter(Context context, List list, RepairRecordHandler repairRecordHandler) {
        this.context = context;
        this.listBeans =list;
        this.repairRecordHandler=repairRecordHandler;
    }


    @Override
    public int getCount() {
        if (listBeans.size()==0){
            Toast.makeText(context,"数据为空",Toast.LENGTH_SHORT).show();
        }
        return listBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHold vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout
                            .repair_record
                    , viewGroup, false);
            vh = new ViewHold();
            view.setTag(vh);
            vh.repair_order=(TextView)view.findViewById(R.id.repair_order);
            vh.order_time=(TextView)view.findViewById(R.id.tv_payTime);
           vh.imageview_item=(ImageView) view.findViewById(R.id.imageview_item);
            image = listBeans.get(i).getPhoto_url();
        }else {
            vh = (ViewHold) view.getTag();
            System.out.println("aaaaaaaaaaaaaaaaa+getview");
        }

        //vh.repair_order.setText(listBeans.get(i).getOrder_id());
        vh.order_time.setText(listBeans.get(i).getC_time());
        vh.imageview_item.setTag(image);
        final OssHandler ossHandler = new OssHandler(context,vh.imageview_item);
        //InitOssClient.initOssClient(context, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
        final File cache_image = new File(context.getCacheDir(), Base64.encodeToString(listBeans.get(i).getPhoto_url().getBytes(), Base64.DEFAULT));
                DownloadUtils.downloadFileFromOss(cache_image, ossHandler,
                        ConfigOfOssClient.BUCKET_NAME,image,context);

        return view;
    }
    public class ViewHold{
        TextView repair_order;
        TextView order_time;
        ImageView imageview_item;

    }
}


