package huard.iws.bean;

import huard.iws.model.InitiatingBody;

public class InitiatingBodyBean {

	private int id;
	private String nameHebrew;
	private String nameEnglish;





	public InitiatingBodyBean (){
		this.id = 0;
		this.nameHebrew = "";
		this.nameEnglish= "";

	}

	public InitiatingBodyBean(InitiatingBody initiatingBody){
		this.id = initiatingBody.getId();
		this.nameHebrew = initiatingBody.getNameHebrew();
		this.nameEnglish = initiatingBody.getNameEnglish();
	}

	public InitiatingBody toInitiatingBody(){
		InitiatingBody initiatingBody = new InitiatingBody();
		initiatingBody.setId(id);
		initiatingBody.setNameHebrew(nameHebrew);
		initiatingBody.setNameEnglish(nameEnglish);
		return initiatingBody;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	public String getNameHebrew() {
		return nameHebrew;
	}

	public void setNameHebrew(String nameHebrew) {
		this.nameHebrew = nameHebrew;
	}



}
