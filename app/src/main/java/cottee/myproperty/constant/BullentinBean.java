package cottee.myproperty.constant;

/**
 * Created by Administrator on 2018/3/7.
 */

public class BullentinBean {
    private String notice_id;
    private String outline;
    private String create_time;
    private String title;
    private String time;
    private String message;
    private String flags;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int  color;

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
