package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonListAttributionBean;
import huard.iws.model.AList;
import huard.iws.model.AListInstruction;
import huard.iws.model.Person;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.ListInstructionListService;
import huard.iws.service.ListListService;
import huard.iws.service.ListService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PersonAttributionService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditPersonAttributionController extends GeneralFormController{
	//private static final Logger logger = Logger.getLogger(EditPersonAttributionController.class);


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		PersonListAttributionBean personAttributionBean = (PersonListAttributionBean) command;
		Map<String, Object> newModel = new HashMap<String, Object>();

		String callerPage = request.getParameter("cp", "person.html");
		newModel.put("cp", callerPage);
		int callerPageObjectId = request.getIntParameter("cpoi", personAttributionBean.getPersonId());
		newModel.put("cpoi", callerPageObjectId);


		String action = request.getParameter("action", "");
		if (action != null && action.equals("cancel")){
			newModel.put("id", callerPageObjectId);
			return new ModelAndView( new RedirectView(callerPage), newModel);
		}

		PersonListAttributionBean connectedPersonAttributionBean=null;
		if (personAttributionBean.isConnectDetails()){
			Person person = personService.getPerson("person", personAttributionBean.getPersonId(),
					userPersonBean.getUsername());
			PersonListAttribution personAttribution = person.toPersonAttribution();
			connectedPersonAttributionBean = new PersonListAttributionBean(personAttribution);
			connectedPersonAttributionBean.setId(personAttributionBean.getId());
			connectedPersonAttributionBean.setPersonId(personAttributionBean.getPersonId());
			connectedPersonAttributionBean.setListId(personAttributionBean.getListId());
			connectedPersonAttributionBean.setPlaceInList(personAttributionBean.getPlaceInList());
			connectedPersonAttributionBean.setConnectDetails(personAttributionBean.isConnectDetails());
			connectedPersonAttributionBean.setTitle(personAttributionBean.getTitle());
			connectedPersonAttributionBean.setTitleId(personAttributionBean.getTitleId());
			personAttributionBean = connectedPersonAttributionBean;
		}

		if (personAttributionBean.getId()>0){
			personAttributionService.updatePersonAttribution(personAttributionBean.toPersonListAttribution());
		}
		else {
			personAttributionBean.setPlaceInList(10);
			int id = personAttributionService.insertPersonAttribution(personAttributionBean.toPersonListAttribution());
			personAttributionBean.setId(id);
		}
		newModel.put("id", personAttributionBean.getId());
		newModel.put("personId", personAttributionBean.getPersonId());
		return new ModelAndView(new RedirectView("personAttribution.html"), newModel);
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		List<AList> lists = listListService.getBasicLists();
		model.put("lists", lists);
		boolean listChosen = request.getIntParameter("id", 0) > 0 || request.getIntParameter("listId", 0) > 0;
		model.put("listChosen", listChosen);

		PersonListAttributionBean attribBean = (PersonListAttributionBean) model.get("command");
		List<AListInstruction> listInstructions = listInstructionListService.getListInstructions(attribBean.getListId());
		HashSet<String> columns = new HashSet<String>();
		for (AListInstruction listInstruction: listInstructions){
			String columnsString = listInstruction.getColumnsSelection();
			columnsString = columnsString.replaceAll("\\(", "");
			columnsString = columnsString.replaceAll("\\)", "");
			columnsString = columnsString.replaceAll(",", " ");
			StringTokenizer st = new StringTokenizer(columnsString);
			while (st.hasMoreTokens()) {
				String column = st.nextToken();
				column = column.substring(column.lastIndexOf(".") !=-1 ? column.lastIndexOf(".")+1 : 0 );
				columns.add(column);
			}
		}
		model.put("columns", columns);
		model.put("titles", mopDeskService.getTitles());
		return new ModelAndView ( "editPersonAttribution", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		PersonListAttributionBean personAttribution = new PersonListAttributionBean();

		int id = request.getIntParameter("id", 0);
		int listId = request.getIntParameter("listId", 0);
		int personId = request.getIntParameter("personId", 0);
		boolean importDetails = request.getBooleanParameter("importDetails", false);

		if (id > 0){
			personAttribution = new PersonListAttributionBean(personAttributionService.getPersonAttribution("personAttribution",
					id, userPersonBean.getUsername()));
			personAttribution.setList(listService.getList("list", personAttribution.getListId(), userPersonBean.getUsername()));
			personAttribution.setPerson(personService.getPerson("person", personAttribution.getPersonId(), userPersonBean.getUsername()));
		}
		else if (listId > 0 && personId > 0){
			personAttribution.setPersonId(personId);
			personAttribution.setPerson(personService.getPerson("person", personId, userPersonBean.getUsername()));
			personAttribution.setListId(listId);
			personAttribution.setList(listService.getList("list", listId, userPersonBean.getUsername()));
		}
		else if (personId > 0){
			personAttribution.setPersonId(personId);
			personAttribution.setPerson(personService.getPerson("person", personId, userPersonBean.getUsername()));
		}
		if (importDetails){
			personAttribution = new PersonListAttributionBean(personAttributionService.getPersonAttributionFromPerson("person", personId, userPersonBean.getUsername()));
		}
		return personAttribution;
	}

	private PersonAttributionService personAttributionService;

	public void setPersonAttributionService(
			PersonAttributionService personAttributionService) {
		this.personAttributionService = personAttributionService;
	}

	private ListListService listListService;


	public void setListListService(ListListService listListService) {
		this.listListService = listListService;
	}

	private ListService listService;


	public void setListService(ListService listService) {
		this.listService = listService;
	}

	private ListInstructionListService listInstructionListService;

	public void setListInstructionListService(
			ListInstructionListService listInstructionListService) {
		this.listInstructionListService = listInstructionListService;
	}

	private MopDeskService mopDeskService;


	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}







}
