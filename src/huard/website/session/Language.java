package huard.website.session;

public class Language {

	private String lang;
	private boolean languageChanged;

	public Language(){
	}

	public boolean isHebrewVer(){
		return lang.equals("heb");
	}

	public String getLang(){
		return lang;
	}

	public void setLang(String lang){
		if(this.lang==null || ! this.lang.equals(lang)){
			languageChanged=true;
			this.lang=lang;
		}
		System.out.println("huardSiteViewer.session.Language.setLang(): languageChanged: "+this.lang);
	}

	public boolean isLanguageChanged() {
		return languageChanged;
	}

	public void falsifyLanguageChanged(){
		languageChanged=false;
	}


}
