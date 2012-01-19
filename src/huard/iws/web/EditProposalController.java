package huard.iws.web;

import huard.iws.bean.PartnerBean;
import huard.iws.bean.PartnerProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.PersonProposalBean;
import huard.iws.bean.ProposalBean;
import huard.iws.constant.Constants;
import huard.iws.constant.PersonProposalRequiredActions;
import huard.iws.constant.PersonProposalTypes;
import huard.iws.model.Fund;
import huard.iws.model.Institute;
import huard.iws.model.Partner;
import huard.iws.model.PartnerInstitute;
import huard.iws.model.PartnerProposal;
import huard.iws.model.Person;
import huard.iws.model.PersonProposal;
import huard.iws.model.Proposal;
import huard.iws.service.FundService;
import huard.iws.service.InstituteListService;
import huard.iws.service.MailMessageService;
import huard.iws.service.MopDeskService;
import huard.iws.service.PartnerListService;
import huard.iws.service.PartnerProposalService;
import huard.iws.service.PartnerService;
import huard.iws.service.PersonListService;
import huard.iws.service.PersonProposalMessageService;
import huard.iws.service.PersonProposalService;
import huard.iws.service.PersonProposalStateService;
import huard.iws.service.ProposalListService;
import huard.iws.service.ProposalService;
import huard.iws.service.ProposalStateHistoryService;
import huard.iws.service.ProposalStateService;
import huard.iws.service.UniverseService;
import huard.iws.service.PersonProposalStateServiceImpl.PersonProposalStateDetails;
import huard.iws.service.ProposalStateServiceImpl.ProposalStateDetails;
import huard.iws.util.BaseUtils;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class EditProposalController extends GeneralFormController {
	private static final Logger logger = Logger.getLogger(EditProposalController.class);
	final boolean INFORMED = true;

	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{

		EditProposalControllerCommand aCommand = (EditProposalControllerCommand) command;

		Map<String,Object> newModel = new HashMap<String,Object>();
		newModel.put("id", aCommand.getProposalBean().getId());

		ProposalBean proposalBean = aCommand.getProposalBean();

		proposalBean.init();

		Proposal preupdateProposal = proposalService.getProposal("proposal", proposalBean.getId(), userPersonBean.getUsername());

		String action = request.getParameter("action", "");

		if (action.equals("cancel")){
			return new ModelAndView(new RedirectView("proposals.html"));
		}
		else if (action.contains("Attach")){
			int attachId = request.getIntParameter("attachId", 0);
			if (attachId > 0){
				if (action.equals("deleteAttach")){
					proposalService.deleteProposalAttachment(attachId, proposalBean.getId());
				}
				else if (action.equals("moveUpAttach")){
					proposalService.moveProposalAttachmentUp(attachId, proposalBean.getId());
				}
				else if (action.equals("moveDownAttach")){
					proposalService.moveProposalAttachmentDown(attachId, proposalBean.getId());
				}
			}
		}

		else if (action.equals("addResearcher")){
			Person addedResearcherPerson = personListService.getResearchersMap().get(aCommand.getAddedResearcher());
			PersonBean addedResearcherPersonBean = new PersonBean(addedResearcherPerson);
			boolean informedOfProposal;

			if (informedOfProposal = addedResearcherPersonBean.isValidEmail()) {
				mailMessageService.createSimpleProposalMail(addedResearcherPersonBean, userPersonBean, proposalBean, "addedResearcher");
			}

			//userMessageService.putUserMessageInSession(request,
			//		userMessageService.getAddedResearcherMessage(addedResearcherPersonBean));


			personProposalService.insertPersonProposal(addedResearcherPerson.getId(), proposalBean,
					PersonProposalTypes.getTypeId("RESEARCHER"), informedOfProposal);
			proposalStateHistoryService.insertProposalState("DRAFT", "ADD_RESEARCHER", aCommand.getProposalBean(), userPersonBean, "Main Researcher");
			return new ModelAndView(new RedirectView("proposal.html"), newModel);
		}
		else if (action.equals("removeResearcher")){
			Person removedResearcherPerson = personService.getPerson("person", aCommand.getRemovedResearcherId(), userPersonBean.getUsername());
			PersonBean removedResearcherPersonBean = new PersonBean(removedResearcherPerson);
			if (removedResearcherPersonBean.isValidEmail()) {
				mailMessageService.createSimpleProposalMail(removedResearcherPersonBean, userPersonBean, proposalBean, "removedResearcher");
			}
			//userMessageService.putUserMessageInSession(request,
			//		userMessageService.getRemovedResearcherMessage(removedResearcherPersonBean));

			personProposalService.deletePersonProposal(removedResearcherPersonBean.getId(), aCommand.getProposalBean().getId());
			proposalStateHistoryService.insertProposalState("DRAFT", "REMOVE_RESEARCHER", proposalBean, userPersonBean, "Main Researcher");
			return new ModelAndView(new RedirectView("proposal.html"), newModel);
		}
		else if (action.equals("addPartner")){

			PartnerBean partnerBean = new PartnerBean (aCommand.getPartner());
			Partner existingPartner =  partnerListService.getPartnerByName(partnerBean.getName());
			Institute institute = aCommand.getInstitute();

			institute = instituteListService.getInstituteByName(institute.getName());

			if (existingPartner == null) {
				partnerBean.setInstituteId(institute.getId());
				partnerBean.setId(partnerService.insertPartner(partnerBean.toPartner()));
				mailMessageService.informAdminOnPartnerAdd(proposalBean, partnerBean);
			}
			else if (existingPartner.getInstituteId() != institute.getId()){
				partnerBean.setInstituteId(institute.getId());
				partnerBean.setId(partnerService.insertPartner(partnerBean.toPartner()));
				partnerBean.setAdditionalIntituteBean(new PartnerBean(existingPartner).getInstituteBean());
				mailMessageService.informAdminOnPartnerDuality(proposalBean, partnerBean);
			}
			else {
				partnerBean = new PartnerBean(existingPartner);
			}
			PartnerProposal partnerProposal = new PartnerProposal( partnerBean.getId(), proposalBean.getId());
			partnerProposalService.insertPartnerProposal(partnerProposal);
			proposalStateHistoryService.insertProposalState("DRAFT", "ADD_PARTNER", proposalBean, userPersonBean, "Researcher");

		}
		else if (action.equals("removePartner")){
			PartnerProposal partnerProposal = new PartnerProposal(aCommand.getRemovedPartnerId(), proposalBean.getId());
			partnerProposalService.deletePartnerProposal(partnerProposal);
			proposalStateHistoryService.insertProposalState("DRAFT", "REMOVE_PARTNER", proposalBean, userPersonBean, "Researcher");
		}

		else if (action.indexOf("sendYissum")!=-1){
			handleYissum(proposalBean, preupdateProposal, userPersonBean);
		}


		else if (action.indexOf("yissumApproval")!=-1){
			handleYissumApproval(proposalBean, preupdateProposal, userPersonBean);
		}

		else if (action.indexOf("save")!=-1){

			if (action.equals("autosave")){
				proposalBean.setResearcherApproved(false);
			}

			// this part saves the content type of the attachments


			if (request.getRequest().getContentType().indexOf("multipart")!=-1){
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
				Iterator fileNames = multipartRequest.getFileNames();
				while (fileNames.hasNext()) {
					String filename = (String) fileNames.next();
					MultipartFile file = multipartRequest.getFile(filename);

					if (filename.contains("proposalBean.addedResearchAttachment")){
						proposalBean.getAddedResearchAttachment().setContentType(file.getContentType());
					}
					else if (filename.equals("proposalBean.ethicsAttach")){
						proposalBean.setEthicsAttachContentType(file.getContentType());
					}
					else if (filename.equals("proposalBean.safetyAttach")){
						proposalBean.setSafetyAttachContentType(file.getContentType());
					}
					else if (filename.equals("proposalBean.humanAttach")){
						proposalBean.setHumanAttachContentType(file.getContentType());
					}
					else if (filename.equals("proposalBean.animalsAttach")){
						proposalBean.setAnimalsAttachContentType(file.getContentType());
					}
				}
			}
			handleAttachmentsAndExperimentApprovals(proposalBean, preupdateProposal);

			if (BaseUtils.hasValue(proposalBean.getAddedResearchAttachment().getFile())  && BaseUtils.hasValue(proposalBean.getAddedResearchAttachment().getTitle())){
				proposalBean.getAddedResearchAttachment().setPlace(proposalBean.getResearchAttachments().size()+1);
				proposalBean.getResearchAttachments().add(proposalBean.getAddedResearchAttachment());
			}

			if (aCommand.getAddedFund() !=null && aCommand.getAddedFund().length() > 0){
				Fund fund = fundService.getFundsNamesMap().get(aCommand.getAddedFund());
				if (fund != null){
					proposalBean.setFundId(fund.getId());
				}
				// If the fund is not recognized we'll create a new temporary fund, unless it's an auto save
				// were the user may have been in the middle of typing
				else {
					fund = new Fund();
					fund.setName(aCommand.getAddedFund());
					fund.setTemporary(true);
					mailMessageService.informAdminsOnUnknownFund(proposalBean, fund);
					int fundId = fundService.insertFund(fund);
					proposalBean.setFundId(fundId);
					userMessageService.putUserMessageInSession(request,
							userMessageService.getResearcherUnknownFundMessage());
				}
			}


			// handle researchers approvals
			// personProposal will first be updated in memory, than each one in his turn will be written to db;

			List<PersonProposalBean> personProposalBeans = proposalBean.getPersonProposalBeans();
			boolean approvedByAll = true;
			boolean mainApproved = false;
			if (proposalBean.getStateId() < proposalStateService.getProposalStateId("APPROVED_BY_ALL")){

				for (PersonProposalBean personProposalBean: personProposalBeans){
					if (proposalBean.getStateId() <= proposalStateService.getProposalStateId("APPROVED_BY_MAIN")){


						if (personProposalBean.getTypeId() == PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){

							if (proposalBean.isResearcherApproved() && userPersonBean.getId() == personProposalBean.getPersonId() &&
								proposalBean.getStateId() < proposalStateService.getProposalStateId("APPROVED_BY_MAIN")){
								personProposalBean.setApproval(true);
								ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("APPROVED_BY_MAIN");
								proposalBean.setStateId(proposalStateDetails.getStateId());
								approvedByAll = true;
								mainApproved = true;
								proposalStateHistoryService.insertProposalState("APPROVED_BY_MAIN", "APPROVED",proposalBean, userPersonBean, "Main_Researcher");


								// if there are secondary researchers
								if (personProposalBeans.size() > 1){
									personProposalMessageService.sendApprovalRequestToAllSecondaryResearchers(proposalBean.getId());
									userMessageService.putUserMessageInSession(request,
											userMessageService.getSecondaryResearchersApprovalRequestMessage());
									personProposalBean.setRequiredActionsIds(PersonProposalRequiredActions.getRequiredActionsIds("WAIT_RESEARCHER_APPROVAL"));
								}
								else{
									personProposalBean.setRequiredActionsIds(PersonProposalRequiredActions.getRequiredActionsIds("WAIT_FUND_RESPONSE"));
								}
								personProposalBean.setStateId(personProposalStateService.getPersonProposalStateId("APPROVED"));
								personProposalService.updateSecondaryResearchersPersonProposals(proposalBean,
										personProposalStateService.getPersonProposalStateId("APPROVAL_REQUEST"),
										PersonProposalRequiredActions.getRequiredActionsIds("APPROVE"));
							}
							// main researcher but not an approval and yet to approve

							else if (! personProposalBean.isApproval()){
								approvedByAll = false;
								mainApproved = false;
								personProposalBean.setRequiredActionsIds(PersonProposalRequiredActions.getRequiredActionsIds(
										"UPDATE_BASIC_DETAILS,HANDLE_SECONDARY_RESEARCHERS," +
								"HANDLE_PARTNERS" + (proposalBean.isReadyForApproval() ? ",APPROVE" : "")));
							}
						}
						// for a secondary researcher
						else{
							// this personProposal approval
						if (proposalBean.isResearcherApproved() && userPersonBean.getId() == personProposalBean.getPersonId()){
								personProposalBean.setApproval(true);
								if (! proposalBean.isLastToApprove()){
									approvedByAll = false;
									proposalStateHistoryService.insertProposalState("APPROVED_BY_MAIN", "APPROVED", proposalBean, userPersonBean, "RESEARCHER");
									personProposalBean.setStateId(personProposalStateService.getPersonProposalStateId("APPROVED"));
									personProposalBean.setRequiredActionsIds(
											PersonProposalRequiredActions.getRequiredActionsIds("WAIT_RESEARCHER_APPROVAL"));
								}
								// last to approve, we'll do something in the next if block of 'APPROVED_BY_ALL'
								// so here we won't do anything
								else{
									approvedByAll = true;
								}
							}
							//not this personProposal approval
							else if (!mainApproved){
								personProposalBean.setRequiredActionsIds(PersonProposalRequiredActions.getRequiredActionsIds(
										"UPDATE_BASIC_DETAILS,HANDLE_SECONDARY_RESEARCHERS," +
								"HANDLE_PARTNERS" + (proposalBean.isReadyForApproval() ? ",APPROVE" : "") ));
							}
							// all cases of the secondary researchers here
							//the proposal is yet to be approved by all
							else{
								approvedByAll = false;
							}
						}
					}
					personProposalService.updatePersonProposal(personProposalBean.toPersonProposal());
				}
			}
			if (approvedByAll && proposalBean.getStateId() < proposalStateService.getProposalStateId("APPROVED_BY_ALL")){
				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("APPROVED_BY_ALL");
				proposalBean.setStateId(proposalStateDetails.getStateId());
				proposalStateHistoryService.insertProposalState("APPROVED_BY_ALL", "APPROVED", proposalBean, userPersonBean, "");
				mailMessageService.informMopOnNewProposal(proposalBean);

				personProposalService.updateResearchersPersonProposals(proposalBean.getId(),
						personProposalStateService.getPersonProposalStateId("APPROVED"),
						PersonProposalRequiredActions.getRequiredActionsIds("WAIT_FUND_RESPONSE"));
//				add the mop person as a personProposal
				// for now we trust there is only one coordinator helper in a desk
				List<PersonBean> personBeans = mopDeskService.getPersonsList(proposalBean.getFund().getDeskId(),
						Constants.getMopTitleId("COORDINATOR_HELPER"));
				if (personBeans.size() != 1) {
					logger.error("Number of coordinator helpers differs from 1");
					throw new Exception();
				}
				for (PersonBean personBean: personBeans)
					personProposalService.insertPersonProposal(personBean.getId(),
						proposalBean, PersonProposalTypes.getTypeId("MOP_DESK"), INFORMED);
			}

			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("APPROVED_BY_ALL")
					&& proposalBean.getPersonProposalBean().getTypeId() == PersonProposalTypes.getTypeId("MOP_DESK")){

				boolean fundApproved;
				String  stateName = (fundApproved = proposalBean.getFundingAgencyApproved() ==1) ?
						"FUND_APPROVED" : "FUND_REFUSED";
				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails(stateName);
				proposalBean.setStateId(proposalStateDetails.getStateId());
				proposalStateHistoryService.insertProposalState(stateName, "FUND_RESPONSE", proposalBean, userPersonBean, "MOP");
				if (fundApproved){
					proposalBean.setYearId(proposalListService.getNextYearId());
					personProposalService.updateResearchersPersonProposals(proposalBean.getId(), PersonProposalRequiredActions.getRequiredActionsIds("ADD_EXPERIMENT_APPROVALS"));

				}
				else{
					personProposalService.updateResearchersPersonProposals(proposalBean.getId(), PersonProposalRequiredActions.getRequiredActionsIds("NOTHING"));
				}
				personProposalService.updatePersonProposal(proposalBean.getId(),
						PersonProposalTypes.getTypeId("MOP_DESK"),
						PersonProposalRequiredActions.getRequiredActionsIds("NOTHING"));

				personProposalMessageService.informAllResearchersOnFundResponse(proposalBean.getId(), fundApproved);


			}

			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("FUND_APPROVED")
					&& (! proposalBean.isExperimental() || proposalBean.isExperimentApproved())){
				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("EXPERIMENT_APPROVED");
				proposalBean.setStateId(proposalStateDetails.getStateId());
				proposalStateHistoryService.insertProposalState("EXPERIMENT_APPROVED", "EXPERIMENT_APPROVAL", proposalBean, userPersonBean, "RESEARCHER");
				personProposalService.updateSecondaryResearchersPersonProposals(proposalBean.getId(), PersonProposalRequiredActions.getRequiredActionsIds("WAIT_DEAN_RESPONSE"));
				personProposalService.updateMainResearcherPersonProposal(proposalBean.getId(), PersonProposalRequiredActions.getRequiredActionsIds("SEND_DEAN"));
			}

			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("EXPERIMENT_APPROVED")
					&& proposalBean.getDeanId() > 0){
				PersonBean dean =
					personListService.getPersonsListPerson(
							configurationService.getConfigurationInt("proposalApproversListId")
							, proposalBean.getDeanId());
				mailMessageService.createSimpleProposalMail(dean, userPersonBean, proposalBean, "deanApprovalRequest");
				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("DEAN_WAIT");
				proposalBean.setStateId(proposalStateDetails.getStateId());
				proposalStateHistoryService.insertProposalState("DEAN_WAIT", "DEAN_SEND", proposalBean, userPersonBean, "RESEARCHER");
				// all are waiting now for the dean response, including the mop person
				personProposalService.updatePersonProposals(proposalBean.getId(), PersonProposalRequiredActions.getRequiredActionsIds("WAIT_DEAN_RESPONSE"));
				// let's add the dean
				personProposalService.insertPersonProposal(dean.getId(), proposalBean,
						PersonProposalTypes.getTypeId("DEAN"), INFORMED);
			}

			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("DEAN_WAIT")
					&& userPersonBean.getId() == proposalBean.getDean().getId()){
				String stateName = proposalBean.isDeanApproved() ? "DEAN_APPROVED" : "DEAN_REFUSED";
				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails(stateName);

				personProposalMessageService.informAllResearchersOnDeanResponse(userPersonBean,
						proposalBean.getId(), proposalBean.isDeanApproved());
				personProposalMessageService.informMopDeskOnDeanResponse(userPersonBean,
						proposalBean.getId(), proposalBean.isDeanApproved());

				proposalBean.setStateId(proposalStateDetails.getStateId());
				proposalStateHistoryService.insertProposalState(stateName, "DEAN_RESPONSE", proposalBean, userPersonBean, "DEAN");
				personProposalService.updateResearchersPersonProposals(proposalBean.getId(),
						PersonProposalRequiredActions.getRequiredActionsIds("WAIT_MOP_OPEN_BUDGET"));
				personProposalService.updatePersonProposal(proposalBean.getId(),
						PersonProposalTypes.getTypeId("MOP_DESK"),
						PersonProposalRequiredActions.getRequiredActionsIds("PASS_BUDGET_OFFICER"));
				personProposalService.updatePersonProposal(proposalBean.getId(),
						PersonProposalTypes.getTypeId("DEAN"),
						personProposalStateService.getPersonProposalStateId("APPROVED"),
						PersonProposalRequiredActions.getRequiredActionsIds("NOTHING"));
			}
			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("DEAN_APPROVED")
					&& proposalBean.getPersonProposalBean().getTypeId() == PersonProposalTypes.getTypeId("MOP_DESK")){
				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("PASSED_MOP_BUDGET_OFFICER");
				proposalBean.setStateId(proposalStateDetails.getStateId());

				proposalStateHistoryService.insertProposalState(proposalStateDetails.getStateName()
						, "PASSED_MOP_BUDGET_OFFICER", proposalBean, userPersonBean, "MOP_DESK");
				PersonBean budgetOfficer = new PersonBean (personService.getPerson(proposalBean.getBudgetOfficerId()));
				mailMessageService.createSimpleProposalMail(budgetOfficer, userPersonBean, proposalBean, "budgetOfficerOpenBudgetRequest");

				// Add the budget officer to the proposal
				personProposalService.updatePersonProposal(proposalBean.getId(), PersonProposalTypes.getTypeId("MOP_DESK"),
						PersonProposalRequiredActions.getRequiredActionsIds("WAIT_MOP_OPEN_BUDGET"));

				personProposalService.insertPersonProposal(budgetOfficer.getId(), proposalBean,
						PersonProposalTypes.getTypeId("BUDGET_OFFICER"), INFORMED);

			}
			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("PASSED_MOP_BUDGET_OFFICER")
					&& proposalBean.getPersonProposalBean().getTypeId() == PersonProposalTypes.getTypeId("BUDGET_OFFICER")
					&& proposalBean.isBudgetApproved()){

				ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("MOP_BUDGET_OPENED");
				proposalBean.setStateId(proposalStateDetails.getStateId());

				proposalStateHistoryService.insertProposalState(proposalStateDetails.getStateName()
						, "OPEN_BUDGET", proposalBean, userPersonBean, "BUDGET_OFFICER");


				// TODO: here the person should be taken from the desk that includes the archive
				// and not from the desk that handles the funding agency
				List<PersonBean> archivers = personListService.getPersonsList(configurationService.getConfigurationInt("archiversListId"));
				// we arbitarry chose one of the archivers.
				Long archiverIndex = Math.round(Math.random() * (archivers.size() - 1));
				PersonBean archiver = archivers.get(archiverIndex.intValue());
				mailMessageService.createSimpleProposalMail(archiver, userPersonBean, proposalBean, "informArchiverOnProposal");

				personProposalMessageService.informAllResearchersOnBudgetOpened(proposalBean.getId());
				personProposalService.updatePersonProposals(proposalBean.getId(),
						PersonProposalRequiredActions.getRequiredActionsIds("NOTHING"));
				personProposalService.insertPersonProposal(archiver.getId(), proposalBean,
						PersonProposalTypes.getTypeId("ARCHIVER"), INFORMED);
			}

			else if (proposalBean.getStateId() == proposalStateService.getProposalStateId("MOP_BUDGET_OPENED")
					&& proposalBean.getPersonProposalBean().getTypeId() == PersonProposalTypes.getTypeId("ARCHIVER")
					&& proposalBean.isArchived()){
				personProposalService.updatePersonProposals(proposalBean.getId(),
						PersonProposalRequiredActions.getRequiredActionsIds("NOTHING"));
				proposalStateHistoryService.insertProposalState("MOP_BUDGET_OPENED"
						, "ARCHIVE", proposalBean, userPersonBean, "ARCHIVER");
			}

			proposalService.updateProposal(proposalBean.toProposal());
			return new ModelAndView(new RedirectView("proposal.html"), newModel);
		}

		return new ModelAndView(new RedirectView("proposal.html"), newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		String action = request.getParameter("action", "");

		model.put("aNewProposal", action.equals("open"));

		if ( model.get("userMessage") == null){
			String aUserMessage = null;

			if (action.equals("opencopy")){
				aUserMessage = userMessageService.getCopyProposalMessage();
			}
			if (aUserMessage ==null){
				aUserMessage = userMessageService.getSessionUserMessage(request.getRequest());
			}
			model.put("userMessage", aUserMessage);
		}

		int proposalId  = request.getIntParameter("id", 0);

		EditProposalControllerCommand command = (EditProposalControllerCommand) model.get("command");

		ProposalBean proposalBean = command.getProposalBean();

		// in case it's an exisitng proposal was asked, check authorization

		if (proposalId > 0) {
			boolean authorized = (	userPersonBean.isAuthorized("EQF", "ADMIN")
													|| userPersonBean.isAuthorized("EQF", "MOP")
													|| userPersonBean.isAuthorized("EQF", "YISSUM")
													|| userPersonBean.isAuthorized("EQF", "DEAN")
													|| userPersonBean.isAuthorized("EQF", "RESEARCHER"))
												&& command.getProposalBean().getPersonProposalBean() !=null;
			if (! authorized ) {
				return new ModelAndView(new RedirectView("accessDenied.html"));
			}
		}
		else if (! (Boolean)model.get("validationErrors") &&  ! action.startsWith("open")){
			return new ModelAndView(new RedirectView("accessDenied.html"));
		}

		// let's put in the model a list of all researchers

		LinkedHashMap<String,Person> researchers = personListService.getResearchersMap();

		for (PersonProposalBean personProposalBean: proposalBean.getPersonProposalBeans()){
			Person person = personService.getPerson("persons", personProposalBean.getPersonId(), userPersonBean.getUsername());
			PersonBean personBean = new PersonBean(person);
			researchers.remove(personBean.getFullNameHebrew());
		}

		model.put("researchers", researchers);

		//let's put in the model a list of all funds
		List <Fund>	 funds = fundService.getFunds();
		model.put("funds", funds);

		LinkedHashMap <String,Fund> fundsNamesMap = fundService.getFundsNamesMap();
		model.put("fundsNamesMap", fundsNamesMap);


		// a researcher was just added let's put his details in the model

		int addedResearcherId = request.getIntParameter("addedResearcherId", 0);
		if (addedResearcherId > 0){
			Person person = personService.getPerson("person", addedResearcherId, userPersonBean.getUsername());
			PersonBean personBean = new PersonBean(person);
			model.put("addedResearcher", personBean);
		}

		//let's add the model the possible proposal states

		Map<String, Integer> statesMap = proposalStateService.getProposalStates();
		for (String state: statesMap.keySet()){
			model.put(state, statesMap.get(state));
		}

		String [] proposalStatesDisplay = proposalStateService.getProposalStatesDisplay();

		model.put("proposalStates", proposalStatesDisplay);

		//let's add the model the possible personProposal states

		String [] personProposalStatesDisplay = new String [personProposalStateService.getPersonProposalStates().size()];

		for (String stateName: personProposalStateService.getPersonProposalStates().keySet()){
			PersonProposalStateDetails personProposalStateDetails = personProposalStateService.getPersonProposalStateDetails(stateName);
			model.put(stateName, personProposalStateDetails.getStateId());
			personProposalStatesDisplay [ personProposalStateDetails.getStateId() ] =
					personProposalStateDetails.getStateDisplay();
		}

		model.put("personProposalStates", personProposalStatesDisplay);

		// let's add the model a list of possible proposal approvers

		model.put("deans", personListService.getPersonsList(configurationService.getConfigurationInt("proposalApproversListId")));


		// let's add the model a list of continents

		model.put("continents", universeService.getContinents());

		int countryId = request.getIntParameter("countryId", 0);
		if (countryId > 0){
			List <Institute> institutes = instituteListService.getInstitutes(countryId);
			model.put("institutes", institutes);
		}


		// let's put a list of all know partners so the user can select one

		LinkedHashSet<String> partners = partnerListService.getPartnersNames();
		model.put("partners", partners);



		Map<String, Integer> personProposalTypes = PersonProposalTypes.getPersonProposalTypes();
		for (String type: personProposalTypes.keySet()){
			model.put(type, personProposalTypes.get(type));
		}

		if (proposalBean.getStateId() >= proposalStateService.getProposalStateId("DEAN_APPROVED")
				&& proposalBean.getFundId() > 0){
			Fund fund = fundService.getFund(proposalBean.getFundId());
			model.put("budgetOfficers", mopDeskService.getPersonsList(fund.getDeskId(),
					Constants.getMopTitleId("BUDGET_OFFICER")));
		}

		boolean approvedByCurrentUser = false;
		for (PersonProposalBean personProposalBean: proposalBean.getPersonProposalBeans()){
			if (personProposalBean.getPersonId() == userPersonBean.getId() && personProposalBean.isApproval()){
				approvedByCurrentUser = true;
			}
		}
		model.put("approvedByCurrentUser", approvedByCurrentUser);

		return new ModelAndView(this.getFormView(), model);
	}


	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		EditProposalControllerCommand command = new EditProposalControllerCommand();
		ProposalBean proposalBean = null;

		// we treat different a  submission and a viewing of a proposal
		// if it's not a form submission

		if (! isFormSubmission(request.getRequest())){
			String action = request.getParameter("action", "");
			if (action.equals("open")){

				// in the case of a new proposal, we collect some initail details

				// TODO: check if allowed to open a proposal

				// Create a new proposal and write it to db
				proposalBean = new ProposalBean();
				proposalBean.setId( proposalService.insertProposal());
				initProposal(userPersonBean, proposalBean);
			}
			else if (action.equals("opencopy")){
				int originalProposalId = request.getIntParameter("originalProposalId", 0);
				if (originalProposalId > 0){
					//Copy the basic details
					Proposal originalProposal = proposalService.getProposal(originalProposalId);
					Proposal proposal = originalProposal.copyProposalBasicDetails();
					proposal.setId( proposalService.insertProposal());
					proposalBean = new ProposalBean(proposal);
					initProposal(userPersonBean, proposalBean);
				}
			}
			// in case of an existing proposal
			else {
				int id = request.getIntParameter("id", 0);
				if (id > 0){
					// try to load proposal details from the db
					Proposal proposal = proposalService.getProposal("proposal", id, userPersonBean.getUsername());
					// if such a proposal does not exist, create a new one, anyone no reseacher owns it
					if (proposal == null ) proposal = new Proposal();
					proposal.setId(id);

					// Building the ProposalBean object
					proposalBean = new ProposalBean(proposal);

					// Update the personProposal and add it to proposalBean

					PersonProposal personProposal;

					if (userPersonBean.isAuthorized("EQF", "ADMIN")){
						personProposal = new PersonProposal();
						personProposal.setPersonId(userPersonBean.getId());
						personProposal.setProposalId(proposalBean.getId());
					}
					else if ((personProposal = personProposalService.getPersonProposal(userPersonBean.getId(),
							proposalBean.getId())) != null ){

						int personProposalStateId = personProposal.getStateId();
						String personProposalState = personProposalStateId <
						personProposalStateService.getPersonProposalStateId("APPROVED") ?
								"WATCHED" : "APPROVED";

						personProposal = personProposalService.updatePersonProposal(userPersonBean.getId(),
								proposalBean.getId(),
								personProposalStateService.getPersonProposalStateId(personProposalState));
					}
					proposalBean.setPersonProposalBean(new PersonProposalBean(personProposal));
				}

				// in case no id was supplied

				else{
					proposalBean = new ProposalBean();
				}
			}
		}

		//in case of a form submission
		else{
			proposalBean = new ProposalBean();
			int id = request.getIntParameter("proposalBean.id", 0);
			proposalBean.setId(id);
		}

		// in all cases, both submission and viewing we need to load proposers details

		List<PersonProposal> personProposals = personProposalService.getPersonProposals(proposalBean.getId());
		List<PersonProposalBean> personProposalBeans = new ArrayList<PersonProposalBean>();
		for (PersonProposal personProposal: personProposals){
			PersonProposalBean personProposalBean = new PersonProposalBean( personProposal);
			personProposalBeans.add(personProposalBean);

			// Put the current user as the ProposalBean's PersonProposalBean


			if (personProposal.getPersonId() == userPersonBean.getId()){
				PersonProposalBean aPersonProposalBean = new PersonProposalBean(personProposal);
				proposalBean.setPersonProposalBean(aPersonProposalBean);
			}
		}
		proposalBean.setPersonProposalBeans(personProposalBeans);

//		if the user is not a proposer, we still have to put him as the personProposalBean
		if (proposalBean.getPersonProposalBean() == null )
			proposalBean.setPersonProposalBean(new PersonProposalBean(userPersonBean.getId(), proposalBean.getId()));

//		let's put a list of all existing partners to show them

		List <PartnerProposal> partnerProposals = partnerProposalService.getPartnerProposals(proposalBean.getId()) ;
		List <PartnerProposalBean> partnerProposalBeans = new ArrayList<PartnerProposalBean>();
		for (PartnerProposal partnerProposal: partnerProposals){
			PartnerProposalBean partnerProposalBean = new PartnerProposalBean(partnerProposal);
			partnerProposalBeans.add(partnerProposalBean);
		}
		proposalBean.setPartnerProposalBeans(partnerProposalBeans);

		command.setProposalBean(proposalBean);
		return command;
	}


	private void initProposal(PersonBean userPersonBean, ProposalBean proposalBean){
//		 Create a new PersonProposal, make it the main proposer, and
		// set it's state to the initial state
		// Write the main proposer to db.
		PersonProposal personProposal = personProposalService.insertPersonProposal(userPersonBean.getId(),
				proposalBean, PersonProposalTypes.getTypeId("MAIN_RESEARCHER"), false);
		PersonProposalBean personProposalBean = new PersonProposalBean(personProposal);

		// Put the new main proposer on ProposalBean
		proposalBean.setPersonProposalBean(personProposalBean);

		// Build a one person list of proposers and put it on ProposalBean
		List <PersonProposalBean> personProposalBeans = new ArrayList<PersonProposalBean>();
		personProposalBeans.add(personProposalBean);
		proposalBean.setPersonProposalBeans( personProposalBeans);

		// Set the proposal state to the initial state

		ProposalStateDetails proposalStateDetails = proposalStateService.getProposalStateDetails("DRAFT");
		proposalBean.setStateId(proposalStateDetails.getStateId());
		proposalStateHistoryService.insertProposalState("DRAFT", "OPEN", proposalBean, userPersonBean, "Main_Researcher");
	}


	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws ServletException {
    // to actually be able to convert Multipart instance to byte[]
    // we have to register a custom editor
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    // now Spring knows how to handle multipart object and convert them
	}


	/**
	 * A proposalBean holds few update times, this method sets an update time depends who was the updating user
	 *
	 * @param proposalBean
	 * @param personBean
	 */
	/*private void setUpdateTimeByUserPersonBean( ProposalBean proposalBean, PersonBean personBean){
		if (personBean.isAuthorized("EQF", "RESEARCHER")) proposalBean.setProposalUpdate(DateUtils.getCurrentTime());
		else if (personBean.isAuthorized("EQF", "MOP")) proposalBean.setMopUpdate(DateUtils.getCurrentTime());
		else if (personBean.isAuthorized("EQF", "SAFETY")) proposalBean.setSafetyUpdate(DateUtils.getCurrentTime());
		else if (personBean.isAuthorized("EQF", "ANIMALS")) proposalBean.setAnimalsUpdate(DateUtils.getCurrentTime());
		else if (personBean.isAuthorized("EQF", "HUMAN")) proposalBean.setHumanUpdate(DateUtils.getCurrentTime());
		else if (personBean.isAuthorized("EQF", "DEAN")) proposalBean.setDeanUpdate(DateUtils.getCurrentTime());
		else if (personBean.isAuthorized("EQF", "YISSUM")) proposalBean.setYissumUpdate(DateUtils.getCurrentTime());
	}*/


	/**
	 *
	 * if an attachment is null or empty we load the previous attachment so it won't be deleted on update
	 *
	 * @param newProposal
	 * @param preupdateProposal
	 */
	private void handleAttachmentsAndExperimentApprovals(ProposalBean newProposal, Proposal preupdateProposal){
		newProposal.setResearchAttachments(preupdateProposal.getResearchAttachments());

		if (newProposal.getEthicsAttach() == null || newProposal.getEthicsAttach().length == 0){
			newProposal.setEthicsAttach(preupdateProposal.getEthicsAttach());
			newProposal.setEthicsAttachContentType(preupdateProposal.getEthicsAttachContentType());
		}
		if (newProposal.isExperimental()){
			if (newProposal.getSafetyAttach() == null || newProposal.getSafetyAttach().length == 0){
				newProposal.setSafetyAttach(preupdateProposal.getSafetyAttach());
				newProposal.setSafetyAttachContentType(preupdateProposal.getSafetyAttachContentType());
			}
			if (newProposal.isHumanExperiment()){
				if (newProposal.getHumanAttach() == null || newProposal.getHumanAttach().length == 0){
					newProposal.setHumanAttach(preupdateProposal.getHumanAttach());
					newProposal.setHumanAttachContentType(preupdateProposal.getHumanAttachContentType());
				}
			}
			else {
				newProposal.setHumanExperiment(false);
			}
			if (newProposal.isAnimalsExperiment()){
				if (newProposal.getAnimalsAttach() == null || newProposal.getAnimalsAttach().length == 0){
					newProposal.setAnimalsAttach(preupdateProposal.getAnimalsAttach());
					newProposal.setAnimalsAttachContentType(preupdateProposal.getAnimalsAttachContentType());
				}
			}
			else {
				newProposal.setAnimalsExperiment(false);
			}
		}
	}


	private void handleYissum(ProposalBean proposalBean, Proposal preupdateProposal, PersonBean userPersonBean){
		preupdateProposal.setYissumResearcherHandled(true);
		if (proposalBean.isNeedsYissumApproval()){
			preupdateProposal.setNeedsYissumApproval(true);
			proposalStateHistoryService.insertProposalState(proposalBean.getStateId(), "SEND_YISSUM", proposalBean, userPersonBean, "Researcher");
			// It's assumed there is only one person in Yissum, otherwise we'll need to let user choose
			PersonBean yissumPerson = personListService.getPersonsListPerson(
					configurationService.getConfigurationInt("yissumApproversListId"));
			mailMessageService.createSimpleProposalMail(yissumPerson, userPersonBean, proposalBean, "yissumApprovalRequest");
			preupdateProposal.setYissumSend(true);
			personProposalService.insertPersonProposal(yissumPerson.getId(), proposalBean,
					PersonProposalTypes.getTypeId("YISSUM"), INFORMED);
			personProposalService.updatePersonProposal(proposalBean.getId(),
					PersonProposalTypes.getTypeId("YISSUM"),
					PersonProposalRequiredActions.getRequiredActionsIds("APPROVE"));
		}
		else{
			preupdateProposal.setNeedsYissumApproval(false);
		}
		proposalService.updateProposal(preupdateProposal);
	}

	private void handleYissumApproval(ProposalBean proposalBean, Proposal preupdateProposal, PersonBean userPersonBean){
		preupdateProposal.setYissumHandled(true);
		preupdateProposal.setYissumApproved(proposalBean.isYissumApproved());
		preupdateProposal.setYissumRefusalDetails(proposalBean.getYissumRefusalDetails());
		String messageKey = "yissum"+(proposalBean.isYissumApproved() ? "Approval" : "Refusal");
		mailMessageService.createSimpleProposalMail(proposalBean.getMainResearcher(), proposalBean, messageKey);
		proposalService.updateProposal(preupdateProposal);
		personProposalService.updatePersonProposal(proposalBean.getId(),
				PersonProposalTypes.getTypeId("YISSUM"),
				PersonProposalRequiredActions.getRequiredActionsIds("NOTHING"));
	}

	public class EditProposalControllerCommand{
		private ProposalBean proposalBean;
		private int addedResearcherId;
		private String addedResearcher;
		private int removedResearcherId;
		private Partner partner = new Partner();
		private Institute institute = new Institute();
		private int removedPartnerId;
		private PartnerInstitute partnerInstitute = new PartnerInstitute();
		private int removedPartnerInstituteId;
		private String userMessage;
		private String action;
		private byte [] file;
		private String addedFund;


		public String getAddedFund() {
			return addedFund;
		}
		public void setAddedFund(String addedFund) {
			this.addedFund = addedFund;
		}
		public Partner getPartner() {
			return partner;
		}
		public void setPartner(Partner partner) {
			this.partner = partner;
		}
		public byte[] getFile() {
			return file;
		}
		public void setFile(byte[] file) {
			this.file = file;
		}
		public String getUserMessage() {
			return userMessage;
		}
		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}
		public int getRemovedResearcherId() {
			return removedResearcherId;
		}
		public void setRemovedResearcherId(int removedResearcherId) {
			this.removedResearcherId = removedResearcherId;
		}
		public ProposalBean getProposalBean() {
			return proposalBean;
		}
		public void setProposalBean(ProposalBean proposalBean) {
			this.proposalBean = proposalBean;
		}
		public int getAddedResearcherId() {
			return addedResearcherId;
		}
		public void setAddedResearcherId(int addedResearcherId) {
			this.addedResearcherId = addedResearcherId;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getAddedResearcher() {
			return addedResearcher;
		}
		public void setAddedResearcher(String addedResearcher) {
			this.addedResearcher = addedResearcher;
		}
		public Institute getInstitute() {
			return institute;
		}
		public void setInstitute(Institute institute) {
			this.institute = institute;
		}
		public int getRemovedPartnerId() {
			return removedPartnerId;
		}
		public void setRemovedPartnerId(int removedPartnerId) {
			this.removedPartnerId = removedPartnerId;
		}
		public PartnerInstitute getPartnerInstitute() {
			return partnerInstitute;
		}
		public void setPartnerInstitute(PartnerInstitute partnerInstitute) {
			this.partnerInstitute = partnerInstitute;
		}
		public int getRemovedPartnerInstituteId() {
			return removedPartnerInstituteId;
		}
		public void setRemovedPartnerInstituteId(int removedPartnerInstituteId) {
			this.removedPartnerInstituteId = removedPartnerInstituteId;
		}


	}

	private ProposalService proposalService;


	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	private PersonListService personListService;


	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

	private PersonProposalService personProposalService;


	public void setPersonProposalService(PersonProposalService personProposalService) {
		this.personProposalService = personProposalService;
	}

	private FundService fundService;

	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

	private ProposalStateHistoryService proposalStateHistoryService;

	public void setproposalStateHistoryService(
			ProposalStateHistoryService proposalStateHistoryService) {
		this.proposalStateHistoryService = proposalStateHistoryService;
	}

	private PersonProposalMessageService personProposalMessageService;


	public void setPersonProposalMessageService(
			PersonProposalMessageService personProposalMessageService) {
		this.personProposalMessageService = personProposalMessageService;
	}


	private MailMessageService mailMessageService;


	public void setMailMessageService(MailMessageService mailMessageService) {
		this.mailMessageService = mailMessageService;
	}

	private ProposalStateService proposalStateService;


	public void setProposalStateService(ProposalStateService proposalStateService) {
		this.proposalStateService = proposalStateService;
	}

	private PersonProposalStateService personProposalStateService;


	public void setPersonProposalStateService(
			PersonProposalStateService personProposalStateService) {
		this.personProposalStateService = personProposalStateService;
	}

	private UniverseService universeService;

	public void setUniverseService(UniverseService universeService) {
		this.universeService = universeService;
	}

	private InstituteListService instituteListService;

	public void setInstituteListService(InstituteListService instituteListService) {
		this.instituteListService = instituteListService;
	}

	private PartnerService partnerService;

	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	private PartnerProposalService partnerProposalService;

	public void setPartnerProposalService(
			PartnerProposalService partnerProposalService) {
		this.partnerProposalService = partnerProposalService;
	}

	private PartnerListService partnerListService;

	public void setPartnerListService(PartnerListService partnerListService) {
		this.partnerListService = partnerListService;
	}

	private ProposalListService proposalListService;

	public void setProposalListService(ProposalListService proposalListService) {
		this.proposalListService = proposalListService;
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}
}
