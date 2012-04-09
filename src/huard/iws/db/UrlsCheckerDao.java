package huard.iws.db;

//import huard.iws.model.CallOfProposal;
//import huard.iws.model.PageMailUrl;
import huard.iws.model.PageUrl;
import huard.iws.model.TextualPage;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface UrlsCheckerDao {

	public void markExistingRowsInInfoPagesUrls(String server);
	
	//public List<CallOfProposal> getAliveTabledInfoPages(Integer ardNum,String server);

	public void insertTabledInfoPagesURLsTable(int ardNum, List<PageUrl> URLsList,String server);

	public void deleteOldRowsFromInfoPagesUrls(String server);
	
	public void markExistingRowsInPubPagesUrls(String server);

	public List<TextualPage> getAliveAndOnSitePubPages(Integer ardNum,String server);
	
	public void insertPubPagesURLsTable(int ardNum, List<PageUrl> URLsList, String server);

	public void deleteOldRowsFromPubPagesUrls(String server);
	
	//public void deleteFromInfoPagesMailUrls(String server);
	
	//public void insertTabledInfoPagesMailUrlsTable(int ardNum, List<PageMailUrl> pageMailUrls,String server);

	//public void deleteFromPubPagesMailUrls(String server);

	//public void insertPubPagesMailUrlsTable(int ardNum, List<PageMailUrl> pageMailUrls,String server);

	public List<PageUrl> getInfoPagesUrls(Integer ardNum,String server);

	//public List<CallOfProposal> getInfoPageByArdNum(int ardNum,String server);

	public void updateTabledInfoPagesUrl(PageUrl pageUrl,String server);

	public List<PageUrl> getPubPagesUrls(String server);
	
	public void updatePubPagesUrl(PageUrl pageUrl,String server);

	//public List<PageMailUrl> getInfoPagesMailUrlsFromTable(String server);
	
	//public List<PageMailUrl> getPubPagesMailUrlsFromTable(String server);

	//public boolean isSuspectedMailAddress(String mailAddress,String server);
	
	//public void setInfoPagesMailUrlSuspected(String address,String server);

	//public void setPubPagesMailUrlSuspected(String address,String server);
	
	public List<PageUrl> getSearchPubPagesUrls(ListView lv, SearchCreteria search, String server);

	public List<PageUrl> getSearchInfoPagesUrls(ListView lv, SearchCreteria search, String server);

}
