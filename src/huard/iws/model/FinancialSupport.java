package huard.iws.model;

public class FinancialSupport {
	private int id;
	private int conferenceProposalId;
	private String name;
	private String sum;
	private String currency;
	private int type;

	public static int TYPE_ASSOSIATE = 1;
	public static int TYPE_EXTERNAL = 2;
	public static int TYPE_ADMITANCEFEE = 3;

	public FinancialSupport(){
		this.id = 0;
		this.conferenceProposalId = 0;
		this.name = "";
		this.sum = "";
		this.currency = "";
		this.type = 0;
	}
	
	public boolean isEmpty(){
		return name.isEmpty() && sum.isEmpty();
	}
	
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
