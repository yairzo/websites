package huard.iws.util;


import huard.iws.service.MessageService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class TestSendEmail {

	private static String messageServiceUrl = "rmi://localhost:1199/MessageService";

	public static MessageService lookupMessageService()
	     throws RemoteException, NotBoundException,
	    MalformedURLException {
	   MessageService messageService = (MessageService)
	     Naming.lookup(messageServiceUrl);
	   return messageService;
	}


	public static void main(String [] args){

		try {
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(MessageService.class);
			factory.setServiceUrl("rmi://localhost:1199/MessageService");
			factory.afterPropertiesSet();
			MessageService messageService = (MessageService) factory.getObject();
			System.out.println("Message Service  null "+(messageService == null));
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}
