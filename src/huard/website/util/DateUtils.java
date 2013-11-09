package huard.website.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String formatDate(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		return sdf.format(new Date(time));
	}

	public static long parseDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		try{
			long time = sdf.parse(date).getTime();
			return time;
		}
		catch(Exception e){
			return 0;
		}
	}



}
