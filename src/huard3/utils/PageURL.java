package huard3.utils;

public class PageURL {
	private int ardNum;
	private String URL;
	private long fileSize;
	private String status;
	
	public PageURL(){
		ardNum=0;
		URL="";
		fileSize=0;
		status="unknown";
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
	public String getURL() {
		return URL;
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
	public void setURL(String string) {
		URL = string;
	}

}
