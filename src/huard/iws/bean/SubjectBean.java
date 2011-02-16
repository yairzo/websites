package huard.iws.bean;

import huard.iws.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectBean {
	private int id;
	private String nameHebrew;
	private String descriptionHebrew;
	private String nameEnglish;
	private String descriptionEnglish;
	private int parentId;
	private List<Subject> subSubjects;

	private String localeId;


	public SubjectBean(String localeId){
		this.localeId = localeId;
	}

	public SubjectBean(Subject subject, String localeId){
		this.id = subject.getId();
		this.nameHebrew = subject.getNameHebrew();
		this.descriptionHebrew = subject.getDescriptionHebrew();
		this.nameEnglish = subject.getNameEnglish();
		this.descriptionEnglish = subject.getDescriptionEnglish();
		this.parentId = subject.getParentId();
		this.subSubjects = subject.getSubSubjects();
		this.localeId = localeId;
	}

	public Subject toSubject(){
		Subject subject = new Subject();
		subject.setId(id);
		subject.setNameHebrew(nameHebrew);
		subject.setDescriptionHebrew(descriptionHebrew);
		subject.setNameEnglish(nameEnglish);
		subject.setDescriptionEnglish(descriptionEnglish);
		subject.setParentId(parentId);
		subject.setSubSubjects(subSubjects);
		return subject;
	}

	public List<SubjectBean> getSubSubjectsBeans(){
		List<SubjectBean> subSubjectsBeans = new ArrayList<SubjectBean>();
		for (Subject subject: subSubjects){
			subSubjectsBeans.add( new SubjectBean(subject, localeId));
		}
		return subSubjectsBeans;
	}

	public String getName(){
		if (localeId.equals("en_US"))
			return nameEnglish;
		return nameHebrew;
	}

	public String getDescription(){
		if (localeId.equals("en_US"))
			return descriptionEnglish;
		return descriptionHebrew;
	}

	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public List<Subject> getSubSubjects() {
		return subSubjects;
	}
	public void setSubSubjects(List<Subject> subSubjects) {
		this.subSubjects = subSubjects;
	}



}
