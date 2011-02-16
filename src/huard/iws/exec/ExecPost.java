package huard.iws.exec;

import huard.iws.service.SendPostService;

import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecPost {

	private static final Logger logger = Logger.getLogger(ExecPost.class);

	private SendPostService sendPostService;

	public ExecPost(){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(SendPostService.class);
			factory.setServiceUrl("rmi://localhost:1199/SendPostService");
			factory.afterPropertiesSet();
			sendPostService = (SendPostService)factory.getObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
		Logger logger = Logger.getLogger(ExecPost.class);
		if (args.length == 0){
			logger.info("Wrong number of arguments. exiting !");
			return;
		}
		String mode = args[0];
		int dayOfWeek = -1;
		int hourOfDay = -1;

		if (args.length == 3){
			if (! mode.equals("preparePosts")){
				logger.info("Wrong number of arguments. exiting !");
				return;
			}
			try{
				dayOfWeek = Integer.parseInt(args[1]);
				hourOfDay = Integer.parseInt(args[2]);
			}
			catch(Exception e){
				logger.info("Wrong arguments. Second argument should be a number"
						+ "(1-7) that represents the day of week"
						+ "the third argument should be a number (0-23) that represents"
						+ "the hour. exiting !");
				return;
			}
		}
		ExecPost execPost = new ExecPost();
		if (execPost.getSendPostService() == null){
			logger.info("Probably rmi lookup failed. exiting !");
			return;
		}
		long startTime = System.currentTimeMillis();
		logger.info("Starting...." + mode + " mode");
		if (mode.equals("preparePosts"))
			execPost.getSendPostService().prepareSendPosts(dayOfWeek, hourOfDay);
		else if (mode.equals("sendPosts"))
			execPost.getSendPostService().sendPosts();
		long endTime = System.currentTimeMillis();
		long runtimeSeconds = (endTime - startTime) / 1000;
		logger.info("Done. " + mode + "mode.  Runtime seconds="
				+ runtimeSeconds);
	}


	public SendPostService getSendPostService() {
		return sendPostService;
	}
}
