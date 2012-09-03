package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonPrivilegeBean;
import huard.iws.bean.PrivilegeBean;
import huard.iws.model.PersonPrivilege;
import huard.iws.model.Privilege;
import huard.iws.service.PersonPrivilegeService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.MD5Encoder;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditPersonPrivilegeController extends GeneralFormController {

	//private static final Logger logger = Logger.getLogger(EditPostController.class);


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{

		PersonBean personBean = (PersonBean) command;

		Map<String, Object> newModel = new HashMap<String, Object>();
		String action = request.getParameter("action", "");

		if (action.equals("insert")){//add from allPrivilegesSelected values to personPrivilege table
			String password = request.getParameter("password","");
			String encodedPassword = "";
			if(!password.isEmpty()&& !password.equals("11111111"))
				encodedPassword=MD5Encoder.digest(password);
			String enabled = request.getParameter("enabled","");
			String vals = request.getParameter("vals", "");
			StringTokenizer valsTk = new StringTokenizer(vals,",");
			while (valsTk.hasMoreTokens() ){
				personPrivilegeService.insertPersonPrivilege(personBean.getId(),valsTk.nextToken(),encodedPassword,enabled);
			}
		}
		if (action.equals("delete")){//delete from personPrivilege table personPrivilegesSelected values
			String vals = request.getParameter("vals", "");
			StringTokenizer valsTk = new StringTokenizer(vals,",");
			while (valsTk.hasMoreTokens() ){
				try{
					int valint = new Integer(valsTk.nextToken()).intValue();
					personPrivilegeService.deletePersonPrivilege(valint);
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
		if (action.equals("save")){//save just password, enabled
			String password = request.getParameter("password","");
			String encodedPassword = "";
			if(!password.isEmpty()&& !password.equals("11111111"))
				encodedPassword=MD5Encoder.digest(password);
			String enabled = request.getParameter("enabled","");
			personPrivilegeService.updatePersonPrivilege(personBean.getId(),encodedPassword,enabled);
		}

		newModel.put("id", personBean.getId());
		return new ModelAndView( new RedirectView(getSuccessView()), newModel);
	}


	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{
		int id = request.getIntParameter("id", 0);
		if ( userPersonBean.getId() != id && ! userPersonBean.isAuthorized("ADMIN"))
			return new ModelAndView(new RedirectView("accessDenied.html"));

		PersonBean personBean = (PersonBean) model.get("command");
		
		List<PersonPrivilege> personPrivileges = personPrivilegeService.getPersonPrivileges(id);
		List<PersonPrivilegeBean> personPrivilegesBeans = new ArrayList<PersonPrivilegeBean>();
		List<String> privelegeStrArr = new ArrayList<String>();
		for (PersonPrivilege privilege: personPrivileges){
			privelegeStrArr.add(privilege.getPrivilege());//array of just privilege names
			PersonPrivilegeBean privilegeBean = new PersonPrivilegeBean(privilege);
			personPrivilegesBeans.add(privilegeBean);
		}
		model.put("personPrivileges", personPrivilegesBeans);
		List<Privilege> allPrivileges = personPrivilegeService.getAllPrivileges();
		List<PrivilegeBean> allPrivilegesBeans = new ArrayList<PrivilegeBean>();
		for (Privilege privilege: allPrivileges){
			if(!privelegeStrArr.contains(privilege.getPrivilege())){
				PrivilegeBean privilegeBean = new PrivilegeBean(privilege);
				allPrivilegesBeans.add(privilegeBean);
			}
		}
		model.put("allPrivileges", allPrivilegesBeans);
		
		LanguageUtils.applyLanguage(model, request, response, userPersonBean.getPreferedLocaleId());
		
		model.put("personName", personBean.getDegreeFullNameHebrew());
		
		if(personPrivilegeService.getPrivilegePassword(personBean.getId()).isEmpty())
			model.put("password", "");
		else
			model.put("password", "11111111");
		
		model.put("enabled",personPrivilegeService.getPrivilegeEnabled(personBean.getId()));
		
		return new ModelAndView("editPersonPrivilege", model);
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

}
