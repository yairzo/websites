package huard.iws.service;

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
	
}
