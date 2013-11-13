package huard3.utils;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yair
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PagesTextsUpdate extends UpdateDatabase{
	
    public PagesTextsUpdate (){
    	super ();
    }
    
    public boolean insertTextsToStaticPagesTextsTable(String fileName, String text, String beginOfText, String html){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
	    	statement.executeUpdate("INSERT INTO StaticPagesTexts SET fileName=\""+fileName+"\", text=\""+text+"\", beginOfText=\""+beginOfText+"\", tags=\""+html+"\";");
    		return true;
    	}
    	catch (SQLException e){
		System.out.println(fileName);
    		System.out.println(e);
    		return true;
    	}
    	
    }
    
    public boolean insertTextsToInfoPagesTextsTable(String ardNum, String text, String beginOfText, String html){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
	    	statement.executeUpdate("INSERT INTO InfoPagesTexts SET ardNum=\""+ardNum+"\", text=\""+text+"\", beginOfText=\""+beginOfText+"\", tags=\""+html+"\";");
    		return true;
    	}
    	catch (SQLException e){
    		System.out.println(e);
    		return true;
    	}
    	
    }
    
    public boolean cleanPagesTextsTable(String pageType){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
	    	statement.executeUpdate("DELETE FROM "+pageType+"PagesTexts;");
    		return true;
    	}
    	catch (SQLException e){
    		System.out.println ("Page type doesn't exist niether the table you wanted to clean or another problem updating database: " + e);
    		return false;
    	}
    }
    	
    
    	
    	
}
