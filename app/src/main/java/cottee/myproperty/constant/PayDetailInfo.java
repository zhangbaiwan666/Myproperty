package cottee.myproperty.constant;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class PayDetailInfo {

    /**
     * paymentinfo : {"area":"0","cost_time":"20","address":"$address",
     * "money":null,"c_time":"2018-02-05 01:15:57","state":"4","type":"$type"}
     */

    private PaymentinfoBean paymentinfo;

    public static PayDetailInfo objectFromData(String str) {

        return new Gson().fromJson(str, PayDetailInfo.class);
    }

    public PaymentinfoBean getPaymentinfo() {
        return paymentinfo;
    }

    public void setPaymentinfo(PaymentinfoBean paymentinfo) {
        this.paymentinfo = paymentinfo;
    }

    public static class PaymentinfoBean {
        /**
         * area : 0
         * cost_time : 20
         * address : $address
         * money : null
         * c_time : 2018-02-05 01:15:57
         * state : 4
         * type : $type
         */

        private String area;
        private String cost_time;
        private String address;
        private String money;
        private String c_time;
        private String state;
        private String type;

        public static PaymentinfoBean objectFromData(String str) {

            return new Gson().fromJson(str, PaymentinfoBean.class);
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCost_time() {
            return cost_time;
        }

        public void setCost_time(String cost_time) {
            this.cost_time = cost_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
