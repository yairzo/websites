package huard.iws.exec;

import huard.iws.service.UrlsCheckerService;

import java.io.File;

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
		
		String usagePhrase = "Usage: ExecUrlsChecker buildTables|checkUrls pathToApp CallOfProposals|TextualPages [ardNum]";
				
		if (args.length < 3 || args.length > 4){
			System.out.println(usagePhrase);
			return;
		}
		boolean validExecution = true;
		String mode = args[0];
		String pathToApp = args[1];
		String pageType = args[2];
		
		if (!mode.equals("buildTables") && !mode.equals("checkUrls")){
			System.out.println(usagePhrase);
			validExecution = false;
		}
		else if (!new File(args[1]).exists()){
			System.out.println(usagePhrase);
			System.out.println("Can't find working dir.");
			validExecution = false;
		}		
		else if (!pageType.equals("CallOfProposals") && !pageType.equals("TextualPages")){
			System.out.println(usagePhrase);
			validExecution = false;
		}
		
		Integer ardNum = null;
		if (!validExecution)
			return;
		else if (args.length==4){
			try{	
				ardNum = new Integer(args[3]);
			}
			catch (NumberFormatException e) {
				System.out.println(usagePhrase);
				return;
			}
		}		
		ExecUrlsChecker execUrlsChecker = new ExecUrlsChecker();
		if (execUrlsChecker.getUrlsCheckerService() == null){
			System.out.println("Probably rmi lookup failed. exiting !");
			return;
		}
					
		if (mode.equals("buildTables") ){
			System.out.println("Starting....buildTables" );
			if (pageType.equals("CallOfProposals")){
				System.out.println("Will run now.");
				execUrlsChecker.getUrlsCheckerService().buildInfoPagesURLsTable(ardNum);
				
			}
			else if (pageType.equals("TextualPages"))
				execUrlsChecker.getUrlsCheckerService().buildPubPagesURLsTable(ardNum);
			System.out.println("finished." );
		}
		else if (mode.equals("checkUrls")){
			System.out.println("Starting....checkUrls" );
			if (pageType.equals("CallOfProposals"))
				execUrlsChecker.getUrlsCheckerService().updateURLsStatusAndSizeInInfoPagesURLsTable(ardNum, pathToApp);
			else if (pageType.equals("TextualPages"))
				execUrlsChecker.getUrlsCheckerService().updateURLsStatusAndSizeInPubPagesURLsTable(ardNum, pathToApp);
			System.out.println("finished." );
		}
	}	
	public UrlsCheckerService getUrlsCheckerService() {
		return urlsCheckerService;
	}

}
