package huard.iws.model;

import java.util.Locale;

public class Language {
	private String localeId;
	private String name;
	private String dir;
	private String align;


	public Language(){

	}

	public String getAlignOpp(){
		if (align.equals("right")) return "left";
		return "right";
	}

	public String getDirOpp(){
		if (dir.equals("rtl")) return "ltr";
		return "rtl";
	}

	public Language(String localeId, String name, String dir, String align){
		this.localeId = localeId;
		this.name = name;
		this.dir = dir;
		this.align = align;
	}

	public Locale getLocale(){
		String language = localeId.substring(0, localeId.indexOf("_"));
		String country = localeId.substring(localeId.indexOf("_")+1);
		return new Locale(language, country);
	}

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isUTF8(){
		return name.equals("Hebrew");
	}
	public boolean isRtl(){
		return dir.equals("rtl");
	}
	public boolean isLtr(){
		return dir.equals("ltr");
	}
	
}
