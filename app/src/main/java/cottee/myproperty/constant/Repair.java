package cottee.myproperty.constant;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class Repair {


    private List<ProinfoBean> proinfo;

    public static Repair objectFromData(String str) {

        return new Gson().fromJson(str, Repair.class);
    }

    public List<ProinfoBean> getProinfo() {
        return proinfo;
    }

    public void setProinfo(List<ProinfoBean> proinfo) {
        this.proinfo = proinfo;
    }

    public static class ProinfoBean {
        /**
         * kind : 卫生间
         * detailed : [{"name":"地漏堵塞","url":"https://thethreestooges
         * .cn/property/img/repair/dilou.png"},{"name":"换装卫生间水龙头",
         * "url":"https://thethreestooges.cn/property/img/repair/shuilong
         * .png"},{"name":"马桶堵塞","url":"https://thethreestooges
         * .cn/property/img/repair/wc.png"},{"name":"马桶漏水",
         * "url":"https://thethreestooges.cn/property/img/repair/wc.png"},
         * {"name":"水(...盆堵塞","url":"https://thethreestooges
         * .cn/property/img/repair/123.jpg"},{"name":"水(...盆漏水",
         * "url":"https://thethreestooges.cn/property/img/repair/123.jpg"},
         * {"name":"卫生间水管渗漏","url":"https://thethreestooges
         * .cn/property/img/repair/123.jpg"},{"name":"浴缸堵塞",
         * "url":"https://thethreestooges.cn/property/img/repair/123.jpg"}]
         */

        private String kind;
        private List<DetailedBean> detailed;

        public static ProinfoBean objectFromData(String str) {

            return new Gson().fromJson(str, ProinfoBean.class);
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public List<DetailedBean> getDetailed() {
            return detailed;
        }

        public void setDetailed(List<DetailedBean> detailed) {
            this.detailed = detailed;
        }

        public static class DetailedBean {
            /**
             * name : 地漏堵塞
             * url : https://thethreestooges.cn/property/img/repair/dilou.png
             */

            private String name;
            private String url;
            private  String id;

            public static DetailedBean objectFromData(String str) {

                return new Gson().fromJson(str, DetailedBean.class);
            }
           public  String getId(){
                return id;
           }
           public  void  setId(String id){
               this.id=id;
           }
            public String getName() {
                return name;
            }


            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
