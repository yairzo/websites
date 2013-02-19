package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.MD5Encoder;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ResetPasswordController extends GeneralFormController{

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		String subscriptionMd5=request.getParameter("subscriptionMd5", "");
		if(!subscriptionMd5.isEmpty()){
			int personId = personService.getPersonIdBySubscriptionMd5(subscriptionMd5);
			String password=request.getParameter("password", "");
			personService.updatePersonPrivilegePassword(personService.getPerson(personId),MD5Encoder.digest(password));
		}
		String userMessage = messageService.getMessage("iw_IL.general.resetPasswordSuccess");
		request.getSession().setAttribute("userMessage", userMessage);
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("cp", "j_acegi_logout");
		request.getSession().setAttribute("userPerson", null);
		return new ModelAndView (new RedirectView("viewMessage.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		String subscriptionMd5=request.getParameter("md5", "");
		boolean isRegistrated=true;
		if(!subscriptionMd5.isEmpty()){
			int personId = personService.getPersonIdBySubscriptionMd5(subscriptionMd5);
			if(personId>0){
				if(personService.isDisabled(personId))
					isRegistrated=false;
			}
			else
				isRegistrated=false;
		}
		model.put("isRegistrated",isRegistrated);
		model.put("subscriptionMd5", subscriptionMd5);
		return new ModelAndView ("resetPassword",model);
	}

	protected Object getFormBackingObject(RequestWrapper request, PersonBean userPersonBean) throws Exception{
		ResetPasswordCommand command = new ResetPasswordCommand();
		
		return command;
	}

	public class ResetPasswordCommand{
		String md5="";
		
		public String getMd5() {
			return md5;
		}
		public void setMd5(String md5) {
			this.md5 = md5;
		}
		
		
	}


}
