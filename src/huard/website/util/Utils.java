package huard.website.util;
import huard.website.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import java.io.*;

public class Utils {
	private static final String SERVER_NAME="localhost.localdomain";
	private static final String MAIL_SERVER_NAME="ard.huji.ac.il";
    private static final String WEBSITE_DATABASE_NAME="HUARD";
	private static final String VERSION_NAME="huard";
   	private static final String[] monthNames= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","All Year"};
	private static final String[] monthFullNamesEnglish= {"January","February","March","April","May","June","July","August","September","October","November","December","All Year"};
    private static final String[] monthFullNamesHebrew= {"ינואר","פברואר","מרץ","אפריל","מאי","יוני","יולי","אוגוסט","ספטמבר","אוקטובר","נובמבר","דצמבר","כל השנה"};
	private static final char[] abc = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    private static final String PATH = "/usr/local/tomcat/webapps/"+VERSION_NAME+"/";
    private static final String EXPIRED_FILE_NAME_AND_PATH = "/home/"+VERSION_NAME+"/expired.txt";
    private static final String HOME_DIR_PATH = "/home/"+VERSION_NAME+"/";
    private static final String STATIC_PAGES_BASE_DIR = "/usr/local/apache2/htdocs";
    private static final String LOG_DIR = "/var/log/"+VERSION_NAME+"/";
    private static final int DAYS_AS_NEW=14;

    private static final double keywordsOccurancesFrequencyThreshold = 0.001;
    private static final String WORKERS_MAIL_ADDRESSES_TO_INFORM_ON_NEW_PAGES="aviad.laxer@ard.huji.ac.il, rivka.goldshtein@ard.huji.ac.il";
    private static final int resultsInPage = 1000;
    private static final String WEBSITE_ONLINE_DATE = "01/12/2006";
    private static final String learningModeAuthorizedIp = "132.64.14.168";
    private static final int numOfLastPublishedInfoPagesToRoll=8;
    private static final int numOfDaysBeforeSubmissionToRollInfoPage=10;
    public static final String DEFAULT_PUB_PAGE_CATEGORY = "harashut_lemop";
    public static boolean HEB = true;
    public static boolean ENG = false;

    public static final long DAY = 1000*60*60*24;

    private static Properties webappProperties;

    public static Properties getAppProperties(String pathToApp){
		if (webappProperties != null)
			return webappProperties;
    	try{
			BufferedReader br = new BufferedReader( new FileReader(System.getenv("TOMCAT_HOME") + "/conf/webapp.conf"));
			String line;
			Properties prop = new Properties();
			while ((line=br.readLine())!=null){
				if (line.indexOf("=")!=-1){
					String key = line.substring(0,line.indexOf("="));
					String value = line.substring(line.indexOf("=")+1);
					prop.setProperty(key.trim(),value.trim());
				}
			}
			return prop;
		}
		catch(IOException e){
			System.out.println(e);
			return null;
		}
	}


    public static int getNumOfDaysBeforeSubmissionToRollInfoPage() {
		return numOfDaysBeforeSubmissionToRollInfoPage;
	}

	public static String getWebsiteOnlineDate() {
		return WEBSITE_ONLINE_DATE;
	}

	public static String addBackslashBeforeDoubleQuots(String s){
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos = s.indexOf("\""))!=-1){
			sb.append(s.substring(0, pos)+"\\\"");
			s = s.substring(pos+1);
		}
		sb.append(s);
		return sb.toString();
	}

	public static TabledInfoPage[] getTabledInfoPagesArrayFromTabledInfoPagesList(List infoPagesList){
		if (infoPagesList.isEmpty())
			return null;
		TabledInfoPage[] infoPages = new TabledInfoPage[infoPagesList.size()];
		for (int i=0; i<infoPages.length; i++)
			infoPages[i] = (TabledInfoPage)infoPagesList.get(i);
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

	public static String firstLetterToUppercase(String s){
		return s.substring(0,1).toUpperCase()+s.substring(1);
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
		if (text == null)
			return false;
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

	public static boolean isHasValue(String s){
		return (s!=null && s.indexOf("xxxxx")==-1 && ! s.equals("") && ! s.equals("null") );
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

    public static String getFormatedDate(long date, boolean heb){
    	if (date!=0) return DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(new Date(date));
    	return (heb ? "כל השנה" : "All Year");
	}

	public static int getMonthNumByName(String monthName){
		for (int i=0; i<monthNames.length; i++){
			if (monthNames[i].equals(monthName)) return i;
		}
		return 0;
	}
	public static int getMonthNumByFullName(String monthFullName){
		for (int i=0; i<monthFullNamesEnglish.length; i++){
			if (monthNames[i].equals(monthFullName)) return i;
		}
		return 0;
	}

	public static String getMonthName(int monthNum){
    	return monthNames[monthNum];
    }

	public static String getMonthFullName(int monthNum, boolean heb){
		if (heb) return monthFullNamesHebrew[monthNum];
		else return monthFullNamesEnglish[monthNum];
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

    public static String getWebsiteDatabaseName(){
    	return WEBSITE_DATABASE_NAME;
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



	public static void main(String [] args){
		Calendar c = new GregorianCalendar();
		c.set(2004,11,1);
		System.out.println(c.getTimeInMillis());

	}

	public static int getResultsInPage() {
		return resultsInPage;
	}

	public static String getLearningModeAuthorizedIp() {
		return learningModeAuthorizedIp;
	}

	public static int getNumOfLastPublishedInfoPagesToRoll() {
		return numOfLastPublishedInfoPagesToRoll;
	}

}
