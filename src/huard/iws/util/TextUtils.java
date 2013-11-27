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

	public static String cleanHtmlFromEditor(String html){
		html=html.replaceAll("<[/]{0,1}p.*?>","");
		html=html.replaceAll("^<br>$","");//if only break in field
		return html;
	}
	public static String cleanBrFromEditor(String html){
		html=html.replaceAll("^<br>$","");//if only break in field
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
	
	public static boolean isImage(String contentType){
		if(contentType.equals("image/jpeg") || contentType.equals("image/tiff") || contentType.equals("image/gif") ||
				contentType.equals("image/png") || contentType.equals("image/bmp"))
			return true;
		else
			return false;
	}
	
	public static String getContentType(String extension){
		if(extension.equals("pdf"))
			return "application/pdf";
		else if (extension.equals("doc"))
			return "application/msword";
		else if (extension.equals("docx"))
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		else if (extension.equals("xls"))
			return "application/vnd.ms-excel";
		else if (extension.equals("xlsx"))
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		else if (extension.equals("txt"))
			return "text/plain";
		else if (extension.equalsIgnoreCase("jpg")|| extension.equalsIgnoreCase("jpeg"))
			return "image/jpeg";
		else if (extension.equalsIgnoreCase("tif")|| extension.equalsIgnoreCase("tiff"))
			return "image/tiff";
		else if (extension.equals("gif"))
			return "image/gif";
		else if (extension.equals("png"))
			return "image/png";
		else if (extension.equals("bmp"))
			return "image/bmp";
		else
			return "text/html";
	}	
	
	public static String getIcon(String extension){
		String icon="<img src='image/";
		if(extension.equals("pdf"))
			icon+= "icon_pdf.png";
		else if (extension.equals("doc"))
			icon+= "icon_word.gif";
		else if (extension.equals("docx"))
			icon+= "icon_word.gif";
		else if (extension.equals("xls"))
			icon+= "icon_excel.png";
		else if (extension.equals("xlsx"))
			icon+= "icon_excel.png";
		else
			icon+= "icon_somefile.gif";
		icon+= "' weight='15px' height='15px'/>";
		return icon;
	}
	
}
