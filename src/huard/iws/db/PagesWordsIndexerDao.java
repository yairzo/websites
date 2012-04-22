package huard.iws.db;

import huard.iws.model.CallOfProposal;
import huard.iws.model.Desk;
import huard.iws.model.TextualPage;

import java.util.List;

public interface PagesWordsIndexerDao {

	public List<CallOfProposal> getLatelyUpdatedInfoPages(long runsInterval, String server);

	public List<TextualPage> getLatelyUpdatedPubPages(long runsInterval, String server);

	public void deleteLatelyUpdatedInfoPagesFromIndexTable(List<CallOfProposal> indexedInfoPages,boolean init, String server);

	public void deleteLatelyUpdatedPubPagesFromIndexTable(List<TextualPage> indexedTextualPages,boolean fullIndex,String server);

	public int getEnglishDesk (String deskId,String server);

	public int getHebrewDesk (String deskId,String server);

	public int insertWordsToInfoPagesIndexTable(List<String> words, int callOfProposalId, String server);

	public int insertWordsToPubPagesIndexTable(List<String> words, int textualPageId, String server);

	public void purgeInfoPagesIndexTable(String server);

	public void purgePubPagesIndexTable(String server);
	
	public List<Desk> getDesks(String server);


}
