package cottee.myproperty.constant;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class Mechanic {

    private List<ProjectStaffBean> project_staff;

    public static Mechanic objectFromData(String str) {

        return new Gson().fromJson(str, Mechanic.class);
    }

    public List<ProjectStaffBean> getProject_staff() {
        return project_staff;
    }

    public void setProject_staff(List<ProjectStaffBean> project_staff) {
        this.project_staff = project_staff;
    }

    public static class ProjectStaffBean {
        /**
         * id : 1
         * name : aaa
         * phone : 1111
         * photo : https://thethreestooges.cn/property/img/repair/123.jpg
         * time : 0
         * grade : 0
         */

        private String id;
        private String name;
        private String phone;
        private String photo;
        private String time;
        private String grade;

        public static ProjectStaffBean objectFromData(String str) {

            return new Gson().fromJson(str, ProjectStaffBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }
}
