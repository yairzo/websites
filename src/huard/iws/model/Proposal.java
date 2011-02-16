package huard.iws.model;

import huard.iws.bean.ProposalBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Proposal implements Base {

	private final Class beanClass = ProposalBean.class;

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
	private boolean archiveHandled;
	private boolean archived;

	public String getBudgetHujiId() {
		return budgetHujiId;
	}

	public void setBudgetHujiId(String budgetHujiId) {
		this.budgetHujiId = budgetHujiId;
	}

	public Proposal(){
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
		this.creationDate = null;
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

	public Proposal copyProposalBasicDetails(){
		Proposal newProposal = new Proposal();
		newProposal.setId(this.getId());
		newProposal.setHebrewTitle(this.getHebrewTitle());
		newProposal.setEnglishTitle(this.getEnglishTitle());
		newProposal.setFundId(this.getFundId());
		newProposal.setResearchAttachments(this.researchAttachments);
		newProposal.setEthicsAttach(this.getEthicsAttach());
		newProposal.setEthicsAttachContentType(this.getEthicsAttachContentType());
		newProposal.setSafetyAttach(this.getSafetyAttach());
		newProposal.setSafetyAttachContentType(this.getSafetyAttachContentType());
		newProposal.setHumanAttach(this.getHumanAttach());
		newProposal.setHumanAttachContentType(this.getHumanAttachContentType());
		newProposal.setAnimalsAttach(this.getAnimalsAttach());
		newProposal.setAnimalsAttachContentType(this.getAnimalsAttachContentType());
		return newProposal;
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
	public boolean isResearcherApproved() {
		return researcherApproved;
	}
	public void setResearcherApproved(boolean researcherApproved) {
		this.researcherApproved = researcherApproved;
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

	public boolean isArchiveHandled() {
		return archiveHandled;
	}

	public void setArchiveHandled(boolean archiveHandled) {
		this.archiveHandled = archiveHandled;
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

	public Class getBeanClass() {
		return beanClass;
	}

}
