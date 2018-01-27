package cottee.myproperty.constant;



public class BaseData {
	private String name;
	private int type;
	private String title;//
   private String url;
   private String id;
	public BaseData(String name, int type, String title, String url,String id) {
		super();
		this.name = name;
		this.type = type;
		this.title = title;
		this.url=url;
		this.id=id;
	}

	public BaseData() {
		super();
	}
	public String getId(){
		return  id;
	}
	public  void  setId(String id){
		this.id=id;
	}
	public String getUrl(){
		return url;

	}
public  void  setUrl(String url){
		this.url=url;
}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
