package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.Person;
import huard.iws.service.PersonListService;
import huard.iws.util.RequestWrapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class PersonListHelperController extends GeneralController{
	
	@Override
	protected ModelAndView handleRequest(RequestWrapper request,
			HttpServletResponse response, Map<String, Object> model,
			PersonBean personBean) throws Exception {
		String term = request.getParameter("term", "");
		String role = request.getParameter("role", "");
		StringBuilder sb = new StringBuilder();
		List<Person> persons = personListService.getFilteredPersons(new String [] { "lastNameHebrew", "firstNameHebrew"},
				term, role);
		boolean first = true;
		sb.append("[");		
		for (Person person: persons){
			
			if (!first)
				sb.append(",");
			first = false;
			PersonBean aPersonBean = new PersonBean(person);
			String listItem = "{\"label\":\"" + aPersonBean.getFullNameHebrew() + "\",\"id\":"+aPersonBean.getId()+"}";
			sb.append(listItem);
		}
		
		sb.append("]");
		model.put("json", sb.toString());
		
		return new ModelAndView("personListHelper", model);
	}
	
	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

}
