package cottee.myproperty.constant;

import android.view.View;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 */

public class SubListBean implements Serializable {
    private String user_id;
    private String remark;
    private String phone_num;

//    public SubListBean(String user_id, String remark, String phone_num) {
//        this.user_id = user_id;
//        this.remark = remark;
//        this.phone_num = phone_num;
//    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

}
