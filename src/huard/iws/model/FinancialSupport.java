package huard.iws.model;

public class FinancialSupport {
	private int id;
	private int conferenceProposalId;
	private String name;
	private String sum;
	private String currency;
	private int type;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConferenceProposalId() {
		return conferenceProposalId;
	}
	public void setConferenceProposalId(int conferenceProposalId) {
		this.conferenceProposalId = conferenceProposalId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
