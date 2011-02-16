package huard.iws.bean;

import huard.iws.constant.PersonProposalTypes;
import huard.iws.model.Fund;
import huard.iws.model.PersonProposal;
import huard.iws.model.Proposal;
import huard.iws.model.ProposalAttachment;
import huard.iws.model.ProposalState;
import huard.iws.model.Searchable;
import huard.iws.service.FundService;
import huard.iws.service.PersonProposalService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;
import huard.iws.util.SearchCreteria;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



public class ProposalBean implements Searchable{
	//private static final Logger logger = Logger.getLogger(ProposalBean.class);

	private int id;
	private int yearId;
	private String hebrewTitle;
	private String englishTitle;
	private int fundId;
	private List<ProposalAttachment> researchAttachments;
	private byte [] ethicsAttach;
	private String ethicsAttachContentType;
	private Timestamp proposalUpdate;
	private Timestamp creationDate;
	private int stateId;
	private boolean researcherApproved;

	private boolean experimental;
	private byte [] safetyAttach;
	private String safetyAttachContentType;
	private boolean safetySend;
	private Timestamp safetySendDate;
	private boolean safetyApproved;
	private Timestamp safetyUpdate;
	private String safetyRefusalDetails;

	private boolean humanExperiment;
	private boolean humanSend;
	private byte [] humanAttach;
	private String humanAttachContentType;
	private Timestamp humanSendDate;
	private boolean humanApproved;
	private Timestamp humanUpdate;
	private String humanRefusalDetails;

	private boolean animalsExperiment;
	private byte []  animalsAttach;
	private String animalsAttachContentType;
	private boolean animalsSend;
	private Timestamp animalsSendDate;
	private boolean animalsApproved;
	private Timestamp animalsUpdate;
	private String animalsRefusalDetails;



	private boolean deanApproved;
	private String deanRefusalDetails;

	private int fundingAgencyApproved;
	private String fundingAgencyDetails;

	private boolean yissumResearcherHandled;
	private boolean needsYissumApproval;
	private boolean yissumSend;
	private Timestamp yissumSendDate;
	private boolean yissumHandled;
	private boolean yissumApproved;
	private Timestamp yissumUpdate;
	private String yissumRefusalDetails;

	private String budgetHujiId;
	private boolean archived;

	private boolean busyRecord;
	private List<PersonProposalBean> personProposalBeans;
	private List<PartnerProposalBean> partnerProposalBeans;

	private PersonProposalBean personProposalBean;
	private List<ProposalState> statesHistory;
	private Fund fund;
	private boolean experimentApproved;
	private int deanId;
	private PersonBean dean;
	private int budgetOfficerId;
	private PersonBean budgetOfficer;
	private PersonBean archivePerson;
	private boolean budgetApproved;
	private PersonBean yissum;

	private ProposalAttachment addedResearchAttachment = new ProposalAttachment();


	public ProposalAttachment getAddedResearchAttachment() {
		return addedResearchAttachment;
	}


	public void setAddedResearchAttachment(
			ProposalAttachment addedResearchAttachment) {
		this.addedResearchAttachment = addedResearchAttachment;
	}


	public void setYissum(PersonBean yissum) {
		this.yissum = yissum;
	}


	public boolean isBudgetApproved() {
		return budgetApproved;
	}


	public void setBudgetApproved(boolean budgetApproved) {
		this.budgetApproved = budgetApproved;
	}


	public PersonBean getArchivePerson() {
		return archivePerson;
	}


	public void setArchivePerson(PersonBean archivePerson) {
		this.archivePerson = archivePerson;
	}


	public String getBudgetHujiId() {
		return budgetHujiId;
	}


	public void setBudgetHujiId(String budgetHujiId) {
		this.budgetHujiId = budgetHujiId;
	}


	public void setBudgetOfficer(PersonBean budgetOfficer) {
		this.budgetOfficer = budgetOfficer;
	}


	public int getBudgetOfficerId() {
		return budgetOfficerId;
	}


	public void setBudgetOfficerId(int budgetOfficerId) {
		this.budgetOfficerId = budgetOfficerId;
	}


	public ProposalBean(){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		this.id =0;
		this.yearId = 0;
		this.hebrewTitle = "";
		this.englishTitle = "";
		this.fundId = 0;
		this.researchAttachments = new ArrayList<ProposalAttachment>();

		this.ethicsAttach = new byte[0];
		this.ethicsAttachContentType = "";
		this.proposalUpdate = now;
		this.creationDate = now;
		this.stateId = 0;
		this.researcherApproved = false;

		this.experimental = false;
		this.safetyAttach = new byte[0];
		this.safetyAttachContentType = "";
		this.safetySend = false;
		this.safetySendDate = now;
		this.safetyApproved = false;
		this.safetyUpdate = now;
		this.safetyRefusalDetails ="";

		this.humanExperiment = false;
		this.humanAttach = new byte[0];
		this.humanAttachContentType = "";
		this.humanSend = false;
		this.humanSendDate = now;
		this.humanApproved = false;
		this.humanUpdate = now;
		this.humanRefusalDetails ="";

		this.animalsExperiment = false;
		this.animalsAttach = new byte[0];
		this.animalsAttachContentType ="";
		this.animalsSend = false;
		this.animalsSendDate = now;
		this.animalsApproved = false;
		this.animalsUpdate = now;
		this.animalsRefusalDetails = "";


		this.deanApproved = false;
		this.deanRefusalDetails ="";

		this.fundingAgencyApproved = -1;
		this.fundingAgencyDetails = "";

		this.yissumResearcherHandled = false;
		this.needsYissumApproval = false;
		this.yissumSend = false;
		this.yissumSendDate = now;
		this.yissumHandled = false;
		this.yissumApproved = false;
		this.yissumUpdate = now;
		this.yissumRefusalDetails ="";

		this.budgetHujiId = "";
		this.archived = false;

	}


	public void init(){
		if ((fundId)>0){
			Object obj = ApplicationContextProvider.getContext().getBean("fundService");
			FundService fundService = (FundService)obj;
			this.fund = fundService.getFund(this.getFundId());
		}
	}

	public boolean isLastToApprove(){
		int counter=0;
		for (PersonProposalBean personProposalBean: getPersonProposalBeans() ){
			if (personProposalBean.isApproval() == false) counter ++;
		}
		return counter ==0;
	}

	public boolean isHasYearId(){
		return yearId > 0;
	}

	public String getFormatedYearId(){
		return yearId%100000 +"/"+yearId/100000;
	}

	public boolean isReadyForAddResearchers(){
		return hebrewTitle.length() > 0;
	}

	public boolean isReadyForApproval(){
		return (this.hebrewTitle.length() > 0 && this.englishTitle.length() > 0
				&& this.fundId>0 && this.fund !=null &&! this.fund.isTemporary()
				&& this.fund.getDeskId() > 0 && this.researchAttachments !=null && this.researchAttachments.size() > 0
				);
	}

	public boolean approvedByUser(PersonBean userPersonBean){
		for (PersonProposalBean personProposalBean: personProposalBeans){
			if (personProposalBean.getPersonId() == userPersonBean.getId() && personProposalBean.isApproval()){
				return true;
			}
		}
		return false;
	}


	public int getDeanApprovalStateId(){
		if (! deanApproved){
			if (deanRefusalDetails==null) return 0;
			else return 2;
		}
		else return 1;
	}

	public PersonBean getDean() {
		if (dean == null){
			Object obj = ApplicationContextProvider.getContext().getBean("personProposalService");
			PersonProposalService personProposalService = (PersonProposalService)obj;
			PersonProposal personProposal = personProposalService.getPersonProposalByType(this.id,
					PersonProposalTypes.getTypeId("DEAN"));
			dean = new PersonProposalBean(personProposal).getPersonBean();
		}
		return dean;

	}

	public PersonBean getYissum() {
		if (yissum == null){
			Object obj = ApplicationContextProvider.getContext().getBean("personProposalService");
			PersonProposalService personProposalService = (PersonProposalService)obj;
			PersonProposal personProposal = personProposalService.getPersonProposalByType(this.id,
					PersonProposalTypes.getTypeId("YISSUM"));
			yissum = new PersonProposalBean(personProposal).getPersonBean();
		}
		return yissum;

	}


	public PersonBean getBudgetOfficer() {
		if (budgetOfficer == null){
			Object obj = ApplicationContextProvider.getContext().getBean("personProposalService");
			PersonProposalService personProposalService = (PersonProposalService)obj;
			PersonProposal personProposal = personProposalService.getPersonProposalByType(this.id,
					PersonProposalTypes.getTypeId("BUDGET_OFFICER"));
			budgetOfficer = new PersonProposalBean(personProposal).getPersonBean();
		}

		return budgetOfficer;

	}


	public void setDean(PersonBean dean) {
		this.dean = dean;
	}


	public boolean isExperimentApproved() {
		return experimentApproved;
	}


	public void setExperimentApproved(boolean experimentApproved) {
		this.experimentApproved = experimentApproved;
	}


	public Fund getFund() {
		return fund;
	}


	public void setFund(Fund fund) {
		this.fund = fund;
	}


	public ProposalBean (Proposal proposal){
		this.id = proposal.getId();
		this.yearId = proposal.getYearId();
		this.hebrewTitle = proposal.getHebrewTitle();
		this.englishTitle = proposal.getEnglishTitle();
		this.fundId = proposal.getFundId();
		this.researchAttachments = proposal.getResearchAttachments();
		this.ethicsAttach = proposal.getEthicsAttach();
		this.ethicsAttachContentType = proposal.getEthicsAttachContentType();
		this.proposalUpdate = proposal.getProposalUpdate();
		this.creationDate = proposal.getCreationDate();
		this.stateId = proposal.getStateId();
		this.researcherApproved = proposal.isResearcherApproved();

		this.experimental = proposal.isExperimental();
		this.safetyAttach = proposal.getSafetyAttach();
		this.safetyAttachContentType = proposal.getSafetyAttachContentType();
		this.safetySend = proposal.isSafetySend();
		this.safetySendDate = proposal.getSafetySendDate();
		this.safetyApproved = proposal.isSafetyApproved();
		this.safetyUpdate = proposal.getSafetyUpdate();
		this.safetyRefusalDetails = proposal.getSafetyRefusalDetails();

		this.humanExperiment = proposal.isHumanExperiment();
		this.humanAttach = proposal.getHumanAttach();
		this.humanAttachContentType = proposal.getHumanAttachContentType();
		this.humanSend = proposal.isHumanSend();
		this.humanSendDate = proposal.getHumanSendDate();
		this.humanApproved = proposal.isHumanApproved();
		this.humanUpdate = proposal.getHumanUpdate();
		this.humanRefusalDetails = proposal.getHumanRefusalDetails();

		this.animalsExperiment = proposal.isAnimalsExperiment();
		this.animalsAttach = proposal.getAnimalsAttach();
		this.animalsAttachContentType = proposal.getAnimalsAttachContentType();
		this.animalsSend = proposal.isAnimalsSend();
		this.animalsSendDate = proposal.getAnimalsSendDate();
		this.animalsApproved = proposal.isAnimalsApproved();
		this.animalsUpdate = proposal.getAnimalsUpdate();
		this.animalsRefusalDetails = proposal.getAnimalsRefusalDetails();

		this.deanApproved = proposal.isDeanApproved();
		this.deanRefusalDetails = proposal.getDeanRefusalDetails();

		this.fundingAgencyApproved = proposal.getFundingAgencyApproved();
		this.fundingAgencyDetails = proposal.getFundingAgencyDetails();

		this.yissumResearcherHandled = proposal.isYissumResearcherHandled();
		this.needsYissumApproval = proposal.isNeedsYissumApproval();
		this.yissumSend = proposal.isYissumSend();
		this.yissumSendDate = proposal.getYissumSendDate();
		this.yissumHandled = proposal.isYissumHandled();
		this.yissumApproved = proposal.isYissumApproved();
		this.yissumUpdate = proposal.getYissumUpdate();
		this.yissumRefusalDetails = proposal.getYissumRefusalDetails();

		this.budgetHujiId = proposal.getBudgetHujiId();


		this.archived = proposal.isArchived();

		init();
	}

	public Proposal toProposal(){
		Proposal proposal = new Proposal();
		proposal.setId(id);
		proposal.setYearId(yearId);
		proposal.setHebrewTitle(hebrewTitle);
		proposal.setEnglishTitle(englishTitle);
		proposal.setFundId(fundId);
		proposal.setResearchAttachments(researchAttachments);
		proposal.setEthicsAttach(ethicsAttach);
		proposal.setEthicsAttachContentType(ethicsAttachContentType);
		proposal.setProposalUpdate(proposalUpdate);
		proposal.setCreationDate(creationDate);
		proposal.setStateId(stateId);
		proposal.setResearcherApproved(researcherApproved);

		proposal.setExperimental(experimental);
		proposal.setSafetyAttach(safetyAttach);
		proposal.setSafetyAttachContentType(safetyAttachContentType);
		proposal.setSafetySend(safetySend);
		proposal.setSafetySendDate(safetySendDate);
		proposal.setSafetyApproved(safetyApproved);
		proposal.setSafetyUpdate(safetyUpdate);
		proposal.setSafetyRefusalDetails(safetyRefusalDetails);

		proposal.setHumanExperiment(humanExperiment);
		proposal.setHumanAttach(humanAttach);
		proposal.setHumanAttachContentType(humanAttachContentType);
		proposal.setHumanSend(humanSend);
		proposal.setHumanSendDate(humanSendDate);
		proposal.setHumanApproved(humanApproved);
		proposal.setHumanUpdate(humanUpdate);
		proposal.setHumanRefusalDetails(humanRefusalDetails);

		proposal.setAnimalsExperiment(animalsExperiment);
		proposal.setAnimalsAttach(animalsAttach);
		proposal.setAnimalsAttachContentType(animalsAttachContentType);
		proposal.setAnimalsSend(animalsSend);
		proposal.setAnimalsSendDate(animalsSendDate);
		proposal.setAnimalsApproved(animalsApproved);
		proposal.setAnimalsUpdate(animalsUpdate);
		proposal.setAnimalsRefusalDetails(animalsRefusalDetails);

		proposal.setDeanApproved(deanApproved);
		proposal.setDeanRefusalDetails(deanRefusalDetails);

		proposal.setFundingAgencyApproved(fundingAgencyApproved);
		proposal.setFundingAgencyDetails(fundingAgencyDetails);

		proposal.setYissumResearcherHandled(yissumResearcherHandled);
		proposal.setNeedsYissumApproval(needsYissumApproval);
		proposal.setYissumSend(yissumSend);
		proposal.setYissumSendDate(yissumSendDate);
		proposal.setYissumHandled(yissumHandled);
		proposal.setYissumApproved(yissumApproved);
		proposal.setYissumUpdate(yissumUpdate);
		proposal.setYissumRefusalDetails(yissumRefusalDetails);

		proposal.setBudgetHujiId(budgetHujiId);
		proposal.setArchived(archived);

		return proposal;
	}

/** expand this method after descision of submission order ****/

	public List<PersonProposalBean> getResearchersPersonProposalBeans(){
		List<PersonProposalBean> researchersPersonProposalBeans = new ArrayList<PersonProposalBean>();
		for (PersonProposalBean personProposalBean: getPersonProposalBeans()){
			if (PersonProposalTypes.isResearcher(personProposalBean.getTypeId())){
				researchersPersonProposalBeans.add(personProposalBean);
			}
		}
		return researchersPersonProposalBeans;
	}

	public PersonBean getMainResearcher(){
		for (PersonProposalBean personProposalBean: getResearchersPersonProposalBeans()){
			if (personProposalBean.getTypeId() == PersonProposalTypes.getTypeId("MAIN_RESEARCHER")){
				return personProposalBean.getPersonBean();
			}
		}
		return null; // We are not supposed to get here ever since every proposal has a main researcher;
	}

	public boolean isOnProposal (PersonBean personBean){
		for (PersonProposalBean personProposalBean: getPersonProposalBeans()){
			if (personProposalBean.getPersonId() == personBean.getId())
				return true;
		}
		return false;
	}


	public boolean isMainResearcherApproved(){
		for (ProposalState state: statesHistory){
			if (state.getId() == 1) return true;
		}
		return false;
	}

	public String getProposalUpdateFormatted() {
		if (proposalUpdate != null){
			return DateUtils.formatDate(proposalUpdate);
		}
		else return "";
	}

	public String getHebrewTitleHandled(){
		if (hebrewTitle !=null && hebrewTitle.length() > 0) {
			return hebrewTitle.length() > 100 ? hebrewTitle.substring(0,100)+" ..." : hebrewTitle;
		}
		else return "[ ללא כותרת ]";
	}

	public boolean isAnimalsApproved() {
		return animalsApproved;
	}
	public void setAnimalsApproved(boolean animalsApproved) {
		this.animalsApproved = animalsApproved;
	}
	public byte[] getAnimalsAttach() {
		return animalsAttach;
	}
	public void setAnimalsAttach(byte[] animalsAttach) {
		this.animalsAttach = animalsAttach;
	}
	public boolean isAnimalsExperiment() {
		return animalsExperiment;
	}
	public void setAnimalsExperiment(boolean animalsExperiment) {
		this.animalsExperiment = animalsExperiment;
	}
	public String getAnimalsRefusalDetails() {
		return animalsRefusalDetails;
	}
	public void setAnimalsRefusalDetails(String animalsRefusalDetails) {
		this.animalsRefusalDetails = animalsRefusalDetails;
	}
	public Timestamp getAnimalsSendDate() {
		return animalsSendDate;
	}
	public void setAnimalsSendDate(Timestamp animalsSendDate) {
		this.animalsSendDate = animalsSendDate;
	}
	public Timestamp getAnimalsUpdate() {
		return animalsUpdate;
	}
	public void setAnimalsUpdate(Timestamp animalsUpdate) {
		this.animalsUpdate = animalsUpdate;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isDeanApproved() {
		return deanApproved;
	}
	public void setDeanApproved(boolean deanApproved) {
		this.deanApproved = deanApproved;
	}
	public String getDeanRefusalDetails() {
		return deanRefusalDetails;
	}
	public void setDeanRefusalDetails(String deanRefusalDetails) {
		this.deanRefusalDetails = deanRefusalDetails;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}
	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}
	public boolean isExperimental() {
		return experimental;
	}
	public void setExperimental(boolean experimental) {
		this.experimental = experimental;
	}
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public int getFundingAgencyApproved() {
		return fundingAgencyApproved;
	}
	public void setFundingAgencyApproved(int fundingAgencyApproved) {
		this.fundingAgencyApproved = fundingAgencyApproved;
	}


	public String getHebrewTitle() {
		return hebrewTitle;
	}
	public void setHebrewTitle(String hebrewTitle) {
		this.hebrewTitle = hebrewTitle;
	}
	public boolean isHumanApproved() {
		return humanApproved;
	}
	public void setHumanApproved(boolean humanApproved) {
		this.humanApproved = humanApproved;
	}
	public byte[] getHumanAttach() {
		return humanAttach;
	}
	public void setHumanAttach(byte[] humanAttach) {
		this.humanAttach = humanAttach;
	}
	public boolean isHumanExperiment() {
		return humanExperiment;
	}
	public void setHumanExperiment(boolean humanExperiment) {
		this.humanExperiment = humanExperiment;
	}
	public String getHumanRefusalDetails() {
		return humanRefusalDetails;
	}
	public void setHumanRefusalDetails(String humanRefusalDetails) {
		this.humanRefusalDetails = humanRefusalDetails;
	}
	public Timestamp getHumanSendDate() {
		return humanSendDate;
	}
	public void setHumanSendDate(Timestamp humanSendDate) {
		this.humanSendDate = humanSendDate;
	}
	public Timestamp getHumanUpdate() {
		return humanUpdate;
	}
	public void setHumanUpdate(Timestamp humanUpdate) {
		this.humanUpdate = humanUpdate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean isNeedsYissumApproval() {
		return needsYissumApproval;
	}
	public void setNeedsYissumApproval(boolean needsYissumApproval) {
		this.needsYissumApproval = needsYissumApproval;
	}

	public Timestamp getProposalUpdate() {
		return proposalUpdate;
	}
	public void setProposalUpdate(Timestamp proposalUpdate) {
		this.proposalUpdate = proposalUpdate;
	}
	public boolean isSafetyApproved() {
		return safetyApproved;
	}
	public void setSafetyApproved(boolean safetyApproved) {
		this.safetyApproved = safetyApproved;
	}
	public byte[] getSafetyAttach() {
		return safetyAttach;
	}
	public void setSafetyAttach(byte[] safetyAttach) {
		this.safetyAttach = safetyAttach;
	}
	public String getSafetyRefusalDetails() {
		return safetyRefusalDetails;
	}
	public void setSafetyRefusalDetails(String safetyRefusalDetails) {
		this.safetyRefusalDetails = safetyRefusalDetails;
	}
	public Timestamp getSafetySendDate() {
		return safetySendDate;
	}
	public void setSafetySendDate(Timestamp safetySendDate) {
		this.safetySendDate = safetySendDate;
	}
	public Timestamp getSafetyUpdate() {
		return safetyUpdate;
	}
	public void setSafetyUpdate(Timestamp safetyUpdate) {
		this.safetyUpdate = safetyUpdate;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public boolean isYissumApproved() {
		return yissumApproved;
	}
	public void setYissumApproved(boolean yissumApproved) {
		this.yissumApproved = yissumApproved;
	}
	public String getYissumRefusalDetails() {
		return yissumRefusalDetails;
	}
	public void setYissumRefusalDetails(String yissumRefusalDetails) {
		this.yissumRefusalDetails = yissumRefusalDetails;
	}
	public Timestamp getYissumSendDate() {
		return yissumSendDate;
	}
	public void setYissumSendDate(Timestamp yissumSendDate) {
		this.yissumSendDate = yissumSendDate;
	}
	public Timestamp getYissumUpdate() {
		return yissumUpdate;
	}
	public void setYissumUpdate(Timestamp yissumUpdate) {
		this.yissumUpdate = yissumUpdate;
	}

	public boolean isBusyRecord() {
		return busyRecord;
	}

	public void setBusyRecord(boolean busyRecord) {
		this.busyRecord = busyRecord;
	}

	public boolean isAnimalsSend() {
		return animalsSend;
	}

	public void setAnimalsSend(boolean animalsSend) {
		this.animalsSend = animalsSend;
	}



	public boolean isHumanSend() {
		return humanSend;
	}

	public void setHumanSend(boolean humanSend) {
		this.humanSend = humanSend;
	}



	public boolean isResearcherApproved() {
		return researcherApproved;
	}

	public void setResearcherApproved(boolean researcherApproved) {
		this.researcherApproved = researcherApproved;
	}

	public boolean isSafetySend() {
		return safetySend;
	}

	public void setSafetySend(boolean safetySend) {
		this.safetySend = safetySend;
	}

	public boolean isYissumSend() {
		return yissumSend;
	}

	public void setYissumSend(boolean yissumSend) {
		this.yissumSend = yissumSend;
	}


	public List<PersonProposalBean> getPersonProposalBeans() {
		if (this.personProposalBeans == null){
			PersonProposalService personProposalService =
				(PersonProposalService) ApplicationContextProvider.getContext().getBean("personProposalService");
			List<PersonProposal> personProposals = personProposalService.getPersonProposals(this.id);
			personProposalBeans = new ArrayList<PersonProposalBean>();
			for (PersonProposal personProposal: personProposals){
				personProposalBeans.add( new PersonProposalBean(personProposal));
			}

		}
		return this.personProposalBeans;
	}


	public void setPersonProposalBeans(List<PersonProposalBean> personProposalBeans) {
		this.personProposalBeans = personProposalBeans;
	}


	public PersonProposalBean getPersonProposalBean() {
		return personProposalBean;
	}


	public void setPersonProposalBean(PersonProposalBean personProposalBean) {
		this.personProposalBean = personProposalBean;
	}


	public String getAnimalsAttachContentType() {
		return animalsAttachContentType;
	}


	public void setAnimalsAttachContentType(String animalsAttachContentType) {
		this.animalsAttachContentType = animalsAttachContentType;
	}


	public String getHumanAttachContentType() {
		return humanAttachContentType;
	}


	public void setHumanAttachContentType(String humanAttachContentType) {
		this.humanAttachContentType = humanAttachContentType;
	}




	public String getSafetyAttachContentType() {
		return safetyAttachContentType;
	}


	public void setSafetyAttachContentType(String safetyAttachContentType) {
		this.safetyAttachContentType = safetyAttachContentType;
	}


	public int getStateId() {
		return stateId;
	}


	public void setStateId(int stateId) {
		this.stateId = stateId;
	}


	public List<ProposalState> getStatesHistory() {
		return statesHistory;
	}

	public void setStatesHistory(List<ProposalState> statesHistory) {
		this.statesHistory = statesHistory;
	}


	public List<PartnerProposalBean> getPartnerProposalBeans() {
		return partnerProposalBeans;
	}

	public void setPartnerProposalBeans(
			List<PartnerProposalBean> partnerProposalBeans) {
		this.partnerProposalBeans = partnerProposalBeans;
	}


	public byte[] getEthicsAttach() {
		return ethicsAttach;
	}


	public void setEthicsAttach(byte[] ethicsAttach) {
		this.ethicsAttach = ethicsAttach;
	}


	public String getEthicsAttachContentType() {
		return ethicsAttachContentType;
	}


	public void setEthicsAttachContentType(String ethicsAttachContentType) {
		this.ethicsAttachContentType = ethicsAttachContentType;
	}


	public int getDeanId() {
		return deanId;
	}


	public void setDeanId(int deanId) {
		this.deanId = deanId;
	}


	public boolean isArchived() {
		return archived;
	}


	public void setArchived(boolean archived) {
		this.archived = archived;
	}


	public boolean isYissumResearcherHandled() {
		return yissumResearcherHandled;
	}


	public void setYissumResearcherHandled(boolean yissumResearcherHandled) {
		this.yissumResearcherHandled = yissumResearcherHandled;
	}


	public boolean isYissumHandled() {
		return yissumHandled;
	}


	public void setYissumHandled(boolean yissumHandled) {
		this.yissumHandled = yissumHandled;
	}


	public List<ProposalAttachment> getResearchAttachments() {
		return researchAttachments;
	}


	public void setResearchAttachments(List<ProposalAttachment> researchAttachments) {
		this.researchAttachments = researchAttachments;
	}


	public String getFundingAgencyDetails() {
		return fundingAgencyDetails;
	}


	public void setFundingAgencyDetails(String fundingAgencyDetails) {
		this.fundingAgencyDetails = fundingAgencyDetails;
	}

	public boolean isMatch(SearchCreteria searchCreteria){
		if (searchCreteria == null || ! searchCreteria.isValid() )
			return true;
		boolean isMatch = false;
		if (searchCreteria.getSearchField().equals("hebrewTitle")){
			isMatch = this.hebrewTitle.equals(searchCreteria.getSearchPhrase());
		}
		if (searchCreteria.getSearchField().equals("mainResearcherId")){
			isMatch = this.getMainResearcher().getId() == Integer.parseInt(searchCreteria.getSearchPhrase());
		}
		if (searchCreteria.getSearchField().equals("fundId")){
			isMatch = (""+this.getFundId()).equals(searchCreteria.getSearchPhrase());
		}
		if (searchCreteria.getSearchField().equals("yearId")){
			isMatch = (""+this.getYearId()).equals(searchCreteria.getSearchPhrase());
		}
		return isMatch;

	}

	public boolean isRequireAction (PersonBean personBean){
		for (PersonProposalBean personProposalBean: getPersonProposalBeans()){
			if (personProposalBean.getPersonId() == personBean.getId()){
				return personProposalBean.isActionRequired();
			}
		}
		return false;
	}

	public boolean isHasHebrewTitle(){
		return this.hebrewTitle.length() > 0;
	}

	public boolean isHasEnglishTitle(){
		return this.englishTitle.length() > 0;
	}

}
