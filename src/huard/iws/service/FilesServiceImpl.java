package huard.iws.service;

import huard.iws.db.FilesDao;
import huard.iws.model.Abstract;
import huard.iws.model.Attachment;

public class FilesServiceImpl implements FilesService{
	
	public Attachment getTextualPageFile(String filename){
		return filesDao.getTextualPageFile(filename);
	}
	
	public Attachment getCallForProposalFile(String filename){
		return filesDao.getCallForProposalFile(filename);
	}

	public Abstract getAbstractFile(String filename){
		return filesDao.getAbstractFile(filename);
	}
	

	private FilesDao filesDao;

	public void setFilesDao(FilesDao filesDao) {
		this.filesDao = filesDao;
	}
	
	

}
