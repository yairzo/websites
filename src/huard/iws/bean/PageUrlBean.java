package huard.iws.bean;

import huard.iws.model.PageUrl;

public class PageUrlBean {
	private int ardNum;
	private String urlText;
	private String url;
	private long fileSize;
	private long formerFileSize;
	private String status;


	public PageUrlBean(){
		this.ardNum = 0;
		this.urlText = "";
		this.url = "";
		this.fileSize = 0;
		this.formerFileSize = 0;
		this.status ="";
	}

	public PageUrlBean( PageUrl pageUrl){
		this.ardNum = pageUrl.getArdNum();
		this.urlText = pageUrl.getUrlText();
		this.url = pageUrl.getUrl();
		this.fileSize = pageUrl.getFileSize();
		this.formerFileSize = pageUrl.getFormerFileSize();
		this.status = pageUrl.getStatus();
	}

	public PageUrl toPageUrl(){
		PageUrl pageUrl = new PageUrl();
		pageUrl.setArdNum(ardNum);
		pageUrl.setUrlText(urlText);
		pageUrl.setUrl(url);
		pageUrl.setFileSize(fileSize);
		pageUrl.setFormerFileSize(formerFileSize);
		pageUrl.setStatus(status);
		return pageUrl;
	}


	/**
	 * @return
	 */
	public int getArdNum() {
		return ardNum;
	}

	/**
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public boolean getIsDead(){
		return (status.equals("Dead"));
	}
	/**
	 * @param i
	 */
	public void setArdNum(int i) {
		ardNum = i;
	}

	/**
	 * @param l
	 */
	public void setFileSize(long l) {
		fileSize = l;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setUrl(String string) {
		url = string;
	}



	public long getFormerFileSize() {
		return formerFileSize;
	}



	public void setFormerFileSize(long formerFileSize) {
		this.formerFileSize = formerFileSize;
	}



	public String getUrlText() {
		return urlText;
	}



	public void setUrlText(String urlText) {
		this.urlText = urlText;
	}

	public String getUrlTextReplaceSpaces(){
		return urlText.replaceAll(" ", "%20");
	}

}
