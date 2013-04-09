package huard.iws.exec;

import huard.iws.service.ImportTextualPagesService;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecImportOldWebsite {


	/*private ImportCallForProposalsService importCallForProposalsService;
	public ImportCallForProposalsService getImportCallForProposalsService() {
		return importCallForProposalsService;
	}*/
	private ImportTextualPagesService importTextualPagesService;
	public ImportTextualPagesService getImportTextualPagesService() {
		return importTextualPagesService;
	}
	
	public ExecImportOldWebsite(){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			/*factory.setServiceInterface(ImportCallForProposalsService.class);
			factory.setServiceUrl("rmi://localhost:1199/ImportCallForProposalsService");
			factory.afterPropertiesSet();
			importCallForProposalsService = (ImportCallForProposalsService)factory.getObject();*/
			factory.setServiceInterface(ImportTextualPagesService.class);
			factory.setServiceUrl("rmi://localhost:1199/ImportTextualPagesService");
			factory.afterPropertiesSet();
			importTextualPagesService = (ImportTextualPagesService)factory.getObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
		
		String usagePhrase = "Usage: CallForProposals|TextualPages";
		if (args.length < 1 || args.length > 1){
			System.out.println(usagePhrase);
			return;
		}
		
		ExecImportOldWebsite execimport = new ExecImportOldWebsite();
		/*if (execimport.getImportCallForProposalsService() == null){
			System.out.println("Probably rmi lookup failed. exiting !");
			return;
		}*/
		long startTime = System.currentTimeMillis();
		System.out.println("Starting...." );

		

//		if (args[0].equals("CallForProposals"))
//			execimport.getImportCallForProposalsService().importCallForProposals();*/
		if(args[0].equals("TextualPages"))
			execimport.getImportTextualPagesService().importTextualPages();
 		
		long endTime = System.currentTimeMillis();
		long runtimeSeconds = (endTime - startTime) / 1000;
		System.out.println("Done. Runtime seconds="	+ runtimeSeconds);
	}



}
