package huard.website.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

	public static String encodeUrlParams(String url){
		try{
			Pattern p = Pattern.compile("[\\?&][^=]*=[^&]*");
			Matcher m = p.matcher(url);
			Set<String []> paramPairs = new LinkedHashSet<String []>();
			while (m.find()){
				String [] paramValuePair = m.group().split("=");
				paramPairs.add(paramValuePair);
			}
			for (String [] paramValuePair: paramPairs){
				String paramToReplace = paramValuePair[0].replace("?", "\\?") + "=" + paramValuePair[1];
				String paramValue="";
				String [] words = paramValuePair [1].split("%20");
				boolean afterFirst = false;
				for (String word: words){
					if (afterFirst)
						paramValue += "%20";
					paramValue += URLEncoder.encode(word, "utf8");
					afterFirst = true;
				}
				String replace = paramValuePair[0] + "=" + paramValue;
				url = url.replaceFirst(paramToReplace, replace);
			}
		}
		catch(UnsupportedEncodingException e){
			System.out.println(e);
		}
		return url;
	}


	/*public static void main(String [] args){
		try{
			Pattern p = Pattern.compile("[\\?&][^=]*=[^&]*");
			String url = "pubPageViewer.jsp?ardNum=1000&category=המחקר&zzz=400";
			Matcher m = p.matcher(url);
			Set<String []> paramPairs = new LinkedHashSet<String []>();
			while (m.find()){
				String [] paramValuePair = m.group().split("=");
				paramPairs.add(paramValuePair);
			}
			for (String [] paramValuePair: paramPairs){
				String paramToReplace = paramValuePair[0].replace("?", "\\?") + "=" + paramValuePair[1];
				String replace = paramValuePair[0] + "=" + URLEncoder.encode(paramValuePair[1], "utf8");
				url = url.replaceFirst(paramToReplace, replace);
			}
		}
		catch(UnsupportedEncodingException e){
			System.out.println(e);
		}

	}*/
}
