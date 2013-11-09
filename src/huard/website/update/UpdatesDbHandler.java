package huard.website.update;
import huard.website.util.*;
import huard.website.baseDb.*;
import java.sql.*;

public class UpdatesDbHandler {

	public void  updatePubPagesLocationDetails(int ardNum, String category, String parentPageTitle){
		try{
			Statement statement = ConnectionSupplier.getConnectionSupplier().getConnection(Utils.getWebsiteDatabaseName(),"UPDATE").createStatement();
			category = category.replaceAll("\"","\\\\\"");
			parentPageTitle = parentPageTitle.replaceAll("\"","\\\\\"");
			String updateString = "UPDATE PubPages SET"+
			" category=\""+category+"\", parentPageTitle=\""+parentPageTitle+"\", onSite=\"1\" WHERE ardNum=\""+ardNum+"\";";


			System.out.println("huardSiteViewer.updates.UpdatesDbHandler.updatePubPagesLocationDetails(): updateString:" + updateString);
			statement.executeUpdate(updateString);
		}
		catch (SQLException e){
			System.out.println("huardSiteViewer.updates.UpdatesDbHandler.updatePubPagesLocationDetails(): "+e);
		}
	}

}
