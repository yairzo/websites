package huard.iws.exec;

import java.io.File;

import huard.iws.service.UrlsCheckerService;

//import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecUrlsChecker {

	//private static final Logger logger = Logger.getLogger(ExecWordsIndexer.class);
	private UrlsCheckerService urlsCheckerService;	
	
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
		if (args.length==3){
			try{	
				ardNum = new Integer(args[2]);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
				return;
			}
		}		
		ExecUrlsChecker execUrlsChecker = new ExecUrlsChecker();
		if (execUrlsChecker.getUrlsCheckerService() == null){
			System.out.println("Probably rmi lookup failed. exiting !");
			return;
		}
		String pathToApp = args[1];
		File file = new File(pathToApp);
		if (!file.exists()){
			System.out.println("Can't find working dir.");
			return;
		}
		if (args[0].equals("buildTables") ){
			System.out.println("Starting....buildTables" );
			execUrlsChecker.getUrlsCheckerService().buildInfoPagesURLsTable(ardNum);
			execUrlsChecker.getUrlsCheckerService().buildPubPagesURLsTable(ardNum);
			System.out.println("finished." );
		}
		else if (args[0].equals("checkUrls") ){
			System.out.println("Starting....checkUrls" );
			execUrlsChecker.getUrlsCheckerService().updateURLsStatusAndSizeInInfoPagesURLsTable(ardNum, pathToApp);
			execUrlsChecker.getUrlsCheckerService().updateURLsStatusAndSizeInPubPagesURLsTable(ardNum, pathToApp);
			System.out.println("finished." );
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


	
	public UrlsCheckerService getUrlsCheckerService() {
		return urlsCheckerService;
	}

}
