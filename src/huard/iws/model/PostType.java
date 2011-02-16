package huard.iws.model;

public class PostType {
	private int id;
	private String hebrewName;
	private String hebrewDescription;
	private String englishName;
	private String englishDescription;
	private String color;
	private boolean relyOnCallOfProposal;


	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getEnglishDescription() {
		return englishDescription;
	}
	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getHebrewDescription() {
		return hebrewDescription;
	}
	public void setHebrewDescription(String hebrewDescription) {
		this.hebrewDescription = hebrewDescription;
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
	public boolean isRelyOnCallOfProposal() {
		return relyOnCallOfProposal;
	}
	public void setRelyOnCallOfProposal(boolean relyOnCallOfProposal) {
		this.relyOnCallOfProposal = relyOnCallOfProposal;
	}


}
