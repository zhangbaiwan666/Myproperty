package cottee.myproperty.constant;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class PayFeeRecord {


    private List<ListBean> list;

    public static PayFeeRecord objectFromData(String str) {

        return new Gson().fromJson(str, PayFeeRecord.class);
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * order_id : 33
         * c_time : 2018-03-14 15:57:31
         * type : 物业费
         * money :
         */

        private String order_id;
        private String c_time;
        private String type;
        private String money;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
