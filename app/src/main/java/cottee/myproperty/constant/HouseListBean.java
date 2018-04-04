package cottee.myproperty.constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/31.
 */

public class HouseListBean implements Serializable {
    private String house_name;
    private String home_id;

    public HouseListBean(String house_name)
    {
        this.house_name=house_name;
    }

    public String getAddress() {
        return house_name;
    }

    public void setAddress(String house_name) {
        this.house_name = house_name;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

}
