package cottee.myproperty.constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/21.
 */

public class BullentinInfo implements Serializable {
    private String notice_id;
    private String title;
    private String outline;
    private String create_time;

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


}
