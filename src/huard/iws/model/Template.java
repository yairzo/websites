package huard.iws.model;



public class Template {

	private int id;
	private String template;
	private String title;
	private long updateTime;
	private int personId;
	private int modifierId;

	public Template(){
		this.id = 0;
		this.template = "";
		this.title = "";
		this.updateTime = 0;
		this.personId = 0;
		this.modifierId = 0;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}

	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getModifierId() {
		return modifierId;
	}
	public void setModifierId(int modifierId) {
		this.modifierId = modifierId;
	}

}
