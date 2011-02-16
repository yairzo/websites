package huard.iws.model;

import java.util.List;

public class Subject {
	private int id;
	private String nameHebrew;
	private String descriptionHebrew;
	private String nameEnglish;
	private String descriptionEnglish;
	private int parentId;
	private List<Subject> subSubjects;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<Subject> getSubSubjects() {
		return subSubjects;
	}
	public void setSubSubjects(List<Subject> subSubjects) {
		this.subSubjects = subSubjects;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getDescriptionEnglish() {
		return descriptionEnglish;
	}
	public void setDescriptionEnglish(String descriptionEnglish) {
		this.descriptionEnglish = descriptionEnglish;
	}
	public String getDescriptionHebrew() {
		return descriptionHebrew;
	}
	public void setDescriptionHebrew(String descriptionHebrew) {
		this.descriptionHebrew = descriptionHebrew;
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
