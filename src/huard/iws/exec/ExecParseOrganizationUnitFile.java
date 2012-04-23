package huard.iws.exec;

import huard.iws.service.ParseOrganizationUnitFileService;

import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecParseOrganizationUnitFile {

	//private static final Logger logger = Logger.getLogger(ExecParseOrganizationUnitFile.class);
	
	private ParseOrganizationUnitFileService parseOrganizationUnitService;
	public ParseOrganizationUnitFileService getParseOrganizationUnitFileService() {
		return parseOrganizationUnitService;
	}
	
	public ExecParseOrganizationUnitFile(){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(ParseOrganizationUnitFileService.class);
			factory.setServiceUrl("rmi://localhost:1199/ParseOrganizationUnitFileService");
			factory.afterPropertiesSet();
			parseOrganizationUnitService = (ParseOrganizationUnitFileService)factory.getObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
		//Logger logger = Logger.getLogger(ExecParseOrganizationUnitFile.class);
		System.out.println(args.length);
		if (args.length<3){
			System.out.println("Wrong number of arguments. exiting !");
			return;
		}
		String typeId = args[0];
		String listId = args[1];
		String file = args[2];
		
		ExecParseOrganizationUnitFile execParseFile = new ExecParseOrganizationUnitFile();
		if (execParseFile.getParseOrganizationUnitFileService() == null){
			System.out.println("Probably rmi lookup failed. exiting !");
			return;
		}
		long startTime = System.currentTimeMillis();
		System.out.println("Starting...." );

		execParseFile.getParseOrganizationUnitFileService().parseFile(typeId, listId, file);
 		
		long endTime = System.currentTimeMillis();
		long runtimeSeconds = (endTime - startTime) / 1000;
		System.out.println("Done. Runtime seconds="	+ runtimeSeconds);
	}



}
