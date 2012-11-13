package huard.iws.db;

import huard.iws.model.CallOfProposalOld;
import huard.iws.model.Desk;
import huard.iws.model.TextualPageOld;

import java.util.List;

public interface PagesWordsIndexerDao {

	public List<CallOfProposalOld> getLatelyUpdatedInfoPages(long runsInterval, String server);

	public List<TextualPageOld> getLatelyUpdatedPubPages(long runsInterval, String server);

	public void deleteLatelyUpdatedInfoPagesFromIndexTable(List<CallOfProposalOld> indexedInfoPages,boolean init, String server);

	public void deleteLatelyUpdatedPubPagesFromIndexTable(List<TextualPageOld> indexedTextualPageOlds,boolean fullIndex,String server);

	public int getEnglishDesk (String deskId,String server);

	public int getHebrewDesk (String deskId,String server);

	public int insertWordsToInfoPagesIndexTable(List<String> words, int callOfProposalId, String server);

	public int insertWordsToPubPagesIndexTable(List<String> words, int textualPageId, String server);

	public void purgeInfoPagesIndexTable(String server);

	public void purgePubPagesIndexTable(String server);
	
	public List<Desk> getDesks(String server);


}
