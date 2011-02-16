package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.Subject;
import huard.iws.service.SubjectService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditPersonPostSubscriptionController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(EditPostController.class);


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		PersonBean personBean = (PersonBean) command;

		Map<String, Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		String redirectTarget = getSuccessView();
		String cpoi = "" + personBean.getId();
		String cp = request.getParameter("cp", "");
		if (action.equals("cancel") || action.equals("done")){
			if (! cp.isEmpty()){
				redirectTarget = cp;
				cpoi = request.getParameter("cpoi", "");
			}
			newModel.put("id", cpoi);
		}
		if (! action.equals("cancel")){
			PersonBean existingPersonBean = new PersonBean(personService.getPerson(personBean.getId()));
			applyPostDetails (existingPersonBean, personBean);
			personService.updatePerson(existingPersonBean.toPerson());
			newModel.put("id", existingPersonBean.getId());
		}
		return new ModelAndView( new RedirectView(redirectTarget), newModel);
	}

	private void applyPostDetails(PersonBean existingPersonBean, PersonBean personBean){
		existingPersonBean.setSubjectsIds(personBean.getSubjectsIds());
		existingPersonBean.setPostReceiveDays(personBean.getPostReceiveDays());
		existingPersonBean.setPostReceiveHour(personBean.getPostReceiveHour());
		existingPersonBean.setPostReceiveImmediately(personBean.isPostReceiveImmediately());
		existingPersonBean.setReadsUTF8Mails(personBean.isReadsUTF8Mails());
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		int id = request.getIntParameter("id", 0);
		if ( userPersonBean.getId() != id && ! userPersonBean.isAuthorized("ADMIN"))
			return new ModelAndView(new RedirectView("accessDenied.html"));

		PersonBean personBean = (PersonBean) model.get("command");
		Subject rootSubject = subjectService.getSubject(1, personBean.getPreferedLocaleId());
		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, personBean.getPreferedLocaleId());
		model.put("rootSubject", rootSubjectBean);

		LanguageUtils.applyLanguage(model, request, response, personBean.getPreferedLocaleId());

		return new ModelAndView("editPersonPostSubscription", model);
	}




	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		PersonBean personBean = new PersonBean();
		if ( ! isFormSubmission(request.getRequest())){
			int id = request.getIntParameter("id", 0);
			if (id > 0)
				personBean = new PersonBean (personService.getPerson(id));
		}
		return personBean;
	}


	private SubjectService subjectService;


	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

}
