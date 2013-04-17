package huard.iws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;
import java.io.*;
import huard.iws.util.LanguageUtils;

import huard.iws.bean.PersonBean;
import huard.iws.model.Attachment;
import huard.iws.model.TextualPage;
import huard.iws.model.TextualPageOld;
import huard.iws.model.MopDesk;
import huard.iws.service.ConfigurationService;


public class ImportTextualPagesServiceImpl implements ImportTextualPagesService{
	private  static Map<String, Integer> typeMap ;
	private  static Map<String, String> categoryMap ;

	static{
		typeMap = new HashMap<String, Integer>();
		typeMap.put("Information", 0);
		typeMap.put("Funding", 1);
		typeMap.put("ARD", 2);
	}

	static{
		categoryMap = new HashMap<String, String>();// code,category name
		categoryMap.put("efsharuiot_mimun", "אפשרויות מימון");
		categoryMap.put("klalei_avoda", "כללי עבודה");
		categoryMap.put("meida_shimushi", "קישורים שימושיים");
		categoryMap.put("hamehkar_bauniversita", "המחקר באוניברסיטה");
		categoryMap.put("harashut_lemop", "הרשות למופ");
		categoryMap.put("haavaratYeda", "העברת ידע וטכנולוגיה");
		categoryMap.put("financeSources", "Funding Opportunities");
		categoryMap.put("research", "General Information");
		categoryMap.put("regulations", "Research Regulations");
		categoryMap.put("useful_information", "Links");
		categoryMap.put("RD_authority", "The Authority");
		categoryMap.put("knowledgeAndTechnology", "Tech. Transfer");
	}

public void importTextualPages(){
	String server=configurationService.getConfigurationString("website", "websiteDb");
	List<TextualPageOld> textualPagesOld = textualPageService.getTextualPagesOldWebsite(server);
	for(TextualPageOld textualPageOld: textualPagesOld){
		TextualPage textualPage = new TextualPage();
		textualPage.setTitle(changeQuotes(textualPageOld.getTitle()));
		String localeId=LanguageUtils.getLanguage(textualPageOld.getTitle()).getLocaleId();
		//textualPage.setLocaleId(localeId);
		MopDesk mopDesk =mopDeskService.getMopDesk(textualPageOld.getDeskId());
		textualPage.setDeskId(mopDesk.getId());
		List<PersonBean> deskPersons;
		if(localeId.equals("iw_IL"))
			deskPersons=mopDeskService.getPersonsList(textualPage.getDeskId(),0);
		else
			deskPersons=mopDeskService.getPersonsListEnglish(textualPage.getDeskId(),0);
		int creatorId = 0;
		for(PersonBean personBean:deskPersons){
			if(personBean.getTitle().indexOf("עוזר")>=0 || personBean.getTitle().indexOf("Assistant")>=0){
				creatorId=personBean.getId();
				break;
			}
		}
		textualPage.setCreatorId(creatorId);
		int newid=textualPageService.insertTextualPage(textualPage);
		if(newid==0)
			continue;
		textualPage.setId(newid);
		int ardNum=textualPageOld.getId();
		textualPageService.insertArdNum(ardNum,newid);
		textualPage.setCreationTime(textualPageOld.getPubDate());
		textualPage.setHtml(textualPageOld.getHtml());
		String docType = textualPageOld.getDocType();
		textualPage.setMessageType(typeMap.get(docType));
		textualPage.setRequireLogin(textualPageOld.getRestricted());
		textualPage.setIsMessage(textualPageOld.getMessage());
		textualPage.setKeepInRollingMessagesExpiryTime(textualPageOld.getStopRollingDate());
		textualPage.setDescription(textualPageOld.getInternalUseDescription());
		textualPage.setWrapExternalPage(textualPageOld.isWraper());
		String sourceToWrap=textualPageOld.getSourceToWrap();
		Pattern p = Pattern.compile(".*?id=(\\d+)&.*?");
		Matcher m = p.matcher(sourceToWrap);
		if(m.find())
			textualPage.setExternalPageUrl(m.group(1));
		//update date
		textualPage.setUpdateTime(textualPageOld.getUpdateTime());
		//link 
		textualPage.setShowFile(textualPageOld.getFileRepresentation());
		if(textualPage.getShowFile()){
			String linkAddress = textualPageOld.getLink();
			try{
				URL url = new URL(linkAddress);
			    URLConnection connection = url.openConnection();
			    connection.setConnectTimeout(10000);
			    InputStream in = connection.getInputStream();
			    int contentLength = connection.getContentLength();

			    // To avoid having to resize the array over and over as bytes are written to the array, provide an estimate of the size 
			    ByteArrayOutputStream tmpOut;
			    if (contentLength != -1) {
			        tmpOut = new ByteArrayOutputStream(contentLength);
			    } else {
			        tmpOut = new ByteArrayOutputStream(16384); // some appropriate size
			    }

			    byte[] buf = new byte[4096];
			    while (true) {
			        int len = in.read(buf);
			        if (len == -1) {
			            break;
			        }
			        tmpOut.write(buf, 0, len);
			    }
			    in.close();
			    tmpOut.close(); 

			    byte[] array = tmpOut.toByteArray();

				Attachment attachment= new Attachment();

			    attachment.setFile(array);
				String contentType = linkAddress.substring(linkAddress.lastIndexOf("."));
				attachment.setContentType(contentType);
				textualPage.setAttachment(attachment);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			/*try {
				Attachment attachment= new Attachment();
				URL toDownload = new URL(linkAddress);
				InputStream stream = toDownload.openStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] chunk = new byte[4096];
				int bytesRead;
				while ((bytesRead = stream.read(chunk)) > 0) {
					outputStream.write(chunk, 0, bytesRead);
				}
				attachment.setFile(outputStream.toByteArray());
				String contentType = linkAddress.substring(linkAddress.lastIndexOf("."));
				attachment.setContentType(contentType);
				textualPage.setAttachment(attachment);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		//category
		String category = "";
		int categoryId=0;
		if(categoryMap.containsKey(textualPageOld.getCategory()))
			category=categoryMap.get(textualPageOld.getCategory());
		if(!category.isEmpty())
			categoryId = categoryService.getCategoryIdByName(category);
		textualPage.setCategoryId(categoryId);
		
		
		textualPageService.updateTextualPage(textualPage);
		if(textualPageOld.getOnSite())
			textualPageService.insertTextualPageOnline(textualPage);
	}
}
    
public String changeQuotes(String text){
	text=text.replaceAll("'", "");
	text=text.replaceAll("&quot;", "\"");
	return text;
}


private TextualPageService textualPageService;
public void setTextualPageService(TextualPageService textualPageService) {
	this.textualPageService = textualPageService;
}


private MopDeskService mopDeskService;
public void setMopDeskService(MopDeskService mopDeskService) {
	this.mopDeskService = mopDeskService;
}

private ConfigurationService configurationService;
public void setConfigurationService(ConfigurationService configurationService) {
	this.configurationService = configurationService;
}

private CategoryService categoryService;
public void setCategoryService(CategoryService categoryService) {
	this.categoryService = categoryService;
}


}
