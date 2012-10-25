package huard.iws.exec;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



import java.net.*;
import java.io.*;

public class checkUrl {

	public static void main (String [] args){
		
		try {
			System.out.println("hehre");
		    URL myURL = new URL("https://ard.huji.ac.il/iws/fileViewer?conferenceProposalId=359&attachFile=programAttach");
		    URLConnection myURLConnection = myURL.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
		    System.out.println("hehre333333" );
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) 
		    		System.out.println(inputLine);
		    in.close();
		} 
		catch (MalformedURLException e) { 
		    // new URL() failed
		    // ...
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
		}

	}	


}
