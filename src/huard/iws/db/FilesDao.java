package huard.iws.db;

import huard.iws.model.Attachment;

public interface FilesDao {
	
	public Attachment getTextualPageFile(String filename);

}
