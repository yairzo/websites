package huard.iws.exec;

import huard.iws.service.PagesWordsIndexerService;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecWordsIndexer {

	//private static final Logger logger = Logger.getLogger(ExecWordsIndexer.class);
	private PagesWordsIndexerService pagesWordsIndexerService;

	public ExecWordsIndexer(){		
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(PagesWordsIndexerService.class);
			factory.setServiceUrl("rmi://localhost:1199/PagesWordsIndexerService");
			factory.afterPropertiesSet();
			pagesWordsIndexerService = (PagesWordsIndexerService)factory.getObject();		
	}


	public static void main (String [] args){
		
		if (args.length == 0 || args.length > 2){
			System.out.println("Usage: ExecWordsIndexer CallForProposals | TextualPage [init]");
			return;
		}
		if (!args[0].equals("CallForProposals") && !args[0].equals("TextualPages")){
			System.out.println("Usage: ExecWordsIndexer CallForProposals | TextualPage [init]");
			return;
		}
		if (args.length == 2 && !args[1].equals("init")){
			System.out.println("Usage: ExecWordsIndexer CallForProposals | TextualPage [init]");
			return;
		}		
		ExecWordsIndexer execWordsIndexer = new ExecWordsIndexer();
		if (execWordsIndexer.getPagesWordsIndexerService() == null){
			System.out.println("Probably rmi lookup failed. exiting !");
			return;
		}
		
		long startTime = System.currentTimeMillis();
		System.out.println("Starting...." );
		
		boolean init = args.length == 2;
		String indexer = args[0];

		if(indexer.equals("CallForProposals"))
			execWordsIndexer.getPagesWordsIndexerService().indexInfoPages(init);
		else if (indexer.equals("TextualPages"))
			execWordsIndexer.getPagesWordsIndexerService().indexTextualPages(init);
		else
			System.out.println("no indexer argument passed");
		long endTime = System.currentTimeMillis();
		long runtimeSeconds = (endTime - startTime) / 1000;
		System.out.println("Done.  Runtime seconds="	+ runtimeSeconds);
	}


	
	public PagesWordsIndexerService getPagesWordsIndexerService() {
		return pagesWordsIndexerService;
	}
	
}
