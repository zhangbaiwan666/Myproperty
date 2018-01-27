package cottee.myproperty.constant;

/**
 * Created by Administrator on 2017/12/27.
 */

public class FixBean {
    private Object project;
    FixBean(Object project){
       this.project=project;
   }
    public Object getProject() {
        return project;
    }
    public void setProject(Object project) {
        this.project = project;
    }
    @Override
    public String toString() {
        String resultString = "";
        resultString += "project" + project +"\n";
        return resultString;
    }

}
