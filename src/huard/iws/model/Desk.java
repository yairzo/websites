package huard.iws.model;



public class Desk {

	private String id;
	private String englishName;
	private String hebrewName;
	private int englishListId;
	private int hebrewListId;

	public Desk(){

	}


	public String getEnglishName() {
		return englishName;
	}

	public String getHebrewName() {
		return hebrewName;
	}

	public String getName(boolean heb){
		return heb ? hebrewName : englishName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}


	public int getEnglishListId() {
		return englishListId;
	}


	public void setEnglishListId(int englishListId) {
		this.englishListId = englishListId;
	}


	public int getHebrewListId() {
		return hebrewListId;
	}


	public void setHebrewListId(int hebrewListId) {
		this.hebrewListId = hebrewListId;
	}


	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public void setHebrewName(String hebrewName) {
		this.hebrewName = hebrewName;
	}

}
