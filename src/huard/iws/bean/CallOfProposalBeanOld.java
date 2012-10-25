package huard.iws.bean;

import huard.iws.model.CallOfProposalOld;
import huard.iws.model.Fund;
import huard.iws.model.MopDesk;
import huard.iws.service.ConfigurationService;
import huard.iws.service.FundService;
import huard.iws.service.MessageService;
import huard.iws.service.MopDeskService;
import huard.iws.util.ApplicationContextProvider;
import huard.iws.util.DateUtils;
import huard.iws.util.LanguageUtils;

public class CallOfProposalBeanOld {
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

	public CallOfProposalBeanOld(){
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

	public CallOfProposalBeanOld(CallOfProposalOld callOfProposal, boolean applyObjs){
		this.id = callOfProposal.getId();
		this.fundId = callOfProposal.getFundId();
		this.publicationTimeMillis = callOfProposal.getPublicationTimeMillis();
		this.submissionTimeMillis = callOfProposal.getSubmissionTimeMillis();
		this.title = callOfProposal.getTitle();
		this.amountOfGrant = callOfProposal.getAmountOfGrant();
		this.deskId = callOfProposal.getDeskId();
		init(applyObjs);
	}

	public CallOfProposalOld toCallOfProposal(){
		CallOfProposalOld callOfProposal = new CallOfProposalOld();
		callOfProposal.setId(id);
		callOfProposal.setFundId(fundId);
		callOfProposal.setPublicationTimeMillis(publicationTimeMillis);
		callOfProposal.setSubmissionTimeMillis(submissionTimeMillis);
		callOfProposal.setTitle(title);
		callOfProposal.setAmountOfGrant(amountOfGrant);
		callOfProposal.setDeskId(deskId);
		return callOfProposal;
	}

	public String toString(){
		//StringBuilder sb = new StringBuilder(messageService.getMessage("general.callOfProposal.titlePrefix", getLocaleId()));
		StringBuilder sb = new StringBuilder();
		sb.append(" <a class=\"big\" href=\"http://" + configurationService.getConfigurationString("webServer") +
				"/huard/infoPageViewer.jsp?ardNum=" + this.id + "\">" + title + "</a><br/> ");

		sb.append("<span class=\"medium\">");
		if (fund != null)
			sb.append(" <a class=\"bold\" href=\"http://" + fund.getWebAddress() + "\">" + fund.getName() + ", " + fund.getShortName() + "</a>;;");
		else
			sb.append("# Funding agency #");
		if(submissionTimeMillis==0)
			sb.append(messageService.getMessage("general.callOfProposal.submission", getLocaleId()) + ": "
					+ messageService.getMessage("general.callOfProposal.submissionAllYear", getLocaleId()) + " <br/> ");
		else	
			sb.append(messageService.getMessage("general.callOfProposal.submission", getLocaleId()) + ": "
				+ DateUtils.getLocaleDependentShortDateFormat(submissionTimeMillis, getLocaleId()) + " <br/> ");
		if (! amountOfGrant.isEmpty())
			sb.append(messageService.getMessage("general.callOfProposal.amountOfGrant", getLocaleId()) + ": " + amountOfGrant + ";;");
		sb.append(messageService.getMessage("general.callOfProposal.successIndex", getLocaleId()) + ": xxxxx;;");
		sb.append(" " +messageService.getMessage("general.callOfProposal.deskPrefix", getLocaleId()));
		MopDeskBean mopDeskBean = new MopDeskBean(mopDesk);
		//sb.append("#mu# #mp##mue#, <span class=\"bold\">" + mopDeskBean.getName(getLocaleId()) + "." +messageService.getMessage("general.callOfProposal.publication", getLocaleId()) + ":" + DateUtils.getLocaleDependentShortDateFormat(publicationTimeMillis, getLocaleId()) + "</span>");
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
