package cottee.myproperty.constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/30.
 */

public class PropertyListBean implements Serializable {
    private String name;
    private String pro_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

}
