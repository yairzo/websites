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
public class DocsTextsTableUpdate extends UpdateDatabase{
	
    public DocsTextsTableUpdate (){
    	super ();
    }
    
    public boolean insertTextsToStaticPagesTextsTable(String fileName, String text, String beginOfText){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
	    	statement.executeUpdate("INSERT INTO StaticPagesTexts SET fileName=\""+fileName+"\", text=\""+text+"\", beginOfText=\""+beginOfText+"\";");
    		return true;
    	}
    	catch (SQLException e){
    		System.out.println("Couldn't insert texts to DocsTexts table: "+e);
    		return true;
    	}
    	
    }
    
    	
    public boolean cleanDocsTextsTable(){
    	try{
    		Statement statement = getCurrentConnection().createStatement();
	    	statement.executeUpdate("DELETE FROM StaticPagesTexts;");
    		return true;
    	}
    	catch (SQLException e){
    		System.out.println (e);
    		return false;
    	}
    }
    	
    	
}
