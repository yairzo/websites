package huard.website.update;

public class PubPageUpdator {

	private int ardNum;
	private String title;
	private String category;
	private String parentPageTitle;

	private UpdatesDbHandler dbHandler;

	public PubPageUpdator (){
		ardNum=0;
		title = "";
		category="";
		parentPageTitle="";
		dbHandler = new UpdatesDbHandler();
	}

	public void updatePubPageLocationDetails(){
		dbHandler.updatePubPagesLocationDetails(ardNum, category, parentPageTitle);
	}

	public int getArdNum() {
		return ardNum;
	}

	public void setArdNum(int ardNum) {
		this.ardNum = ardNum;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getParentPageTitle() {
		return parentPageTitle;
	}

	public void setParentPageTitle(String parentPageTitle) {
		this.parentPageTitle = parentPageTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
