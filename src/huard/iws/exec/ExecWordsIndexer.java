package huard.iws.exec;

import huard.iws.service.PageWordsIndexerService;
import huard.iws.service.PageTextualWordsIndexerService;

import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecWordsIndexer {

	//private static final Logger logger = Logger.getLogger(ExecWordsIndexer.class);

	public ExecWordsIndexer(String indexer){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			if (indexer.equals("PageWordsIndexerService")){
				factory.setServiceInterface(PageWordsIndexerService.class);
				factory.setServiceUrl("rmi://localhost:1199/PageWordsIndexerService");
				factory.afterPropertiesSet();
				pageWordsIndexerService = (PageWordsIndexerService)factory.getObject();
			}
			else{
				factory.setServiceInterface(PageTextualWordsIndexerService.class);
				factory.setServiceUrl("rmi://localhost:1199/PageTextualWordsIndexerService");
				factory.afterPropertiesSet();
				pageTextualWordsIndexerService = (PageTextualWordsIndexerService)factory.getObject();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
		//Logger logger = Logger.getLogger(ExecWordsIndexer.class);
		ExecWordsIndexer execWordsIndexer = new ExecWordsIndexer(args[0]);
		String indexer="";
		if (args.length>0)
			indexer = args[0];
		
		if(indexer.equals("PageWordsIndexerService")){
				if (execWordsIndexer.getPageWordsIndexerService() == null){
					System.out.println("Probably rmi lookup failed. exiting !");
					return;
				}
		}
		else if(indexer.equals("PageTextualWordsIndexerService")){
				if (execWordsIndexer.getPageTextualWordsIndexerService() == null){
					System.out.println("Probably rmi lookup failed. exiting !");
					return;
				}
		}
		long startTime = System.currentTimeMillis();
		System.out.println("Starting...." );
		
		boolean init = false;
		if (args.length>1)
			init = (args[1]!=null && "init".equals(args[1]));

		if(indexer.equals("PageWordsIndexerService"))
			execWordsIndexer.getPageWordsIndexerService().indexInfoPages(init);
		else if (indexer.equals("PageTextualWordsIndexerService"))
			execWordsIndexer.getPageTextualWordsIndexerService().indexTextualPages(init);
		else
			System.out.println("no indexer argument passed");
		long endTime = System.currentTimeMillis();
		long runtimeSeconds = (endTime - startTime) / 1000;
		System.out.println("Done.  Runtime seconds="	+ runtimeSeconds);
	}


	private PageWordsIndexerService pageWordsIndexerService;
	public PageWordsIndexerService getPageWordsIndexerService() {
		return pageWordsIndexerService;
	}
	private PageTextualWordsIndexerService pageTextualWordsIndexerService;
	public PageTextualWordsIndexerService getPageTextualWordsIndexerService() {
		return pageTextualWordsIndexerService;
	}
}
