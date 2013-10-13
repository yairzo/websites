package huard.iws.util;

import java.util.StringTokenizer;

public class SQLUtils {

	//private static final Logger logger = Logger.getLogger(SQLUtils.class);

	public static boolean isNormalOrderStatement(String order){
		if (order.equals("")) return false;
		StringTokenizer st = new StringTokenizer(order, ",");
		while (st.hasMoreTokens()){
			String orderStatement = st.nextToken();
			if (! orderStatement.matches("\\S+?\\s{0,1}\\S*?")) return false;
		}
		return true;
	}

	public static String toSQLString(String text){
		text = text.replaceAll("\"", "\\\\\"");
		text = text.replaceAll("'", "\\\\'");
		return text;
	}
	
	public static String getTimestampString(long timeMillis){
		if(timeMillis == 0)//
			 return "0000-00-00 00:00:00";
		else
			return DateUtils.formatTimestampWithoutMillis(timeMillis);
	}

}
