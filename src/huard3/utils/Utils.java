package huard3.utils;
import huard3.actions.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.*;

import java.io.*;

public class Utils {
	private static final String SERVER_NAME="ard.huji.ac.il";
	private static final String MAIL_SERVER_NAME="ard.huji.ac.il";
    private static final String DATABASE_NAME="HUARD";
	private static final String VERSION_NAME="huard";
   	private static final String[] monthNames= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","All Year"};
	private static final String[] monthFullNames= {"January","February","March","April","May","June","July","August","September","October","November","December","All Year"};
    private static final char[] abc = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    private static final String PATH = "/usr/local/tomcat/webapps/"+VERSION_NAME+"/";
    private static final String EXPIRED_FILE_NAME_AND_PATH = "/home/"+VERSION_NAME+"/expired.txt";
    private static final String HOME_DIR_PATH = "/home/"+VERSION_NAME+"/";
    private static final String STATIC_PAGES_BASE_DIR = "/usr/local/apache2/htdocs";
    private static final String LOG_DIR = "/var/log/"+VERSION_NAME+"/";
    private static final int DAYS_AS_NEW=14;

    private static final double keywordsOccurancesFrequencyThreshold = 0.001;
    private static final String WORKERS_MAIL_ADDRESSES_TO_INFORM_ON_NEW_PAGES="michal.bardarian@ard.huji.ac.il";




	public static InfoPage[] getInfoPagesArrayFromInfoPagesList(List infoPagesList){
		InfoPage[] infoPages = new InfoPage[infoPagesList.size()];
		for (int i=0; i<infoPages.length; i++){
			infoPages[i] = (InfoPage)infoPagesList.get(i);
		}
		return infoPages;

	}

	public static long parseDate(String date){
		try{
    		return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(date).getTime();
    	}
    	catch (ParseException pe){
    		System.out.println("Parse exception occured: "+pe);
    		return 0;
    	}

    }

   /* public static String reduceHebrewCharsToAscii(String text){
    	 char [] charArray = text.toCharArray();
    	 StringBuffer hebrewCharsAtAscii = new StringBuffer();
    	 for (int i=0; i<charArray.length; i++){
    	 	int charIntValue = (int)charArray[i];
    	 	if (charIntValue >= 1488 && charIntValue <= 1514) {
    	 		 charIntValue = charIntValue - 1488 + 128;
    	 	     charArray[i] = (char)charIntValue;
    	 	}
    	 	hebrewCharsAtAscii.append(charArray[i]);

      	 }
      	 return  hebrewCharsAtAscii.toString();
    }*/


    public static String checkForHebrewCharsAndChangeThemToTheirCodes(String text){
    	char [] textCharArray = text.toCharArray();
    	StringBuffer changedText = new StringBuffer();
    	for (int i=0; i<textCharArray.length; i++){
    		int charIntValue = (int)textCharArray[i];
    		if (charIntValue>=1488 && charIntValue<=1514){
    			changedText.append("&#"+charIntValue+";");
    		}
    		else changedText.append(textCharArray[i]);
    	}
    	return changedText.toString();
    }

	public static boolean isHebrew (String text){
		char [] charArray = text.toCharArray();
		boolean foundHebrewChar = false;
		int i=0;
		while (foundHebrewChar == false && i<charArray.length){
			int charIntValue = (int)text.charAt(i);
			foundHebrewChar = ((charIntValue >=1488 && charIntValue<=1514) || (charIntValue >=224 && charIntValue<=250));

		    i++;
		}
		return foundHebrewChar;
	}

	public static String checkForHebrewCharsAndChangeThemToAscii(String text){
			char [] textCharArray = text.toCharArray();
			StringBuffer changedText = new StringBuffer();
			for (int i=0; i<textCharArray.length; i++){
				int charIntValue = (int)textCharArray[i];
				if (charIntValue>=1488 && charIntValue<=1514){
					charIntValue = charIntValue-1488+224;
					changedText.append((char)charIntValue);

				}
				else changedText.append(textCharArray[i]);
			}
			return changedText.toString();
		}

    public static String getFormatedDate(long date){
    	if (date!=0) return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(date));
    	return ("All Year");
	}

	public static int getMonthNumByName(String monthName){
		for (int i=0; i<monthNames.length; i++){
			if (monthNames[i].equals(monthName)) return i;
		}
		return 0;
	}
	public static int getMonthNumByFullName(String monthFullName){
		for (int i=0; i<monthFullNames.length; i++){
			if (monthNames[i].equals(monthFullName)) return i;
		}
		return 0;
	}

	public static String getMonthName(int monthNum){
    	return monthNames[monthNum];
    }

	public static String getMonthFullName(int monthNum){
		return monthFullNames[monthNum];
	}

    public static char getLetter(int num){
    	return abc [num];
    }

    public static boolean isANumber (String str){
    	try {
    		Integer.parseInt(str);
    		return true;
    	}
    	catch (NumberFormatException nfe){
    		return false;
    	}
    }



    public static String getPath(){
    	return PATH;
    }

    public static int getDaysAsNew(){
    	return DAYS_AS_NEW;
    }

    public static String getExpiredFileNameAndPath(){
    	return EXPIRED_FILE_NAME_AND_PATH;
    }

    public static String getServerName(){
    	return SERVER_NAME;
    }

    public static String getDatabaseName(){
    	return DATABASE_NAME;
    }

    public static String getHomeDirPath(){
    	return HOME_DIR_PATH;
    }

	/**
	 * Returns the sTATIC_PAGES_BASE_DIR.
	 * @return String
	 */
	public static String getStaticPagesBaseDir() {
		return STATIC_PAGES_BASE_DIR;
	}

	public static String getVersionName(){
		return VERSION_NAME;
	}

	public static void executeCommand (String externCommand) throws IOException {
		Process proc = Runtime.getRuntime().exec(externCommand);
		try {
			proc.waitFor();
		}
		catch (InterruptedException e) {
			System.out.println("InterruptedException raised: "+e.getMessage());
		}

	}

	public static void executeCommand (String [] externCommands, String [] envp) throws IOException {
			Process proc = Runtime.getRuntime().exec(externCommands);
			try {
				proc.waitFor();
			}
			catch (InterruptedException e) {
				System.out.println("InterruptedException raised: "+e.getMessage());
			}

		}
	public static String getMailAddress(String englishName){
		String mailAddress="";
		if (englishName!=null && englishName.indexOf(" ")!=-1){
			WordsTokenizer wt = new WordsTokenizer(" ");
			int i=0;
			String firstName = (String) wt.getSubstringsList(englishName).get(i);
			i++;
			String middleName="";
			if (wt.getSubstringsList(englishName).size()==3) {
				middleName = (String) wt.getSubstringsList(englishName).get(i);
				i++;
			}
			String lastName = (String) wt.getSubstringsList(englishName).get(i);
			mailAddress = firstName.toLowerCase()+"."+middleName.toLowerCase()+lastName.toLowerCase()+"@"+getMailServerName();
			return mailAddress;
		}
		return mailAddress;
	}

	public static String moveHebrewCharsFromAsciiToHebrewCharset(String string){
		char [] textCharArray = string.toCharArray();
		StringBuffer changedText = new StringBuffer();
		for (int i=0; i<textCharArray.length; i++){
			int charIntValue = (int)textCharArray[i];

			if (charIntValue>=224 && charIntValue<=250){
				//System.out.println("The char value before the change: "+charIntValue+" "+textCharArray[i]);
				charIntValue = charIntValue-224+1488;
				changedText.append((char)charIntValue);
				//System.out.println("The char value after the change: "+charIntValue+" "+textCharArray[i]);
			}
			else changedText.append((char)charIntValue);
		}
		//System.out.println(changedText.toString());
		return changedText.toString();
	}


	public static String getMailServerName(){
		return MAIL_SERVER_NAME;
	}



	/**
	 * @return
	 */
	public static String getLogDir() {
		return LOG_DIR;
	}

	/**
	 * @return
	 */
	public static double getKeywordsOccurancesFrequencyThreshold() {
		return keywordsOccurancesFrequencyThreshold;
	}

	public static String getWorkersMailAddressesToInformOnNewPages(){
		return WORKERS_MAIL_ADDRESSES_TO_INFORM_ON_NEW_PAGES;
	}

	public static String[] [] getHujiIpsRange(){
		String [] [] ipsRange = { {"132.64.0.0" ,"132.65.255.255"},
								  { "128.139.0.0","128.139.31.255"}};
		return ipsRange;
	}

	public static String firstLetterToUppercase(String s){
		return  s.substring(0,1).toUpperCase()+s.substring(1);

	}



	public static void main(String [] args){
		Calendar c = new GregorianCalendar();
		c.set(2004,11,1);
		System.out.println(c.getTimeInMillis());

	}

	/*public static void main(String[] args){
		int i=100;
		while (i<325){

			File f1 = new File("/mnt/ard_tomcat/webapps/huard_new/html/deleted_htmls/"+i+".html");
			if (f1.exists()){
				System.out.println("Found: "+f1.getName());
				int j=i+10000;
				File f2 = new File ("/mnt/ard_tomcat/webapps/huard_new/html/deleted_htmls/"+j+".html");
				if (! f2.exists()){
					f1.renameTo(f2);
					System.out.println("Renamed to: "+f2.getName());
				}
			}

		i++;
		}

	}

	/*
			 * This part handels hebrew chars
			 *
			 *
			 *
			 *
			 try{
				FileInputStream fis = new FileInputStream(new File("/mnt/ard_yair/junk/HUARD.Workers.sql"));
				BufferedWriter bw = new BufferedWriter ( new FileWriter("/mnt/ard_yair/junk/1255i.sql"));

				int i;
				int count=0;

				while ((i=fis.read())!=-1){// && count<30000 ){
					//System.out.print(i+" ");
					if (i>=128){// && i<=250) {
						//System.out.println((char)i+" "+i+" "+(char)(i+55)+" "+(i+55));
						i=i-160+1488;
					}
					//if (i>=224 && i<=250) {
					//					//System.out.println((char)i+" "+i+" "+(char)(i+55)+" "+(i+55));
					//	i=i-224+1488;
					//}
					//System.out.print(i+" "+(char)i);
					if (i<1520) bw.write(i);
					//bw.write(i);


					if (count%500==0) System.out.println(count);
					count++;
				}
				fis.close();

				bw.close();
			}

			catch(IOException e){
				System.out.print(e);
			}

			*
			*
			*
			*/



}
