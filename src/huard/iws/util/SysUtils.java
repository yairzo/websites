package huard.iws.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class SysUtils {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");

	/**
	 * Format the given date to a timestamp in the format "yyyy-MM-dd, HH:mm:ss"
	 *
	 * @param date
	 * @return
	 */
	public static String toTimestamp(Date date)
	{
		return dateFormat.format(date);
	}

	/**
	 * Format the given date (given as millseconds) to a timestamp in the format
	 * "yyyy-MM-dd, HH:mm:ss"
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String toTimestamp(long timeMillis)
	{
		return toTimestamp(new Date(timeMillis));
	}

	/**
	 * This method will sleep for the given ampount of time.
	 *
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// do nothing - just return ;
		}
	}

}
