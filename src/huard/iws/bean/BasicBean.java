package huard.iws.bean;

import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.Locale;

public class BasicBean {
	private Locale locale;


	public BasicBean (RequestWrapper request){
		if (request == null){
			locale = new Locale("iw", "IL");
		}
		else{
			locale = LanguageUtils.getLanguage(request).getLocale();
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}


}
