package huard.iws.db;

import huard.iws.model.CallOfProposal;
import huard.iws.model.TextualPage;

import java.util.List;

public interface PageWordsIndexerDao {

	public List<CallOfProposal> getLatelyUpdatedInfoPages(long runsInterval, String server);

	public List<TextualPage> getLatelyUpdatedPubPages(long runsInterval, String server);

	public void deleteLatelyUpdatedInfoPagesFromIndexTable(List<CallOfProposal> indexedInfoPages,boolean init, String server);

	public void deleteLatelyUpdatedPubPagesFromIndexTable(List<TextualPage> indexedTextualPages,boolean fullIndex,String server);

	public int getEnglishDesk (String deskId,String server);

	public int getHebrewDesk (String deskId,String server);

	public void insertWordToInfoPagesIndexTable(String columnsvalues,String server);

	public void insertWordToTextualPagesIndexTable(String columnsvalues,String server);

	public void purgeInfoPagesIndexTable(String server);

	public void purgePubPagesIndexTable(String server);

}
