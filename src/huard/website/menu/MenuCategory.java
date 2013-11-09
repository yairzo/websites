package huard.website.menu;


public class MenuCategory {
	private String name;
	private String link;
	private String backgroundImage;
	private String bgcolor;
	private String tableName;
	private int width;

	private MenuSubCategory [] subCategoriesArray;

	public MenuCategory() {
		name="";
		link="";
		backgroundImage = "";
		bgcolor = "";
		tableName="";
		width = 0;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public String getMaxStringSize(){
		int counter=0;
		for (int i=0 ; i<subCategoriesArray.length; i++){
			if (subCategoriesArray == null){
				System.out.println("Menu category " + this.name + " is null");
				continue;
			}
			if (counter < subCategoriesArray[i].getName().length())
				counter = subCategoriesArray[i].getName().length();
		}
		return String.valueOf(counter*9+10);
	}

	public String getLinkTarget(){
		if (link.indexOf("://")!=-1 || link.indexOf(".pdf")!=-1) return "_blank";
		else return "";
	}
	/**
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	public String getNameApostropheBackslashed(){
		return name.replaceAll("\'","\\\'");
	}

	/**
	 * @param string
	 */
	public void setLink(String string) {
		link = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param string
	 */
	public void setTableName(String string) {
		tableName = string;
	}
	public MenuSubCategory[] getSubCategoriesArray() {
		return subCategoriesArray;
	}
	public void setSubCategoriesArray(MenuSubCategory[] subCategoriesArray) {
		this.subCategoriesArray = subCategoriesArray;
	}


	public String getBackgroundImage() {
		return backgroundImage;
	}


	public void setBackgroundImage(String bgcolorImage) {
		this.backgroundImage = bgcolorImage;
	}


	public String getBgcolor() {
		return bgcolor;
	}


	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getStyleClass(){
		return name.replace(' ','_').replaceAll("\"","");
	}

	public String getClassColor(boolean heb){
		String color = "";
		if ( this.bgcolor.startsWith("#DF8522") )
			color = "orange";
		else if ( this.bgcolor.startsWith("#84971") )
			color = "green";
		else if ( this.bgcolor.startsWith("#d13d36") || this.bgcolor.startsWith("#E6545E") )
			color = "red";
		else if ( this.bgcolor.startsWith("#168F78") )
			color = "turkiz";
		else if ( this.bgcolor.startsWith("#a322a0") )
			color = "purple";
		else //  this.bgcolor.startsWith("#1A69A6") or not set
			color = "blue";
		if (!heb)
			color = color + "eng";
		return color;
	}


}
