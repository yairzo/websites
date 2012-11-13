package huard.iws.model;


public class TextualPageOld {
	private int id;
	private String html;
	private String title;
	private boolean wraper;
	private String sourceToWrap;



	public TextualPageOld(){
		this.id = 0;
		this.html = "";
		this.title="";
		this.wraper = false;
		this.sourceToWrap="";
	}
	
	public String toString(){
			StringBuffer text = new StringBuffer();
			text.append(html + " * ");
			text.append(title + " * ");
			return text.toString();
	}

	public boolean isWraper() {
		return wraper;
	}
	public boolean getWraper() {
		return wraper;
	}
	public void setWraper(boolean wraper) {
		this.wraper = wraper;
	}

	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getSourceToWrap() {
		return sourceToWrap;
	}
	public void setSourceToWrap(String sourceToWrap) {
		this.sourceToWrap = sourceToWrap;
	}



}
