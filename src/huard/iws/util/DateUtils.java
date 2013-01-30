package huard.iws.util;

import huard.iws.service.MessageService;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {
	//private static final Logger logger = Logger.getLogger(DateUtils.class);

	private static long milisInDay = 86400000L;
	private static MessageService messageService;
	private static final boolean DO_NOT_INCLUDE_TIME = false;
	private static final boolean INCLUDE_TIME = true;
	//private static final boolean RELATIVE_DAY = true;
	private static final boolean FIXED_DAY = false;

	static{
		Object obj = ApplicationContextProvider.getContext().getBean("messageService");
		messageService = (MessageService)obj;
	}

	public static boolean isSameYear(Date date){
		Calendar c  = new GregorianCalendar();
		c.setTime(date);
		Calendar now  = new GregorianCalendar();
		return c.get(Calendar.YEAR)==now.get(Calendar.YEAR);
	}

	public static boolean isCurrentYear(int year){
		Calendar now  = new GregorianCalendar();
		return now.get(Calendar.YEAR) == year;
	}

	public static Date getCurrentDate(){
		return new Date( System.currentTimeMillis());
	}

	public static Timestamp getCurrentTime(){
		return new Timestamp(new java.util.Date().getTime());
	}

	public static String formatDate (long timeMillis, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format( new Date(timeMillis));
	}

	public static String formatDate(Timestamp timestamp, boolean includeTime, boolean relativeDay){
		String date;
		SimpleDateFormat sdf;
		if ( relativeDay && timestamp.after(new Date( lastMidnightTimeMillis() ))) {
			date = messageService.getMessage("iw_IL.general.today");
		}
		else if (relativeDay &&  timestamp.after(new Date( lastMidnightTimeMillis() - milisInDay ))) {
			date = messageService.getMessage("iw_IL.general.yesterday");
		}
		else {
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf.format( (java.util.Date)timestamp );
		}
		String formatedDate = date;
		if (includeTime){
			sdf = new SimpleDateFormat("HH:mm");
			formatedDate += " " +messageService.getMessage("iw_IL.general.atHour")
				+ " " + sdf.format( (java.util.Date)timestamp );
		}
		return formatedDate;
	}

	public static String formatDateTime(Timestamp timestamp){
		return formatDate(timestamp, INCLUDE_TIME, FIXED_DAY);
	}

	public static String formatDate(Timestamp timestamp){
		return formatDate(timestamp, DO_NOT_INCLUDE_TIME, FIXED_DAY);
	}

	public static long lastMidnightTimeMillis()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static String [] getMonthsNames(String localeId){
		String [] monthsNames = new String [12];
		for (int i=0; i<12; i++){
			monthsNames [i] = messageService.getMessage(localeId+".general.month."+(i+1));
		}
		return monthsNames;
	}

	public static String getLocaleDependentLongDateTimeFormat(long timeMillis, String localeId){
		DateFormatSymbols dfs = new DateFormatSymbols();
		dfs.setMonths(getMonthsNames(localeId));
		String format = "d בMMMM, yyyy בשעה kk:00";
		if (localeId.equals("en_US"))
			format = "MMMM d, yyyy 'at' kk:00";
		SimpleDateFormat sdf = new SimpleDateFormat(format, dfs);
		Date sendTime = new Date(timeMillis);
		return sdf.format(sendTime);
	}

	public static String getLocaleDependentShortDateFormat(long timeMillis, String localeId){
		DateFormatSymbols dfs = new DateFormatSymbols();
		dfs.setMonths(getMonthsNames(localeId));
		String format = "d בMMMM, yyyy";
		if (localeId.equals("en_US"))
			format = "MMMM, d yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, dfs);
		Date sendTime = new Date(timeMillis);
		return sdf.format(sendTime);
	}
	
	public static long parseDate(String date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long aDate = 0;
		try{
			aDate = sdf.parse(date).getTime();
		}
		catch(ParseException e){
		  e.printStackTrace();	
		}
		return aDate;		
	}
	
	public static String formatDate(String date, String fromFormat, String toFormat){
		if(date.isEmpty())
			return "";
		DateFormat formatter = new SimpleDateFormat(fromFormat);
		String returnDate="";
		try{
			java.util.Date formattedDate = (java.util.Date)formatter.parse(date);
			formatter=new SimpleDateFormat(toFormat);
			returnDate= formatter.format(formattedDate);
		}
		catch(ParseException e){
		  e.printStackTrace();	
		}
		return returnDate;
	}
	public static String formatToSqlDate(String date, String fromFormat){
		return formatDate(date,fromFormat,"yyyy-MM-dd");
	}
}
