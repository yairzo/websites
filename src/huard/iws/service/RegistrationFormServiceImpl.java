package huard.iws.service;

import huard.iws.db.RegistrationFormDao;
import huard.iws.model.Abstract;
import huard.iws.model.Attachment;
import huard.iws.model.RegistrationForm;

import java.util.List;

public class RegistrationFormServiceImpl implements RegistrationFormService {

	public RegistrationForm getRegistrationForm(int id) {
		return registrationFormDao.getRegistrationForm(id);
	}

	public int insertRegistrationForm(RegistrationForm registrationForm){
		return registrationFormDao.insertRegistrationForm(registrationForm);
	}
	
	public void updateRegistrationForm(RegistrationForm registrationForm){
		registrationFormDao.updateRegistrationForm(registrationForm);
	}

	public List<RegistrationForm> getRegistrationForms(){
		return registrationFormDao.getRegistrationForms();
	}

	public int insertAttachmentToRegistrationForm(int registrationFormId, Abstract attachment){
		return registrationFormDao.insertAttachmentToRegistrationForm(registrationFormId, attachment);
	}

	public void deleteFile(int attachmentId){
		registrationFormDao.deleteFile(attachmentId);
	}
	
	
	private RegistrationFormDao registrationFormDao;

	public void setRegistrationFormDao(RegistrationFormDao registrationFormDao) {
		this.registrationFormDao = registrationFormDao;
	}



	
}
