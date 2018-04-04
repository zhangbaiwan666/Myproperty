package cottee.myproperty.constant;

import java.io.Serializable;

public class BullentinFindInfo implements Serializable {
    private String notice_id;
    private String title;
    private String outline;
    private String type;
    private String url_list;
    private String content;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl_list() {
        return url_list;
    }

    public void setUrl_list(String url_list) {
        this.url_list = url_list;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    private String create_time;


}
