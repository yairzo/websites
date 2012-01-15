package huard.iws.model;

import huard.iws.bean.PersonBean;
import huard.iws.service.PersonService;
import huard.iws.util.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConferenceProposalGrading {
	private int id;
	private int approverId;
	private int adminId;
	private long deadline;
	private long sentForGradingDate;
	private long finishedGradingDate;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApproverId() {
		return approverId;
	}
	public PersonBean getApprover() {
		PersonService personService = (PersonService) ApplicationContextProvider
				.getContext().getBean("personService");
		PersonBean approver = new PersonBean(
				personService.getPerson(this.approverId));
		return approver;
	}
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	public String getFormattedDeadline() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date deadlineTmp = new Date(deadline);
		return formatter.format(deadlineTmp);
	}
	public long getSentForGradingDate() {
		return sentForGradingDate;
	}
	public void setSentForGradingDate(long sentForGradingDate) {
		this.sentForGradingDate = sentForGradingDate;
	}
	public String getFormattedSentForGradingDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date tmpDate = new Date(sentForGradingDate);
		return formatter.format(tmpDate);
	}
	public long getFinishedGradingDate() {
		return finishedGradingDate;
	}
	public void setFinishedGradingDate(long finishedGradingDate) {
		this.finishedGradingDate = finishedGradingDate;
	}
	public String getFormattedFinishedGradingDate() {
		if (finishedGradingDate==1000)
			return "";
		else{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date tmpDate = new Date(finishedGradingDate);
			return formatter.format(tmpDate);
		}
	}


}
