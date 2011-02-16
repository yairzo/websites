package huard.iws.util;

public class NearPage {
	String display;
	int pageNum;
	public NearPage(String display, int pageNum){
		this.display = display;
		this.pageNum = pageNum;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
