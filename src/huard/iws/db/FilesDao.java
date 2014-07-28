package huard.iws.db;

import huard.iws.model.Abstract;
import huard.iws.model.Attachment;

public interface FilesDao {
	
	public Attachment getTextualPageFile(String filename);
	
	public Attachment getCallForProposalFile(String filename);

	public Abstract getAbstractFile(String filename);

}
