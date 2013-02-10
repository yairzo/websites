package huard.iws.exec;

import huard.iws.service.ImportCallForProposalsService;

import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecImportCallForProposals {


	private ImportCallForProposalsService importCallForProposalsService;
	public ImportCallForProposalsService getImportCallForProposalsService() {
		return importCallForProposalsService;
	}
	
	public ExecImportCallForProposals(){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(ImportCallForProposalsService.class);
			factory.setServiceUrl("rmi://localhost:1199/ImportCallForProposalsService");
			factory.afterPropertiesSet();
			importCallForProposalsService = (ImportCallForProposalsService)factory.getObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
		
		ExecImportCallForProposals execimport = new ExecImportCallForProposals();
		if (execimport.getImportCallForProposalsService() == null){
			System.out.println("Probably rmi lookup failed. exiting !");
			return;
		}
		long startTime = System.currentTimeMillis();
		System.out.println("Starting...." );

		execimport.getImportCallForProposalsService().importCallForProposals();
 		
		long endTime = System.currentTimeMillis();
		long runtimeSeconds = (endTime - startTime) / 1000;
		System.out.println("Done. Runtime seconds="	+ runtimeSeconds);
	}



}
