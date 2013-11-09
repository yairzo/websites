package huard.website.session;
import huard.website.util.Utils;
import huard.website.util.WordsTokenizer;

import java.util.List;


public class Authorizer {
	private String mailAddress;
	private String password;
	private boolean authorized;
	private boolean authorizationFailure;
	//private boolean outsideHuji;
	private boolean learningMode;
	private AuthorizationDbHandler dbHandler;
	private String signup;





	public String getSignup() {
		return signup;
	}

	public void setSignup(String signup) {
		this.signup = signup;
	}

	public Authorizer(){
		dbHandler = new AuthorizationDbHandler();
		mailAddress="";
		password="";
		authorized=false;
		authorizationFailure=false;
	}

	public boolean isMopIp(String ip){
		return dbHandler.isMopIp(ip);
	}

	public boolean isAllowedMailAddress(){
		mailAddress= mailAddress.concat(" ");
		return signup.endsWith("huji.ac.il ") || signup.endsWith("hadassah.org.il");
	}

    public boolean isASignup(){
    	return (signup!=null && ! "".equals(signup));
    }

	public boolean isAllowedSignup(){
		signup= signup.concat(" ");
		return signup.endsWith("huji.ac.il ") || signup.endsWith("hadassah.org.il");
	}


	public void moveSignupToMailAddress(){
		mailAddress=signup;
		password="";
		signup="";
	}

	public void resetAll(){
		mailAddress="";
		password="";
		signup="";
		authorizationFailure=false;
	}



	public String getMailAddress() {
		return mailAddress;
	}


	public String getPassword() {
		return password;
	}





	public void setMailAddress(String string) {
		mailAddress = string;
	}


	public void setPassword(String string) {
		password = string;
	}






	public boolean checkAuthorization(String ipString, String md5){
		if ( ! authorized){
			WordsTokenizer wt = new WordsTokenizer(".");
			List addressNumsList = wt.getSubstringsList(ipString);
			int[] addressNumsArray = new int[addressNumsList.size()];
			try{
			for (int i=0; i<addressNumsArray.length; i++){
				addressNumsArray[i] = Integer.parseInt((String)addressNumsList.get(i));
			}
			}
			catch(NumberFormatException e){
				System.out.println(e);
				return false;
			}


			if(addressNumsArray[0]==132){
				if (addressNumsArray[1]>=64 && addressNumsArray[1]<=65) authorized= true;

			}
			else if (addressNumsArray[0]==128 && addressNumsArray[1]==139){
				if (addressNumsArray[2]<=31) authorized=true;

			}
			if (ipString.equals("132.64.14.69")) authorized=false;

			if (md5 != null && md5.length() > 0)
				authorized = dbHandler.isAuthorized(md5);

			if (! authorized && mailAddress!=null && ! "".equals(mailAddress)) {
				authorized= dbHandler.isAuthorized(mailAddress,password);
				if (!authorized) authorized = dbHandler.isNonHujiAuthorized(mailAddress);
				if (!authorized) authorizationFailure = true;
			}

		}
		return authorized;
	}

	/**
	 * @return
	 */
	public boolean isAuthorized(String ipString, String md5) {
		if (authorized==false) checkAuthorization(ipString, md5);
		return authorized;
	}


	public boolean isLearningMode() {
		return learningMode;
	}

	public boolean isAuthorizedForLearningMode(String ipString){
		return (learningMode && ipString.equals(Utils.getLearningModeAuthorizedIp()));
	}

	public void setLearningMode(boolean learningMode) {
		this.learningMode = learningMode;
	}

	public boolean isAuthorizationFailure() {
		return authorizationFailure;
	}

	public void setAuthorizationFailure(boolean authorizationFailure) {
		this.authorizationFailure = authorizationFailure;
	}




}
