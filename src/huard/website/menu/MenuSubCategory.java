package huard.website.menu;

import huard.website.util.UrlUtils;

public class MenuSubCategory {
	private String name;
	private String link;
	

	public MenuSubCategory() {
		name = "";
		link = "";		
	}

	

	public String getLinkTarget() {
		if (link.indexOf("://") != -1 || link.indexOf(".pdf") != -1)
			return "_blank";
		else
			return "";
	}

	/**
	 * @return
	 */
	public String getLink() {
		return UrlUtils.encodeUrlParams(link);
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	public String getNameApostropheBackslashed() {
		return name.replaceAll("\'", "\\\\\'");
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

}
