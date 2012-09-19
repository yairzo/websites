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

public class EditTextualPageController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		TextualPageCommand aCommand = (TextualPageCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		return new ModelAndView ("editTextualPage",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		TextualPageCommand command = new TextualPageCommand();
		return command;
	}

	public class TextualPageCommand{
		private int id = 100;

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	}
}
