package huard.iws.util;

import huard.iws.model.Language;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LanguageUtils {

	private static final Logger logger = Logger.getLogger(LanguageUtils.class);
	private static final String DEFAULT_LOCALE_ID = "en_US";

	private  static Map<String, Language> languagesMap ;

	static{
		languagesMap = new HashMap<String, Language>();
		languagesMap.put("iw_IL", new Language("iw_IL", "Hebrew",  "rtl", "right"));
		languagesMap.put("en_US", new Language("en_US", "English", "ltr", "left"));
	}

	public static Language getLanguage(RequestWrapper request, HttpServletResponse response, String textSampleOrLocaleId){
		String localeId = "";
		if (request != null){
			localeId = request.getParameter("locale", "");
			
			if (localeId.isEmpty()) {
				localeId = (String)request.getSession().getAttribute("locale");
			}
		}
		logger.info("Locale id: " + localeId);
		if ((localeId == null || localeId.isEmpty()) && textSampleOrLocaleId != null && !textSampleOrLocaleId.isEmpty()){
			if (languagesMap.containsKey(textSampleOrLocaleId))
				localeId = textSampleOrLocaleId;
			else
				localeId = getLocaleId(textSampleOrLocaleId);
		}
		
		logger.info("Locale id: " + localeId);
		
		
		if (localeId == null) 
			localeId = DEFAULT_LOCALE_ID;
		logger.info("Locale id: " + localeId);
		if (response != null){
			String language = localeId.substring(0,localeId.indexOf("_"));
			String country = localeId.substring(localeId.indexOf("_")+1);
			RequestContextUtils.getLocaleResolver(request.getRequest()).setLocale(request.getRequest(), response, new Locale(language, country));
		}
		if (request != null)
			request.getSession().setAttribute("locale",localeId);
		logger.info("Locale id: " + localeId);
		return languagesMap.get(localeId);
	}

	public static Language getLanguage(RequestWrapper request){
		return getLanguage(request, null, null);
	}

	public static Language getLanguage(String textSample){
		return getLanguage(null,null,textSample);
	}

	private static String getLocaleId(String textSample){
		if (textSample !=null && textSample.length() > 0){
			try{
				for (int i=0;i< textSample.length();i++){
					if (textSample.codePointAt(i) > 0x590 && textSample.codePointAt(i) < 0x5FF) return "iw_IL";
				}
				//byte[] utf8 = textSample.getBytes("UTF-8");
				//for (byte b : utf8){
				//	if ((256+b) >=144 && (256+b)<=170) return "iw_IL";
				//}
				return "en_US";
			}
			catch (Exception e){
				logger.info(e);
			}
		}
		return "";
	}

	public static void applyLanguage(Map<String,Object> model, RequestWrapper request, HttpServletResponse response, String textSampleOrLocaleId){
		model.put("lang", getLanguage(request, response, textSampleOrLocaleId));
	}
	
	public static void applyLanguage(Map<String,Object> model, String textSampleOrLocaleId){
		model.put("lang", getLanguage(textSampleOrLocaleId));
	}

	public static void applyLanguage(Map<String,Object> model, RequestWrapper request){
		model.put("lang", getLanguage(request));
	}

	public static void applyLanguages(Map<String,Object> model){
		model.put("langs", languagesMap.values());
	}

	public static Map<String, Language> getLanguagesMap() {
		return languagesMap;
	}
}
