package huard.iws.service;

import huard.iws.model.PageUrl;
import huard.iws.util.ListView;
import huard.iws.util.SearchCreteria;

import java.util.List;

public interface UrlsCheckerService {

	public void buildInfoPagesURLsTable(Integer ardNum);
	
	public void buildPubPagesURLsTable(Integer ardNum);
	
	//public void buildInfoPagesMailURLsTable();
	
	//public void buildPubPagesMailURLsTable();

	public void updateURLsStatusAndSizeInInfoPagesURLsTable(Integer ardNum, String pathToApp);

	public void updateURLsStatusAndSizeInPubPagesURLsTable(Integer ardNum, String pathToApp);

	//public void notifyPeopleWhoseMailAddressOnTheSite();
	
	//public void updateMailURLsStatusInInfoPagesMailURLsTable();

	//public void updateMailURLsStatusInPubPagesMailURLsTable();
	
	public List<PageUrl> getSearchPubPagesUrls(ListView lv, SearchCreteria search, String server);

	public List<PageUrl> getSearchInfoPagesUrls(ListView lv, SearchCreteria search, String server);

	
}
