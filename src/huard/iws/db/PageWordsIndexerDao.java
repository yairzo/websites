package huard.iws.db;

import huard.iws.model.CallOfProposal;

import java.util.List;

public interface PageWordsIndexerDao {

	public List<CallOfProposal> getLatelyUpdatedInfoPages(long runsInterval, String server);
	
	public void deleteLatelyUpdatedInfoPagesFromIndexTable(long runsInterval, String server);

	public int getEnglishDesk (String deskId);

	public int getHebrewDesk (String deskId);

	public void insertWordToInfoPagesIndexTable(String word,int ardNum,String server);
	
	public void purgeInfoPagesIndexTable(String server);

}
