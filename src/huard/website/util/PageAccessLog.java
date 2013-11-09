package huard.website.util;
import huard.website.baseDb.ConnectionSupplier;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class PageAccessLog {

	private static final Logger logger = Logger.getLogger(PageAccessLog.class);

	public static void logAccesToPage(String type, int ardNum, String title, boolean heb, String ip){
		try{
			Statement statement = ConnectionSupplier.getConnectionSupplier()
				.getConnection("HUARD","UPDATE").createStatement();
			java.util.Date date = new java.util.Date();
			if (!ip
					.matches("[\\d]+\\.[\\d]+\\.[\\d]+\\.[\\d]+"))
				ip = "127.0.0.1";
			long ipAsLong = Long.parseLong(ip.replaceAll("\\.", ""));
			String query = "INSERT PagesAccessLog SET type=\""+type+"\", ardNum=\""+ardNum+"\","+
				" title=\""+title+"\", time=\""+date.getTime()+"\", lang=\""+(heb ? "Hebrew" : "English")+"\","+
				" ip=\""+ip+"\", ipAsLong="+ipAsLong+";";
			//logger.info(updateString);
			System.out.println(query);
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			logger.info("huard1.utils.PageAccessLog.logAccessToPage(): "+e);
		}
	}

}
