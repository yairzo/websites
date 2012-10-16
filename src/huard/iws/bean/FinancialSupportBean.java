package huard.iws.bean;

import huard.iws.model.FinancialSupport;

public class FinancialSupportBean {
	private int id;
	private String conferenceProposalId;
	private String name;
	private String sum;
	private String currency;
	private String type;
	private String sumPerson;
	private byte[] referenceFile;
	private String fileContentType;
	
	public static int TYPE_ASSOSIATE = 1;
	public static int TYPE_EXTERNAL = 2;
	public static int TYPE_ADMITANCEFEE = 3;

	public FinancialSupportBean(){
		this.id = 0;
		this.conferenceProposalId = "";
		this.name = "";
		this.sum = "";
		this.currency = "";
		this.type = "";
		this.sumPerson="";
		this.referenceFile = new byte[0];
		this.fileContentType = "";
	}
	
	
	public FinancialSupportBean(FinancialSupport financialSupport){
		this.id = financialSupport.getId();
		this.conferenceProposalId = "" + financialSupport.getConferenceProposalId();
		this.name = financialSupport.getName();
		this.sum = financialSupport.getSum();
		this.currency = financialSupport.getCurrency();
		this.type = "" + financialSupport.getType();
		this.sumPerson=	financialSupport.getSumPerson();
		this.referenceFile = financialSupport.getReferenceFile();
		this.fileContentType = financialSupport.getFileContentType();
	}
	
	
	public FinancialSupport toFinancialSupport(){
		FinancialSupport financialSupport = new FinancialSupport();
		financialSupport.setId(id);
		financialSupport.setConferenceProposalId(conferenceProposalId.isEmpty() ? 0 : Integer.parseInt(conferenceProposalId));
		financialSupport.setName(name);
		financialSupport.setSum(sum);
		financialSupport.setCurrency(currency);
		financialSupport.setType(type.isEmpty() ? 0 : Integer.parseInt(type));
		financialSupport.setSumPerson(sumPerson);
		financialSupport.setReferenceFile(referenceFile);
		financialSupport.setFileContentType(fileContentType);
		return financialSupport;
	}
	
	
	
	
	public byte[] getReferenceFile() {
		return referenceFile;
	}


	public void setReferenceFile(byte[] referenceFile) {
		this.referenceFile = referenceFile;
	}


	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
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
	public String getConferenceProposalId() {
		return conferenceProposalId;
	}
	public void setConferenceProposalId(String conferenceProposalId) {
		this.conferenceProposalId = conferenceProposalId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSumPerson() {
		return sumPerson;
	}
	public void setSumPerson(String sumPerson) {
		this.sumPerson = sumPerson;
	}


}
