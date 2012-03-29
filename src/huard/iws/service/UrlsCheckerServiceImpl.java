package huard.iws.service;

import huard.iws.db.UrlsCheckerDao;
import huard.iws.model.CallOfProposal;
import huard.iws.model.PageUrl;
import huard.iws.model.TextualPage;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;
import huard.iws.util.WordsTokenizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlsCheckerServiceImpl implements UrlsCheckerService{
	
	public void buildInfoPagesURLsTable(Integer ardNum){
		String server = configurationService.getConfigurationString("websiteDb");
		urlsCheckerDao.markExistingRowsInInfoPagesUrls(server);
		List<CallOfProposal> callOfProposals = urlsCheckerDao.getAliveTabledInfoPages(ardNum,server);
		for (CallOfProposal callOfProposal: callOfProposals){
			String text = callOfProposal.toString();
			List<PageUrl> pageURLsList = getURLs(text);
			if (! callOfProposal.isDescriptionOnly()){
				String pageWebAddress = callOfProposal.getPageWebAddress();
				if (pageWebAddress!=null && !"".equals(pageWebAddress)){
					if (pageWebAddress.indexOf("http://")==-1) pageWebAddress = "http://"+pageWebAddress;
					PageUrl pageUrl = new PageUrl("Original Call", pageWebAddress);
					pageURLsList.add(pageUrl);
				}
			}
			urlsCheckerDao.insertTabledInfoPagesURLsTable(callOfProposal.getId(), pageURLsList, server);
		}
		urlsCheckerDao.deleteOldRowsFromInfoPagesUrls(server);
	}

	
	public void buildPubPagesURLsTable(Integer ardNum){
		String server = configurationService.getConfigurationString("websiteDb");
		urlsCheckerDao.markExistingRowsInPubPagesUrls(server);
		List<TextualPage> pubPages = urlsCheckerDao.getAliveAndOnSitePubPages(ardNum,server);
		for (TextualPage pubPage: pubPages){
			String text = pubPage.getHtml();
			List<PageUrl> pageURLsList = getURLs(text);
			urlsCheckerDao.insertPubPagesURLsTable(pubPage.getId(), pageURLsList, server);
		}
		urlsCheckerDao.deleteOldRowsFromPubPagesUrls(server);
	}

	public List<PageUrl> getURLs(String text){
		List<PageUrl> pageUrls = new ArrayList<PageUrl>();
		WordsTokenizer wt = new WordsTokenizer("*");
		List<String> tokens = wt.getSubstringsList(text);
		if(tokens.size()>0){
			String lastToken="";
			for (String token : tokens){
				if ( (token.indexOf("http://")==-1 && token.indexOf("ftp://")==-1)
						|| (token.trim().indexOf("http://")!=0 && token.trim().indexOf("ftp://")!=0 )){
				}
				else {
			System.out.println("url:" + token);
					PageUrl pageUrl = new PageUrl(lastToken,token);
					pageUrls.add(pageUrl);
				}

				lastToken=token;
			}
			return pageUrls;
		}
		else{//when no *
			int fromIndex=0;
			while (text.indexOf("http://", fromIndex)>-1){
				String url = "";
				int index=text.indexOf("http://", fromIndex);
				//if (text.indexOf("*", fromIndex)>-1)
				//	url = text.substring(text.indexOf("http://", fromIndex), text.indexOf("*", index));
				//else 
				if (text.indexOf(" ", fromIndex)>-1)
					url = text.substring(text.indexOf("http://", fromIndex), text.indexOf(" ", index));
				else
					url = text.substring(text.indexOf("http://", fromIndex));
				PageUrl pageUrl = new PageUrl("",url);
				pageUrls.add(pageUrl);
				fromIndex = index+7;
			}
			fromIndex=0;
			while (text.indexOf("ftp://", fromIndex)>-1){
				String url = "";
				int index=text.indexOf("ftp://", fromIndex);
				//if (text.indexOf("*", fromIndex)>-1)
				//	url = text.substring(text.indexOf("ftp://", fromIndex), text.indexOf("*", index));
				//else 
				if (text.indexOf(" ", fromIndex)>-1)
					url = text.substring(text.indexOf("ftp://", fromIndex), text.indexOf(" ", index));
				else
					url = text.substring(text.indexOf("ftp://", fromIndex));
				PageUrl pageUrl = new PageUrl("",url);
				pageUrls.add(pageUrl);
				fromIndex = index+6;
			}
			return pageUrls;
		}
	}

	/*public void buildInfoPagesMailURLsTable(){
		String server = configurationService.getConfigurationString("websiteDb");
		urlsCheckerDao.deleteFromInfoPagesMailUrls(server);
		List<CallOfProposal> infoPages = urlsCheckerDao.getAliveTabledInfoPages(ardNum,server);
		for (CallOfProposal infoPage: infoPages){
			String text = infoPage.toString();
			List<PageMailUrl> pageMailUrls = getPageMailUrls(text);
			urlsCheckerDao.insertTabledInfoPagesMailUrlsTable(infoPage.getId(), pageMailUrls,server);
		}
	}

	public void buildPubPagesMailURLsTable(){
		String server = configurationService.getConfigurationString("websiteDb");
		urlsCheckerDao.deleteFromPubPagesMailUrls(server);
		List<TextualPage> pubPages = urlsCheckerDao.getAliveAndOnSitePubPages(ardNum,server);
		for (TextualPage pubPage: pubPages){
			String text = pubPage.getHtml();
			List<PageMailUrl> pageMailUrls = getPageMailUrls(text);
			urlsCheckerDao.insertPubPagesMailUrlsTable(pubPage.getId(), pageMailUrls,server);
		}

	}

	public List<PageMailUrl> getPageMailUrls(String text){
		WordsTokenizer wt = new WordsTokenizer("*");
		List<String> tokens = wt.getSubstringsList(text);
		List<PageMailUrl> pageMailUrls = new ArrayList<PageMailUrl>();
		String lastToken="";
		for (String token: tokens){
			if ( token.indexOf("mailto:")!=-1 && token.trim().indexOf("mailto:")==0 ){
				PageMailUrl pageMailUrl = new PageMailUrl();
				pageMailUrl.setMailAddress(token.replaceFirst("mailto:",""));
				pageMailUrl.setUrlText(lastToken);
				pageMailUrls.add(pageMailUrl);
			}
			lastToken = token;
		}
		return pageMailUrls;
	}*/


	public void updateURLsStatusAndSizeInInfoPagesURLsTable(Integer ardNum, String pathToApp){
		String server = configurationService.getConfigurationString("websiteDb");
		List<PageUrl> pagesUrls = urlsCheckerDao.getInfoPagesUrls(ardNum,server);
		for (PageUrl pageUrl: pagesUrls){
			pageUrl = updateURLsStatusAndSize(pageUrl, pathToApp);
			if(pageUrl!=null)
				urlsCheckerDao.updateTabledInfoPagesUrl(pageUrl,server);

		}
	}	

	public void updateURLsStatusAndSizeInPubPagesURLsTable(Integer ardNum, String pathToApp){
		String server = configurationService.getConfigurationString("websiteDb");
		List<PageUrl> pagesUrls = urlsCheckerDao.getPubPagesUrls(server);
		for (PageUrl pageUrl: pagesUrls){
			pageUrl = updateURLsStatusAndSize(pageUrl, pathToApp);
			if(pageUrl!=null)
				urlsCheckerDao.updatePubPagesUrl(pageUrl,server);
		}
			
	}

	public PageUrl updateURLsStatusAndSize(PageUrl pageUrl, String pathToApp){
		try{
			System.out.println("Waiting .....");
			Thread.sleep(2000);
		}
		catch (InterruptedException ie){
		}
		try{
			String pathToDownloadedFile = pathToApp+"/work/urlsChecker/"+pageUrl.getArdNum()+"Target.html";
			System.out.println("Command: wget -t 2 -w 10s -T 120 -U  Mozilla/5.0 -O "+pathToDownloadedFile+" "+pageUrl.getUrl());
			Process p = Runtime.getRuntime().exec("wget -t 2 -w 10s -T 60 -U  Mozilla/5.0 -O "+pathToDownloadedFile+" "+pageUrl.getUrl());
			try{
				long start = System.currentTimeMillis();
				p.waitFor();
				System.out.println("Download Time:"+(System.currentTimeMillis()-start)/1000);
			}
			catch (InterruptedException ie){
			}
			if (p.exitValue() != 0){
				System.out.println("Url is Dead. Exit status:"+p.exitValue());
				pageUrl.setStatus("Dead");
				pageUrl.setFileSize(0);
				return pageUrl;
			}
			
			File file = new File (pathToDownloadedFile);
			long fileSize = file.length();
			pageUrl.setFileSize(fileSize);
			//file.delete();
			System.out.println("fileSize:"+fileSize + "  pageUrl.getFormerFileSize():"+ pageUrl.getFormerFileSize());
			if (pageUrl.getFormerFileSize()>0 && Math.abs(fileSize - pageUrl.getFormerFileSize()) > 20000){
				System.out.println("Url is Changed. exit status:"+p.exitValue());
				pageUrl.setStatus("Changed");
				pageUrl.setFileSize(fileSize);
			}
			else {
				System.out.println("Url is Alive");
				pageUrl.setStatus("Alive");									
			}			
			return pageUrl;
		}
		catch(IOException e){
			System.out.println(e);
			return null;
		}
	}
	
	
	/*public String createDeadUrlMailMessageBody(PageUrl pageUrl, String type){
		String message = "The following URL: <a href=\""+pageUrl.getUrl()+"\">"+pageUrl.getUrl()+
		"</a> seems to be dead " +
		"\n It appears in this <a href=\"http://"+webDomain+(port!=null && !"".equals(port) ? ":"+port : "")+"/huard/"+type+"Viewer.jsp?ardNum="+pageUrl.getArdNum()+
		"&foundBySearchWords="+pageUrl.getUrlTextReplaceSpaces()+"\">page</a>\n The link has the text: "+pageUrl.getUrlText()+
		"\n check it manually and edit the page if necessary";
		return message;
	}

	public void notifyPeopleWhoseMailAddressOnTheSite(){
		String server = configurationService.getConfigurationString("websiteDb");
		List<PageMailUrl> mailUrls = urlsCheckerDao.getInfoPagesMailUrlsFromTable(server);
		for (PageMailUrl mailUrl: mailUrls){
			String to = mailUrl.getMailAddress();
			final String subject = "Message from Hebrew University ARD Webmaster";
			String body = "I would like to inform you that your mail address is on our web site in the following page: \n" +
				"http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=" + mailUrl.getArdNum() + "&mailAddress="+to+"\n" +
				"Any comments on this issue should be sent to michal@ard.huji.ac.il \n " +
				"at the Authority for Research and Development";
			MailCollector.getMailCollector().add("webmaster@ard.huji.ac.il",to,"yair@ard.huji.ac.il","",
				"webmaster@ard.huji.ac.il",subject,body,"");
		}
		mailUrls = urlsCheckerDao.getPubPagesMailUrlsFromTable(server);
		for (PageMailUrl mailUrl: mailUrls){
			String to = mailUrl.getMailAddress();
			final String subject = "Message from Hebrew University ARD Webmaster";
			String body = "I would like to inform you that your mail address is on our web site in the following page: \n" +
				"http://ard.huji.ac.il/huard/pubPageViewer.jsp?ardNum=" + mailUrl.getArdNum() + "\n" +
				"Any comments on this issue should be sent to michal@ard.huji.ac.il \n " +
				"at the Authority for Research and Development";
			MailCollector.getMailCollector().add("webmaster@ard.huji.ac.il",to,"yair@ard.huji.ac.il","",
					"webmaster@ard.huji.ac.il",subject,body,"");
		}
	}*/

	/*public void updateMailURLsStatusInInfoPagesMailURLsTable(){
		String server = configurationService.getConfigurationString("websiteDb");
		List<PageMailUrl> pageMailUrls = urlsCheckerDao.getInfoPagesMailUrlsFromTable(server);
		for (PageMailUrl pageMailUrl: pageMailUrls){
			if (urlsCheckerDao.isSuspectedMailAddress(pageMailUrl.getMailAddress(),server)){
				String docOwner = urlsCheckerDao.getInfoPageByArdNum(pageMailUrl.getArdNum(),server).get(0).getDocOwner();
				String to = "yair@"+webDomain;
				String to = docOwner.toLowerCase()+"@"+webDomain;
				String subject = "A suspected mail address on a page";
				String message = "The following mail address is suspected to be not connected: "+pageMailUrl.getMailAddress()+
					"\n It's in page http://"+webDomain+(port!=null && !"".equals(port) ? ":"+port : "")+"/huard/infoPageViewer.jsp?ardNum="+
					pageMailUrl.getArdNum()+"&foundBySearchWords="+pageMailUrl.getUrlTextReplaceSpaces()+
					"\n try send a test mail to this address and edit the page if nessesary";
				MailCollector.getMailCollector().add(from, to, cc, "", replyTo, subject, message , "");
				urlsCheckerDao.setInfoPagesMailUrlSuspected(pageMailUrl.getMailAddress(),server);
			}
		}
	}*/

	/*public void updateMailURLsStatusInPubPagesMailURLsTable(){
		String server = configurationService.getConfigurationString("websiteDb");
		List<PageMailUrl> pageMailUrls = urlsCheckerDao.getPubPagesMailUrlsFromTable(server);
		for (PageMailUrl pageMailUrl: pageMailUrls){
			if (urlsCheckerDao.isSuspectedMailAddress(pageMailUrl.getMailAddress(),server)){
				String docOwner = urlsCheckerDao.getPubPageByArdNum(pageMailUrl.getArdNum(),server).get(0).getDocOwner();
				String to = "yair@"+webDomain;
				//String to = docOwner.toLowerCase()+"@"+webDomain;
				String subject = "A suspected mail address on a page";
				String message = "The following mail address is suspected to be not connected: "+pageMailUrl.getMailAddress()+
					"\n It's in page http://"+webDomain+(port!=null && !"".equals(port) ? ":"+port : "")+"/huard/pubPageViewer.jsp?ardNum="
					+pageMailUrl.getArdNum()+"&foundBySearchWords="+pageMailUrl.getUrlTextReplaceSpaces()+
					"\n try send a test mail to it and edit the page if nessesary";
				MailCollector.getMailCollector().add(from, to, cc, "", replyTo, subject, message , "");
				urlsCheckerDao.setPubPagesMailUrlSuspected(pageMailUrl.getMailAddress(),server);
			}
		}
	}*/

	/*public Properties getAppProperties(String pathToApp){
		try{
			BufferedReader br = new BufferedReader( new FileReader(pathToApp+"/urlsChecker/conf/urlsChecker.conf"));
			String line;
			Properties prop = new Properties();
			while ((line=br.readLine())!=null){
				if (line.indexOf("=")!=-1){
					String key = line.substring(0,line.indexOf("="));
					String value = line.substring(line.indexOf("=")+1);
					prop.setProperty(key.trim(),value.trim());
				}
			}
			return prop;
		}
		catch(IOException e){
			System.out.println(e);
			return null;
		}
	}*/
	
	public List<PageUrl> getSearchPubPagesUrls(ListView lv, SearchCreteria search, String server){
		return urlsCheckerDao.getSearchPubPagesUrls(lv, search, server);
	}

	public List<PageUrl> getSearchInfoPagesUrls(ListView lv, SearchCreteria search, String server){
		return urlsCheckerDao.getSearchInfoPagesUrls(lv, search, server);
	}
	
	
	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	
	private UrlsCheckerDao urlsCheckerDao;
	public void setUrlsCheckerDao(UrlsCheckerDao urlsCheckerDao) {
		this.urlsCheckerDao = urlsCheckerDao;
	}

}
