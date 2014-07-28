package huard.iws.db;

import java.util.List;

import huard.iws.model.Abstract;
import huard.iws.model.RegistrationForm;

public interface RegistrationFormDao {

	public RegistrationForm getRegistrationForm(int id);
	
	public int insertRegistrationForm(RegistrationForm registrationForm);
	
	public void updateRegistrationForm(RegistrationForm registrationForm);
	
	public List<RegistrationForm> getRegistrationForms();

	public int insertAttachmentToRegistrationForm(int registrationFormId, Abstract attachment);

	public void deleteFile(int attachmentId);

}
