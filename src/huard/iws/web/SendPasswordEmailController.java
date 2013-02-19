package huard.iws.web;

import huard.iws.bean.CallForProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.Person;
import huard.iws.util.RequestWrapper;
import huard.iws.util.MD5Encoder;
import huard.iws.util.SysUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class SendPasswordEmailController extends GeneralFormController{

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();

		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		String civilId=request.getParameter("username", "");
System.out.println("111111111111111111:"+ civilId);
		Person person = personService.getPersonByCivilId(civilId);
		if(person!=null){
			boolean enabled = personService.isSubscribed(person.getId());
			if(enabled){
				String subscriptionMd5 = MD5Encoder.digest(person.getId() + SysUtils.toTimestamp(System.currentTimeMillis()));
				personPrivilegeService.updateSubscriptionMd5(person.getId(),subscriptionMd5);
				mailMessageService.createPasswordMail(new PersonBean(person), subscriptionMd5);
				model.put("message", messageService.getMessage(person.getPreferedLocaleId() + ".passwordEmail.emailWasSent"));
			}
			else
				model.put("message", messageService.getMessage(person.getPreferedLocaleId() + ".passwordEmail.userNotAuthenticated"));
		}
		else
			model.put("message", messageService.getMessage("iw_IL.passwordEmail.userNotAuthenticated"));

		return new ModelAndView (this.getFormView(), model);
	}

	protected Object getFormBackingObject(RequestWrapper request, PersonBean userPersonBean) throws Exception{
		SendPasswordEmailControllerCommand command = new SendPasswordEmailControllerCommand();
		return command;
	}

	public class SendPasswordEmailControllerCommand{
	}


}
