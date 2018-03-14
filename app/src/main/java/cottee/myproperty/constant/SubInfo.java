package cottee.myproperty.constant;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SubInfo {

        private String sub_remark;
        private String sub_Id;
        private List<String> sub_phone;

    public SubInfo(String sub_remark, String sub_Id, List<String> sub_phone)
        {
            this.sub_remark=sub_remark;
            this.sub_Id =sub_Id;
            this.sub_phone=sub_phone;
        }

        public String getName()
        {
            return sub_remark;
        }

        public String getSub_Id()
        {
            return sub_Id;
        }

        public List<String> getSub_phone() {
            return sub_phone;
        }
    }

