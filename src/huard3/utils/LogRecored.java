package huard3.utils;

public class LogRecored {
	private String username;
	private String action;
	private String ardNum;
	private String title;
	private String docType;
	private java.sql.Date date;



	/**
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return
	 */
	public String getArdNum() {
		return ardNum;
	}



	/**
	 * @return
	 */
	public String getDocType() {
		return docType;
	}



	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param string
	 */
	public void setAction(String string) {
		action = string;
	}

	/**
	 * @param string
	 */
	public void setArdNum(String string) {
		ardNum = string;
	}



	/**
	 * @param string
	 */
	public void setDocType(String string) {
		docType = string;
	}



	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

	/**
	 * @param string
	 */
	public void setUsername(String string) {
		username = string;
	}

}
