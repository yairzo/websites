
package huard3.utils;
import java.io.*;
//import java.util.*;


public class MailAliasesCreator {
	private BufferedReader br;
	private BufferedWriter bw;
	//private List usersList;
	private WorkersQuery workersQuery;
	
	public MailAliasesCreator(){
		try{
			br = new BufferedReader(new FileReader("/etc/passwd"));
		}
		catch (IOException e){
			System.out.println(e);
		}
		workersQuery = new WorkersQuery();
	}
	
	public void createAliases(){
		try{
			String line;
			while ((line=br.readLine())!=null){
				String username = line.substring(0,line.indexOf(':'));
				String usernameFirstNamePart = username;
				String usernameLastNamePart="";
				usernameFirstNamePart = usernameFirstNamePart.substring(0,1).toUpperCase().concat(usernameFirstNamePart.substring(1));
				String [] fullName = new String[2];
				while ( (fullName = workersQuery.getWorkersFullNameByUsernameParts(usernameFirstNamePart,usernameLastNamePart))[1].equals("")){
					usernameLastNamePart = usernameFirstNamePart.substring(usernameFirstNamePart.length()-1).concat(usernameLastNamePart);
					usernameLastNamePart = usernameLastNamePart.substring(0,1).toUpperCase().concat(usernameLastNamePart.substring(1));
					usernameFirstNamePart = usernameFirstNamePart.substring(0, usernameFirstNamePart.length()-1);
				}
				bw = new BufferedWriter(new FileWriter("/home/yair/junk/"+fullName[0]+"."+fullName[1]));
				bw.write("&"+username);
				bw.close();
			}
		}
		catch(IOException ie){
			System.out.println(ie);			
		}		
	}

}