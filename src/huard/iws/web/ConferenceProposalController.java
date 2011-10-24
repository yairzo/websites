package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ConferenceProposalBean;
import huard.iws.model.Faculty;
import huard.iws.model.Person;
import huard.iws.model.ConferenceProposal;
import huard.iws.service.ConferenceProposalService;
import huard.iws.service.FacultyService;
import huard.iws.service.MessageService;
import huard.iws.service.PersonListService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ConferenceProposalController extends GeneralFormController{

	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{

		ConferenceProposalBean conferenceProposalBean = (ConferenceProposalBean) command;

		conferenceProposalService.updateConferenceProposal(conferenceProposalBean.toConferenceProposal());
		
		model.put("id", conferenceProposalBean.getId())	;			
		return new ModelAndView(new RedirectView("editConferenceProposal.html"),model);
	}
	
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{

		// let's add the model a list of possible proposal approvers
		model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("proposalApproversListId")));
		//get faculty name by user facultyId
		Faculty faculty = facultyService.getFaculty(userPersonBean.getFacultyId());
		model.put("faculty", faculty.getNameHebrew());

		// if new proposal Create a new proposal and write it to db
		if (request.getParameter("action", "").equals("open")){
			ConferenceProposal conferenceProposal= new ConferenceProposal();
			conferenceProposal.setPersonId(userPersonBean.getId());
			int conferenceProposalId = conferenceProposalService.insertConferenceProposal(conferenceProposal);
			model.put("id",conferenceProposalId);
			return new ModelAndView ( new RedirectView("editConferenceProposal.html"), model);
		}
		else{//show edit
			return new ModelAndView ( this.getFormView(), model);
		}
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean();
		if ( ! isFormSubmission(request.getRequest())){
			String moveVer= request.getParameter("moveVer","");
			if(moveVer.equals("prev")){
				int id = request.getIntParameter("id", 0);
				int verId = request.getIntParameter("versionId",0);
				if (verId==0)
					verId= conferenceProposalService.getLastVersion(id);
				if (verId == conferenceProposalService.getFirstVersion(id)){
					String userMessage = messageService.getMessage("iw_IL.conferenceProposal.firstVersion");
					request.getSession().setAttribute("userMessage", userMessage);
					conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getPrevVersionConferenceProposal(id,verId+1));
				}
				else
					conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getPrevVersionConferenceProposal(id,verId));
			}
			else if(moveVer.equals("next")){
				int id = request.getIntParameter("id", 0);
				int verId = request.getIntParameter("versionId",0);
				if (verId==0)
					verId= conferenceProposalService.getLastVersion(id);
				if (verId == conferenceProposalService.getLastVersion(id)){
					String userMessage = messageService.getMessage("iw_IL.conferenceProposal.lastVersion");
					request.getSession().setAttribute("userMessage", userMessage);
					conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getNextVersionConferenceProposal(id,verId-1));
				}
				else
					conferenceProposalBean =new ConferenceProposalBean(conferenceProposalService.getNextVersionConferenceProposal(id,verId));
			}
			else{
				int id = request.getIntParameter("id", 0);
				if (id > 0)
					conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(id));
		
			}
		}
		return conferenceProposalBean;
	}

	
	private ConferenceProposalService conferenceProposalService;

	public void setConferenceProposalService(ConferenceProposalService conferenceProposalService) {
		this.conferenceProposalService = conferenceProposalService;
	}
	
	
	private PersonListService personListService;

	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
	
	
	private FacultyService facultyService;

	public void setFacultyService(FacultyService facultyService) {
		this.facultyService = facultyService;
	}
	
	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	
}
