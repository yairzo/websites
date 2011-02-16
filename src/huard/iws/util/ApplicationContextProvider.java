package huard.iws.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



public class ApplicationContextProvider implements ApplicationContextAware{
	/*private final static String [] xmlContextFiles = new String [] {
			"mop/tomcat/webapps/eqfSystem/WEB-INF/listsManager-dao.xml",
			"mop/tomcat/webapps/eqfSystem/WEB-INF/listsManager-service.xml",
			"mop/tomcat/webapps/eqfSystem/WEB-INF/listsManager-security.xml",
			"mop/tomcat/webapps/eqfSystem/WEB-INF/listsManager-servlet.xml"
	};

	private static ApplicationContext context = new FileSystemXmlApplicationContext(xmlContextFiles);
*/	// private static ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());//getWebApplicationContext(getServletContext());

	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	public static ApplicationContext getContext() { return ctx; }

	/*public static ApplicationContext getApplicationContext(){
		return context;
	}*/
}
