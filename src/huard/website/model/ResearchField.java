package huard.website.model;

public class ResearchField {
	private int num;
	private String englishName;
	private String hebrewName;
	private String shortName;
	private boolean experimental;

	public ResearchField(){
		num=0;
		englishName="";
		hebrewName="";
		shortName="";
	}

	public void setNum(int num){
		this.num=num;
	}

	public int getNum(){
		return this.num;
	}

	public void setEnglishName(String englishName){
		this.englishName=englishName;
	}

	public String getEnglishName(){
		return englishName;
	}

	public void setShortName(String shortName){
		this.shortName=shortName;
	}

	public String getShortName(){
		return shortName;
	}



	/**
	 * @return
	 */
	public boolean isExperimental() {
		return experimental;
	}

	/**
	 * @param b
	 */
	public void setExperimental(boolean b) {
		experimental = b;
	}

	public String getHebrewName() {
		return hebrewName;
	}

	public void setHebrewName(String hebrewName) {
		this.hebrewName = hebrewName;
	}

}

