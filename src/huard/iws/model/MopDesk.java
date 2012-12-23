package huard.iws.model;

//TODO: expand it to include the personel

public class MopDesk {
	private int id;
	private String deskId;
	private String hebrewName;
	private String englishName;
	private int appearence;
	private int personsListId;
	private int personsListIdEnglish;


	public int getAppearence() {
		return appearence;
	}
	public void setAppearence(int appearence) {
		this.appearence = appearence;
	}
	public String getDeskId() {
		return deskId;
	}
	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}
	public int getPersonsListId() {
		return personsListId;
	}
	public void setPersonsListId(int personsListId) {
		this.personsListId = personsListId;
	}
	public int getPersonsListIdEnglish() {
		return personsListIdEnglish;
	}
	public void setPersonsListIdEnglish(int personsListIdEnglish) {
		this.personsListIdEnglish = personsListIdEnglish;
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
