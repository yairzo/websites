package huard.iws.bean;

import huard.iws.model.CallForProposalOld;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.service.ConfigurationService;
import huard.iws.service.FundService;
import huard.iws.service.MessageService;
import huard.iws.service.MopDeskService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;

public class CallForProposalBeanOld {
	private int id;
	private int fundId;
	private long publicationTimeMillis;
	private long submissionTimeMillis;
	private String title;
	private String amountOfGrant;
	private String deskId;

	private Fund fund;
	private MopDesk mopDesk;
	private MessageService messageService;
	private ConfigurationService configurationService;

	public Fund getFund() {
		return fund;
	}

	public CallForProposalBeanOld(){
		this.id = 0;
		this.fundId = 0;
		this.publicationTimeMillis = 0;
		this.submissionTimeMillis = 0;
		this.title = "";
		this.amountOfGrant = "";
		this.deskId = "";
	}

	public void init(boolean applyObjs){
		if (applyObjs && fundId > 0){
			FundService fundService = (FundService) ApplicationContextProvider.getContext().getBean("fundService");
			fund = fundService.getArdFund(fundId);
			MopDeskService mopDeskService = (MopDeskService) ApplicationContextProvider.getContext().getBean("mopDeskService");
			mopDesk = mopDeskService.getMopDesk(deskId);
		}
		messageService = (MessageService) ApplicationContextProvider.getContext().getBean("messageService");
		configurationService = (ConfigurationService) ApplicationContextProvider.getContext().getBean("configurationService");
	}

	public CallForProposalBeanOld(CallForProposalOld callForProposal, boolean applyObjs){
		this.id = callForProposal.getId();
		this.fundId = callForProposal.getFundId();
		this.publicationTimeMillis = callForProposal.getPublicationTimeMillis();
		this.submissionTimeMillis = callForProposal.getSubmissionTimeMillis();
		this.title = callForProposal.getTitle();
		this.amountOfGrant = callForProposal.getAmountOfGrant();
		this.deskId = callForProposal.getDeskId();
		init(applyObjs);
	}

	public CallForProposalOld toCallForProposal(){
		CallForProposalOld callForProposal = new CallForProposalOld();
		callForProposal.setId(id);
		callForProposal.setFundId(fundId);
		callForProposal.setPublicationTimeMillis(publicationTimeMillis);
		callForProposal.setSubmissionTimeMillis(submissionTimeMillis);
		callForProposal.setTitle(title);
		callForProposal.setAmountOfGrant(amountOfGrant);
		callForProposal.setDeskId(deskId);
		return callForProposal;
	}

	public String toPostMessage(){
		//StringBuilder sb = new StringBuilder(messageService.getMessage("general.callForProposal.titlePrefix", getLocaleId()));
		StringBuilder sb = new StringBuilder();
		sb.append(" <a class=\"big\" href=\"http://" + configurationService.getConfigurationString("website", "webServer") +
				"/huard/infoPageViewer.jsp?ardNum=" + this.id + "\">" + title + "</a><br/> ");

		sb.append("<span class=\"medium\">");
		if (fund != null)
			sb.append(" <a class=\"bold\" href=\"http://" + fund.getWebAddress() + "\">" + fund.getName() + ", " + fund.getShortName() + "</a>;;");
		else
			sb.append("# Funding agency #");
		if(submissionTimeMillis==0)
			sb.append(messageService.getMessage("general.callForProposal.submission", getLocaleId()) + ": "
					+ messageService.getMessage("general.callForProposal.submissionAllYear", getLocaleId()) + " <br/> ");
		else	
			sb.append(messageService.getMessage("general.callForProposal.submission", getLocaleId()) + ": "
				+ DateUtils.getLocaleDependentShortDateFormat(submissionTimeMillis, getLocaleId()) + " <br/> ");
		if (! amountOfGrant.isEmpty())
			sb.append(messageService.getMessage("general.callForProposal.amountOfGrant", getLocaleId()) + ": " + amountOfGrant + ";;");
		sb.append(messageService.getMessage("general.callForProposal.successIndex", getLocaleId()) + ": xxxxx;;");
		sb.append(" " +messageService.getMessage("general.callForProposal.deskPrefix", getLocaleId()));
		MopDeskBean mopDeskBean = new MopDeskBean(mopDesk);
		//sb.append("#mu# #mp##mue#, <span class=\"bold\">" + mopDeskBean.getName(getLocaleId()) + "." +messageService.getMessage("general.callForProposal.publication", getLocaleId()) + ":" + DateUtils.getLocaleDependentShortDateFormat(publicationTimeMillis, getLocaleId()) + "</span>");
		sb.append("#mu# #mp##mue#, <span class=\"bold\">" + mopDeskBean.getName(getLocaleId()) + "." + "</span>");

		return sb.toString();
	}

	public String getTrimmedTitle(){
		String trimmedTitle =  title.substring(0, Math.min(title.length(), 120));
		if (title.length() > 120)
			trimmedTitle += "...";
		return trimmedTitle;
	}

	public String getLocaleId(){
		return LanguageUtils.getLanguage(title).getLocaleId();
	}

	public String getAmountOfGrant() {
		return amountOfGrant;
	}

	public void setAmountOfGrant(String amountOfGrant) {
		this.amountOfGrant = amountOfGrant;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getSubmissionTimeMillis() {
		return submissionTimeMillis;
	}

	public void setSubmissionTimeMillis(long submissionTimeMillis) {
		this.submissionTimeMillis = submissionTimeMillis;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeskId() {
		return deskId;
	}

	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	public long getPublicationTimeMillis() {
		return publicationTimeMillis;
	}

	public void setPublicationTimeMillis(long publicationTimeMillis) {
		this.publicationTimeMillis = publicationTimeMillis;
	}

}
