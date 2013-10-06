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
	private List<SubjectBean> subSubjects;
	private boolean checked;

	
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
		this.subSubjects = new ArrayList<SubjectBean>();
		for (Subject aSubject: subject.getSubSubjects()){
			SubjectBean aSubjectBean = new SubjectBean(aSubject, localeId);
			this.subSubjects.add(aSubjectBean);			
		}
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
		for (SubjectBean subjectBean: subSubjects){
			subject.getSubSubjects().add(subjectBean.toSubject());
		}
		return subject;
	}
	
	public void checkSubjects(List<Integer> subjectsToCheck){
		this.checked = subjectsToCheck.contains(this.id);
		for (SubjectBean subjectBean: subSubjects){
			subjectBean.checkSubjects(subjectsToCheck);
		}
	}

	public List<SubjectBean> getSubSubjectsBeans(){
		return subSubjects;
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
	
	
	public List<SubjectBean> getSubSubjects() {
		return subSubjects;
	}

	public void setSubSubjects(List<SubjectBean> subSubjects) {
		this.subSubjects = subSubjects;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}



}
