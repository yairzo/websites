package huard.website.session;

import huard.website.util.PasswordGenerator;

public class Subscriber {
	private String signup;
	private AuthorizationDbHandler dbHandler;

	public Subscriber(){
		dbHandler = new AuthorizationDbHandler();
	}

	public boolean subscribe(){
		if (signup!=null){
			String password;
			password = dbHandler.getPasswordByMailAddress(signup);
			if (password.equals("")) password  = new PasswordGenerator(6).getPassword();
			dbHandler.insertUserToAuthorizedOutsideHujiTable(signup,password);
			//MailCollector.getMailCollector().add("webmaster@ard.huji.ac.il", signup, " " ,"yair@ard.huji.ac.il" , "webmaster@ard.huji.ac.il" , "Password for ARD Site" , "Your Password: "+password, "" );
			return true;
		}
		return false;
	}

	public String getSignup() {
		return signup;
	}

	/**
	 * @param string
	 */
	public void setSignup(String string) {
		signup = string;
	}



}
