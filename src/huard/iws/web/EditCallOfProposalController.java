package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.SubjectBean;
import huard.iws.model.PersonPrivilege;
import huard.iws.model.Subject;
import huard.iws.service.SubjectService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditCallOfProposalController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		CallOfProposalCommand aCommand = (CallOfProposalCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		Subject rootSubject = subjectService.getSubject(1, "iw_IL");
		SubjectBean rootSubjectBean = new SubjectBean(rootSubject, "iw_IL");
		model.put("rootSubject", rootSubjectBean);

		return new ModelAndView ("editCallOfProposal",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		CallOfProposalCommand command = new CallOfProposalCommand();
		return command;
	}

	public class CallOfProposalCommand{
		private int id = 11;
		private List<Integer> subjectsIds;

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public List<Integer> getSubjectsIds() {
			return subjectsIds;
		}

		public void setSubjectsIds(List<Integer> subjectsIds) {
			this.subjectsIds = subjectsIds;
		}

	}
	private SubjectService subjectService;


	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
}
