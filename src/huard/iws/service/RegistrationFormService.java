package huard.iws.service;

import huard.iws.model.Abstract;
import huard.iws.model.RegistrationForm;
import java.util.List;

public interface RegistrationFormService {

	public RegistrationForm getRegistrationForm(int id);
	
	public int insertRegistrationForm(RegistrationForm registrationForm);
	
	public void updateRegistrationForm(RegistrationForm registrationForm);
	
	public List<RegistrationForm> getRegistrationForms();

	public int insertAttachmentToRegistrationForm(int registrationFormId, Abstract attachment);
	
	public void deleteFile(int attachmentId);
	

}
