package huard.iws.service;

import huard.iws.db.CallForProposalDaoOld;
import huard.iws.db.UrlsCheckerDao;
import huard.iws.model.CallForProposalOld;
import huard.iws.model.PageUrl;
import huard.iws.model.TextualPageOld;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;
import huard.iws.util.WordsTokenizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class UrlsCheckerServiceImpl implements UrlsCheckerService{
	
	private static final Logger logger = Logger.getLogger(UrlsCheckerServiceImpl.class);
	
	public void buildInfoPagesURLsTable(Integer ardNum){
		String server = configurationService.getConfigurationString("website", "websiteDb");
		urlsCheckerDao.markExistingRowsInInfoPagesUrls(server);
		List<CallForProposalOld> callForProposals = callForProposalDaoOld.getAliveTabledInfoPages(ardNum,server);
		for (CallForProposalOld callForProposal: callForProposals){
			String text = callForProposal.toString();
			List<PageUrl> pageURLsList = getURLs(text);
			if (! callForProposal.isDescriptionOnly()){
				String pageWebAddress = callForProposal.getPageWebAddress();
				if (pageWebAddress!=null && !"".equals(pageWebAddress)){
					if (pageWebAddress.indexOf("http://")==-1) pageWebAddress = "http://"+pageWebAddress;
					PageUrl pageUrl = new PageUrl("Original Call", pageWebAddress);
					pageURLsList.add(pageUrl);
				}
			}
			urlsCheckerDao.insertTabledInfoPagesURLsTable(callForProposal.getId(), pageURLsList, server);
		}
		urlsCheckerDao.deleteOldRowsFromInfoPagesUrls(server);
	}

	
	public void buildPubPagesURLsTable(Integer ardNum){
		String server = configurationService.getConfigurationString("website", "websiteDb");
		urlsCheckerDao.markExistingRowsInPubPagesUrls(server);
		List<TextualPageOld> pubPages = urlsCheckerDao.getAliveAndOnSitePubPages(ardNum,server);
		for (TextualPageOld pubPage: pubPages){
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
					System.out.println("invalid url:" + token);
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

	public void updateURLsStatusAndSizeInInfoPagesURLsTable(Integer ardNum, String pathToApp){
		String server = configurationService.getConfigurationString("website", "websiteDb");
		List<PageUrl> pagesUrls = urlsCheckerDao.getInfoPagesUrls(ardNum,server);
		for (PageUrl pageUrl: pagesUrls){
			pageUrl = updateURLsStatusAndSize(pageUrl, pathToApp);
			if(pageUrl!=null)
				urlsCheckerDao.updateTabledInfoPagesUrl(pageUrl,server);
		}
	}	

	public void updateURLsStatusAndSizeInPubPagesURLsTable(Integer ardNum, String pathToApp){
		String server = configurationService.getConfigurationString("website", "websiteDb");
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
			logger.info("Command: wget -t 2 -w 10s -T 120 -U  Mozilla/5.0 -O "+pathToDownloadedFile+" '"+pageUrl.getUrl()+"'");
			try{
				long start = System.currentTimeMillis();
				Process p = Runtime.getRuntime().exec(pathToApp+"/work/urlsChecker" + "/runWget.sh " + pathToDownloadedFile + " " + pageUrl.getUrl().replace(" ","%20"));
				p.waitFor();
				logger.info("Download Time:"+(System.currentTimeMillis()-start)/1000);
				if (p.exitValue() != 0){
					logger.info("Url is Dead. Exit status:"+p.exitValue());
					pageUrl.setStatus("Dead");
					pageUrl.setFileSize(0);
					return pageUrl;
				}
			}
			catch (InterruptedException ie){
			}
			
			
			File file = new File (pathToDownloadedFile);
			long fileSize = file.length();
			pageUrl.setFileSize(fileSize);
			file.delete();
			logger.info("fileSize:"+fileSize + "  pageUrl.getFormerFileSize():"+ pageUrl.getFormerFileSize());
			if (pageUrl.getFormerFileSize()>0 && Math.abs(fileSize - pageUrl.getFormerFileSize()) > 20000){
				logger.info("Url is Changed.");
				pageUrl.setStatus("Changed");
				pageUrl.setFileSize(fileSize);
			}
			else {
				logger.info("Url is Alive");
				pageUrl.setStatus("Alive");									
			}			
			return pageUrl;
		}
		catch(IOException e){
			System.out.println(e);
			return null;
		}
	}
	
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
	
	private CallForProposalDaoOld callForProposalDaoOld;
	public void setCallForProposalDaoOld(CallForProposalDaoOld callForProposalDaoOld) {
		this.callForProposalDaoOld = callForProposalDaoOld;
	}
}
