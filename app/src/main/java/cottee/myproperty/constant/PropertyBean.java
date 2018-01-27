package cottee.myproperty.constant;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PropertyBean {
    private String name;
    private String home_id;
    private String pro_id;
    public String getProid() {
        return pro_id;
    }
    public void setProid() {
        this.pro_id = pro_id;
    }
    public String getName() {
        return name;
    }
    public void setName() {
        this.name = name;
    }
    public String getHomeid() {
        return home_id;
    }
    public void setHomeid() {
        this.home_id = home_id;
    }
    @Override
    public String toString() {
        return "HomeNewsBean [name=" + name
                + ", id=" + home_id
                + ", pro_id="  + pro_id + "]";
    }
}
