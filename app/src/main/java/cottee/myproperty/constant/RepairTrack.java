package cottee.myproperty.constant;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class RepairTrack {

    /**
     * indent : {"staff_id":"2","staff_name":"周一","address":"cc",
     * "part":"厨房的换水龙头","order":[{"c_time":"2018-03-12 14:28:56","state":"1"}]}
     */

    private IndentBean indent;

    public static RepairTrack objectFromData(String str) {

        return new Gson().fromJson(str, RepairTrack.class);
    }

    public IndentBean getIndent() {
        return indent;
    }

    public void setIndent(IndentBean indent) {
        this.indent = indent;
    }

    public static class IndentBean {
        /**
         * staff_id : 2
         * staff_name : 周一
         * address : cc
         * part : 厨房的换水龙头
         * order : [{"c_time":"2018-03-12 14:28:56","state":"1"}]
         */

        private String staff_id;
        private String staff_name;
        private String address;
        private String part;
        private List<OrderBean> order;

        public static IndentBean objectFromData(String str) {

            return new Gson().fromJson(str, IndentBean.class);
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPart() {
            return part;
        }

        public void setPart(String part) {
            this.part = part;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * c_time : 2018-03-12 14:28:56
             * state : 1
             */

            private String c_time;
            private String state;

            public static OrderBean objectFromData(String str) {

                return new Gson().fromJson(str, OrderBean.class);
            }

            public String getC_time() {
                return c_time;
            }

            public void setC_time(String c_time) {
                this.c_time = c_time;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
