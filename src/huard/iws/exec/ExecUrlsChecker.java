package huard.iws.exec;

import huard.iws.service.UrlsCheckerService;

//import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecUrlsChecker {

	//private static final Logger logger = Logger.getLogger(ExecWordsIndexer.class);

	public ExecUrlsChecker(){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(UrlsCheckerService.class);
			factory.setServiceUrl("rmi://localhost:1199/UrlsCheckerService");
			factory.afterPropertiesSet();
			urlsCheckerService = (UrlsCheckerService)factory.getObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
	
		Integer ardNum = null;
		if (args.length==3) ardNum = new Integer(args[2]);
		ExecUrlsChecker execUrlsChecker = new ExecUrlsChecker();
		
		if (args[0].equals("buildTables") ){
			execUrlsChecker.getUrlsCheckerService().buildInfoPagesURLsTable(ardNum);
			execUrlsChecker.getUrlsCheckerService().buildPubPagesURLsTable(ardNum);
		}
		else if (args[0].equals("checkUrls") ){
			execUrlsChecker.getUrlsCheckerService().updateURLsStatusAndSizeInInfoPagesURLsTable(ardNum, args[1]);
			execUrlsChecker.getUrlsCheckerService().updateURLsStatusAndSizeInPubPagesURLsTable(ardNum, args[1]);
		}
		//else if (args[0].equals("checkMailAddresses") ){
			//execUrlsChecker.getUrlsCheckerService().updateMailURLsStatusInInfoPagesMailURLsTable();
			//execUrlsChecker.getUrlsCheckerService().updateMailURLsStatusInPubPagesMailURLsTable();
		//}
		//else if (args[0].equals("notify")){
			//execUrlsChecker.getUrlsCheckerService().buildInfoPagesMailURLsTable();
			//execUrlsChecker.getUrlsCheckerService().buildPubPagesMailURLsTable();
			//execUrlsChecker.getUrlsCheckerService().notifyPeopleWhoseMailAddressOnTheSite();
		//}

	}


	private UrlsCheckerService urlsCheckerService;
	public UrlsCheckerService getUrlsCheckerService() {
		return urlsCheckerService;
	}

}
