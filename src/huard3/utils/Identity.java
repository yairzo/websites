package huard3.utils;

public class Identity {
	
	private PageQuery databaseQuery;
	private String username;
	private String password;
	private String usersDeskId;
	
	public Identity(){
		databaseQuery = new PageQuery();
		username="";
		password="";
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public boolean isAuthorized(){
		if (!(username.equals(""))){
			if (databaseQuery.isUserAuthorized(username,password)) return true;
		    else return false;
		}
		else return false;
	}
	
	public boolean isAuthorizedToEdit(String pagesDeskId){
	   if (!(username.equals(""))){
	   	String usersDeskId = databaseQuery.getUsersDeskIdByUsername(username);
	    if ((usersDeskId.equals(pagesDeskId))||(usersDeskId.equals("ALL"))) return true;
	    else return false;
	   }
	   else return false;
	}
	
	
		 
	
	
	
	
	
	     

	/**
	 * Returns the deskId.
	 * @return String
	 */
	public String getUsersDeskId() {
		if (usersDeskId==null) usersDeskId = databaseQuery.getUsersDeskIdByUsername(username);
		return usersDeskId;
	}
	

	
	

	/**
	 * @param string
	 */
	public void setUsersDeskId(String string) {
		usersDeskId = string;
	}

}

