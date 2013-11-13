package huard3.utils;
public class ResearchField {
	private int num;
	private String researchFieldName;
	private String researchFieldShort;
	private boolean experimental;
	
	public ResearchField(){
		num=0;
		researchFieldName="";
		researchFieldShort="";
	}
	
	public void setNum(int num){
		this.num=num;
	}
	
	public int getNum(){
		return this.num;
	}
	
	public void setResearchFieldName(String researchFieldName){
		this.researchFieldName=researchFieldName;
	}
	
	public String getResearchFieldName(){
		return researchFieldName;
	}
	
	public void setResearchFieldShort(String researchFieldShort){
		this.researchFieldShort=researchFieldShort;
	}
	
	public String getResearchFieldShort(){
		return researchFieldShort;
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

}

