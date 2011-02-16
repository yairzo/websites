package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonListAttributionBean;
import huard.iws.model.AList;
import huard.iws.model.PersonListAttribution;
import huard.iws.service.ListService;
import huard.iws.service.PersonAttributionListService;
import huard.iws.service.PersonAttributionService;
import huard.iws.service.PersonService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;



public class EditListOrderController extends GeneralFormController {

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		EditListOrderControllerCommand editListOrderControllerCommand = (EditListOrderControllerCommand) command;
		for (PersonListAttributionBean attribBean :  editListOrderControllerCommand.getAttribs()){
			attribBean.setPlaceInList(attribBean.getPlaceInList() * -1);
			personAttributionService.updatePersonAttributionPlaceInList(attribBean.toPersonListAttribution());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", editListOrderControllerCommand.getId());
		return new ModelAndView(new RedirectView(getSuccessView()),map);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		List<PersonListAttributionBean> attribBeans = ((EditListOrderControllerCommand) model.get("command")).getAttribs();
		model.put("attribBeans", attribBeans);
		return new ModelAndView( "editListOrder", model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		List<PersonListAttributionBean> attribBeans = new ArrayList<PersonListAttributionBean>();
		List<PersonListAttribution> attribs;


		int id = request.getIntParameter("id", 0);
		if (id > 0){
			attribs = personAttributionListService.getPersonAttributions(id);
			for (PersonListAttribution attrib: attribs){
				PersonListAttributionBean attribBean = new PersonListAttributionBean(attrib);
				attribBean.setList(listService.getList("list", attribBean.getListId(), userPersonBean.getUsername()));
				attribBean.setPerson(personService.getPerson("person", attribBean.getPersonId(),
						userPersonBean.getUsername()));
				attribBean.setPlaceInList(attribBean.getPlaceInList() * -1);
				attribBeans.add(attribBean);
			}
		}
		EditListOrderControllerCommand command = new EditListOrderControllerCommand();
		command.setId(id);
		command.setAttribs(attribBeans);
		command.setList(listService.getList("list", id, userPersonBean.getUsername()));
		return command;
	}

	private PersonAttributionListService personAttributionListService;
	public void setPersonAttributionListService(
			PersonAttributionListService personAttributionListService) {
		this.personAttributionListService = personAttributionListService;
	}

	private ListService listService;
	public void setListService(ListService listService) {
		this.listService = listService;
	}

	private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private PersonAttributionService personAttributionService;

	public void setPersonAttributionService(
			PersonAttributionService personAttributionService) {
		this.personAttributionService = personAttributionService;
	}

	public class EditListOrderControllerCommand{
		int id;
		List<PersonListAttributionBean> attribs;
		AList list;

		public AList getList() {
			return list;
		}

		public void setList(AList list) {
			this.list = list;
		}

		public List<PersonListAttributionBean> getAttribs() {
			return attribs;
		}

		public void setAttribs(List<PersonListAttributionBean> attribs) {
			this.attribs = attribs;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}
}
