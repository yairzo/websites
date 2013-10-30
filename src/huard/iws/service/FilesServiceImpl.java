package huard.iws.service;

import huard.iws.db.FilesDao;
import huard.iws.model.Attachment;

public class FilesServiceImpl implements FilesService{
	
	public Attachment getTextualPageFile(String filename){
		return filesDao.getTextualPageFile(filename);
	}
	
	private FilesDao filesDao;

	public void setFilesDao(FilesDao filesDao) {
		this.filesDao = filesDao;
	}
	
	

}
