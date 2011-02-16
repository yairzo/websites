package huard.iws.bean;

import huard.iws.model.Faculty;

public class FacultyBean {

	private int id;
	private String nameHebrew;
	private String nameEnglish;





	public FacultyBean (){
		this.id = 0;
		this.nameHebrew = "";
		this.nameEnglish= "";

	}

	public FacultyBean(Faculty faculty){
		this.id = faculty.getId();
		this.nameHebrew = faculty.getNameHebrew();
		this.nameEnglish = faculty.getNameEnglish();
	}

	public Faculty toFaculty(){
		Faculty faculty = new Faculty();
		faculty.setId(id);
		faculty.setNameHebrew(nameHebrew);
		faculty.setNameEnglish(nameEnglish);
		return faculty;
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
