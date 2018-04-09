package cottee.myproperty.constant;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class RepairRecord {

    private List<ListBean> list;

    public static RepairRecord objectFromData(String str) {

        return new Gson().fromJson(str, RepairRecord.class);
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * order_id : 1
         * c_time : 2018-02-04 03:15:22
         */

        private String order_id;
        private String c_time;
        private  String photo_url;
        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }
public String getPhoto_url(){
            return photo_url;
}
public void setPhoto_url(String photo_url){
    this.photo_url=photo_url;
}
        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
        }
    }
}
