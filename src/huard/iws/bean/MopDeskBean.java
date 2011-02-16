package huard.iws.bean;

import huard.iws.model.MopDesk;

import java.util.LinkedHashSet;

public class MopDeskBean {
	private int id;
	private String hebrewName;
	private String englishName;

	private LinkedHashSet<PersonBean> budgetOfficers = new LinkedHashSet<PersonBean>();

	public String getName (String localeId){
		if (localeId.equals("iw_IL"))
			return hebrewName;
		return englishName;
	}

	public LinkedHashSet<PersonBean> getBudgetOfficers() {
		return budgetOfficers;
	}

	public void setBudgetOfficers(LinkedHashSet<PersonBean> budgetOfficers) {
		this.budgetOfficers = budgetOfficers;
	}

	public MopDeskBean(){

	}

	public MopDeskBean (MopDesk mopDesk){

		this.id = mopDesk.getId();
		this.hebrewName = mopDesk.getHebrewName();
		this.englishName = mopDesk.getEnglishName();
	}

	public MopDesk toMopDesk(){
		MopDesk mopDesk = new MopDesk();
		mopDesk.setId(id);
		mopDesk.setHebrewName(hebrewName);
		mopDesk.setEnglishName(englishName);
		return mopDesk;
	}

	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getHebrewName() {
		return hebrewName;
	}
	public void setHebrewName(String hebrewName) {
		this.hebrewName = hebrewName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}



}
