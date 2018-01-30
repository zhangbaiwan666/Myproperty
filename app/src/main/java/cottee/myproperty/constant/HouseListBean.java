package cottee.myproperty.constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/31.
 */

public class HouseListBean implements Serializable {
    private String address;
    private String home_id;

    public HouseListBean(String address)
    {
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

}
