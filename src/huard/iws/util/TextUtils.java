package huard.iws.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TextUtils {
	private static LinkedHashMap<String,String> stringsToReplace;

	static{
		stringsToReplace = new LinkedHashMap<String,String>();

		stringsToReplace.put("<(br)>","<$1/>");

		stringsToReplace.put("<!--[Ww].*?-->","");

		stringsToReplace.put("<title>[Ww].*?</title>","");

		stringsToReplace.put("</?[vo]:[^>]*?>","");



		stringsToReplace.put("<style>.*?</style>","");

		stringsToReplace.put("<!--\\[.*?>","");

		stringsToReplace.put("<xml>.*?</xml>","");

		stringsToReplace.put("<!\\[endif\\]-->","");

		stringsToReplace.put("</?(meta|link|object)[^>]*?>","");


		// remove any attributes from the tags apart those of <img> tag
		stringsToReplace.put("<([^(img)p(div)>]*?)\\s[^>]*?>","<$1>");

		stringsToReplace.put("</?st1:[^>]*?>","");


		// remove html tag pairs which has nothing between them
		stringsToReplace.put("<[^/>]*?>[\\s]*?</[^>]*?>","");

		stringsToReplace.put("<p class=\"Ms[^>]*?>","<p>");

		stringsToReplace.put("<span[^>]*?>","<span>");




	}

	public static String cleanHtml(String html){

		for (Map.Entry<String, String> entry : stringsToReplace.entrySet())

	    {
			html = Pattern.compile(entry.getKey(), Pattern.DOTALL).matcher(html).replaceAll(entry.getValue());
	     }
		writeToFile(html);
	    return html;
	}


	public static String readFile(){
		try{
			BufferedReader br = new BufferedReader(new FileReader("/home/yair/mop/tmp/wordHtmlExample1.html"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) !=null ){
				sb.append(line+"\n");
			}
			br.close();
			return sb.toString();
		}
		catch(Exception e){
			System.out.println(e);
			return "";
		}
	}

	public static void writeToFile(String text){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("/home/yair/mop/tmp/testOutput.html"));
			System.out.println(text);
			bw.write(text);
			bw.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


	public static void main(String[] args){

		String html = TextUtils.readFile();
		html = TextUtils.cleanHtml(html);
		TextUtils.writeToFile(html);
	}

		/*BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String inputText = null;
		try{
		while ((inputText = stdIn.readLine()) != null){

			Pattern pattern =	Pattern.compile(inputText);

			Matcher matcher =
				pattern.matcher((inputText = stdIn.readLine()));

			boolean found = false;
			while (matcher.find()) {
				System.out.println("I found the text \""+matcher.group()+"\" starting at " +
						"index "+matcher.start()+" and ending at index " + matcher.end());
				found = true;
			}
			if(!found){
				System.out.println("No match found.");
			}
		}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
*/
}
