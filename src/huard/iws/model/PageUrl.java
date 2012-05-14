package huard.iws.model;

public class PageUrl {
	private int ardNum;
	private String urlText;
	private String url;
	private long fileSize;
	private long formerFileSize;
	private String status;

	public PageUrl(){
		ardNum=0;
		urlText="";
		url="";
		fileSize=0;
		formerFileSize=0;
		status="unknown";
	}

	public PageUrl(String urlText, String url){
		this.urlText = urlText;
		this.url = url;
	}
	
	public boolean isValid(){
		boolean isValid = true;
		if (urlText.length() > 255)
			isValid = false;
		return isValid;
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
