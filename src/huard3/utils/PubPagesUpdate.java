package huard3.utils;
import huard3.actions.*;
import java.sql.*;



public class PubPagesUpdate extends UpdateDatabase {

	public PubPagesUpdate(){
		super();
	}

	public boolean insertPubPage(PubPage pubPage){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate(getInsertSqlString(pubPage));
			statement.executeUpdate("INSERT PubPagesLastUpdates SET ardNum="+pubPage.getArdNum()+", date="+new java.util.Date().getTime()+";");
			return true;
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}

	}

	public boolean updatePubPage(PubPage pubPage){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate(getUpdateSqlString(pubPage));
			statement.executeUpdate("DELETE FROM PubPagesLastUpdates WHERE ardNum="+pubPage.getArdNum()+";");
			statement.executeUpdate("INSERT PubPagesLastUpdates SET ardNum="+pubPage.getArdNum()+", date="+new java.util.Date().getTime()+";");
			return true;
		}
		catch (SQLException e){
			System.out.println(e);
			return false;
		}
	}

	public String getInsertSqlString(PubPage pubPage){
		String insertSqlString;
		if (pubPage.isMessage()){
			insertSqlString = "INSERT PubPages SET ardNum="+pubPage.getArdNum()+", title=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getTitle())+"\""+
				", pubDate="+pubPage.getPubDate()+", html=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getHtml())+"\", restricted="+ (pubPage.isRestricted() ? 1:0)+
				", docType=\""+pubPage.getDocType()+"\", docOwner=\""+pubPage.getDocOwner()+"\", deskId=\""+pubPage.getDeskId()+"\", message="+(pubPage.isMessage() ? 1:0)+
				", expDate=\""+ pubPage.getExpDate() + "\", toRollingMessages="+(pubPage.isToRollingMessages() ? 1:0)+
				", stopRollingDate=\""+pubPage.getStopRollingDate() + "\", hasImage="+(pubPage.isHasImage() ? 1 :0) +
				", imageFilename=\""+pubPage.getImageFilename()+"\", fileRepresentation="+(pubPage.isFileRepresentation() ? 1 :0) +
				", link=\""+pubPage.getLink()+"\", referenceFile=\""+pubPage.getReferenceFile()+
				"\", source=\""+pubPage.getSource()+"\", internalUseDescription=\""+pubPage.getInternalUseDescription()+
				"\", onSite="+(pubPage.isOnSite() ? 1 :0)+";";
		}
		else{
			insertSqlString = "INSERT PubPages SET ardNum="+pubPage.getArdNum()+", title=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getTitle())+"\""+
				", pubDate="+pubPage.getPubDate()+", html=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getHtml())+"\", restricted="+ (pubPage.isRestricted() ? 1:0)+
				", docType=\""+pubPage.getDocType()+"\", docOwner=\""+pubPage.getDocOwner()+"\", deskId=\""+pubPage.getDeskId()+ "\", message="+(pubPage.isMessage() ? 1:0)+
				", hasImage="+(pubPage.isHasImage() ? 1 :0) + ", imageFilename=\""+pubPage.getImageFilename()+
				"\", fileRepresentation="+(pubPage.isFileRepresentation() ? 1 :0) +
				", link=\""+pubPage.getLink()+"\", referenceFile=\""+pubPage.getReferenceFile()+
				"\", source=\""+pubPage.getSource()+"\", internalUseDescription=\""+pubPage.getInternalUseDescription()+
				"\", wraper="+(pubPage.isWraper() ? 1 :0) + ", sourceToWrap=\""+pubPage.getSourceToWrap()+
				"\", onSite="+(pubPage.isOnSite() ? 1 :0)+";";
		}
		System.out.println("huard3.utils.PubPagesUpdate(): insert String: "+insertSqlString);
		return insertSqlString;
	}

	public String getUpdateSqlString(PubPage pubPage){
		String updateSqlString;
		if (pubPage.isMessage()){
			updateSqlString = "UPDATE PubPages SET title=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getTitle())+"\""+
				", pubDate="+pubPage.getPubDate()+", html=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getHtml())+"\", restricted="+ (pubPage.isRestricted() ? 1:0)+
				", docType=\""+pubPage.getDocType()+"\", docOwner=\""+pubPage.getDocOwner()+"\", deskId=\""+pubPage.getDeskId()+"\", message="+(pubPage.isMessage() ? 1:0)+
				", expDate=\""+ pubPage.getExpDate() + "\", toRollingMessages="+(pubPage.isToRollingMessages() ? 1:0)+
				", stopRollingDate=\""+pubPage.getStopRollingDate()+ "\", hasImage="+(pubPage.isHasImage() ? 1 :0) +
				", imageFilename=\""+pubPage.getImageFilename() + "\", fileRepresentation="+(pubPage.isFileRepresentation() ? 1 :0) +
				", link=\""+pubPage.getLink()+"\", referenceFile=\""+pubPage.getReferenceFile()+
				"\", source=\""+pubPage.getSource()+"\", internalUseDescription=\""+pubPage.getInternalUseDescription()+
				"\", onSite="+(pubPage.isOnSite() ? 1 :0)+" WHERE ardNum="+pubPage.getArdNum()+";";
		}
		else{
			updateSqlString = "UPDATE PubPages SET title=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getTitle())+"\""+
				", pubDate="+pubPage.getPubDate()+", html=\""+Utils.moveHebrewCharsFromAsciiToHebrewCharset(pubPage.getHtml())+"\", restricted="+ (pubPage.isRestricted() ? 1:0)+
				", docType=\""+pubPage.getDocType()+"\", docOwner=\""+pubPage.getDocOwner()+"\", deskId=\""+pubPage.getDeskId()+"\", message="+(pubPage.isMessage() ? 1:0)+
				", hasImage="+(pubPage.isHasImage() ? 1 :0) + ", imageFilename=\""+pubPage.getImageFilename()+"\", fileRepresentation="+(pubPage.isFileRepresentation() ? 1 :0) +
				", link=\""+pubPage.getLink()+"\", referenceFile=\""+pubPage.getReferenceFile()+
				"\", source=\""+pubPage.getSource()+"\", internalUseDescription=\""+pubPage.getInternalUseDescription()+
				"\", wraper="+(pubPage.isWraper() ? 1 :0) + ", sourceToWrap=\""+pubPage.getSourceToWrap()+
				"\", onSite="+(pubPage.isOnSite() ? 1 :0)+" WHERE ardNum="+pubPage.getArdNum()+";";
		}
		System.out.println("huard3.utils.PubPagesUpdate(): insert String: "+updateSqlString);
		return updateSqlString;
	}

	public boolean insertPubPageKeywordsIntoDatabase(int ardNum,String[] keywords){
		try{
			Statement updateStatement = getCurrentConnection().createStatement();
    		updateStatement.executeUpdate("DELETE FROM PubPagesKeywords WHERE ardNum="+ardNum+";");
			for(int i=0;i<keywords.length;i++){
    			String updateString = "INSERT INTO PubPagesKeywords (ardNum,keyword) VALUES ('"+ardNum+"','"+keywords[i]+"');" ;
				updateStatement.executeUpdate( updateString );
			}
			return true;
		}
		catch(SQLException e){
			System.out.println("Exception while updateing keywords: " + e);
			return false;
		}
	}

	public void deletePubPageByArdNum(int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("UPDATE PubPages SET isDeleted=1 WHERE ardNum="+ardNum+";");
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
    	}
	}

	public void revivePubPage(PubPage pubPage){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("UPDATE PubPages SET isDeleted=0 WHERE ardNum="+ pubPage.getArdNum() +";");
		}
		catch(SQLException e){
			System.out.println("Exception while querying data: " + e);
		}
	}

	public void stopMessageFromRolling(int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("UPDATE PubPages SET toRollingMessages=0 WHERE ardNum="+ardNum+";");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}

	public void insertWordToPubPagesIndexTable(String word, int ardNum){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("INSERT PubPagesIndex SET word=\""+word+"\", ardNum="+ardNum);
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}

	public void createPubPagesIndexCopyTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM PubPagesIndex_copy;");
			statement.executeUpdate("INSERT PubPagesIndex_copy SELECT * FROM PubPagesIndex;");
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}

	public void deleteFromPubPagesIndexTable(){
		try{
			Statement statement = getCurrentConnection().createStatement();
			statement.executeUpdate("DELETE FROM PubPagesIndex;");
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}


}
