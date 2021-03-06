package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.PersonPrivilege;
import huard.iws.service.LocksService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ActivePersonsController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		ActivePersonsControllerCommand aCommand = (ActivePersonsControllerCommand)command;
		Map<String,Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		if (action.equals("search"))
			aCommand.getListView().setPage(1);
		if (action.equals("deleteLock")){
			String lockId= request.getParameter("lockId", "");
			if(!lockId.isEmpty()){
				StringTokenizer st = new StringTokenizer(lockId, "~"); 
				if(st.countTokens()==3){
					String id=st.nextToken();
					String subject=st.nextToken();
					String arguments=st.nextToken();
					locksService.releaseLock(id, subject, arguments);
				}
			}
		}

		return new ModelAndView(new RedirectView(getSuccessView()),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		List<PersonPrivilege> personsPrivileges =  personPrivilegeService.getActivePersons();
		model.put("activePersons", personsPrivileges);
		return new ModelAndView ("activePersonsList",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		ActivePersonsControllerCommand command = new ActivePersonsControllerCommand();
		return command;
	}

	public class ActivePersonsControllerCommand{
		private ListView listView = new ListView();

		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}
	}
	
	private LocksService locksService;

	public void setLocksService(LocksService locksService) {
		this.locksService = locksService;
	}
}
