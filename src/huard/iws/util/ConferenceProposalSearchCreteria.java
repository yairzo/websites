package huard.iws.util;

public class ConferenceProposalSearchCreteria extends SearchCreteria{
	
	public static int SUBMITTED = 1;
	public static int DRAFT = 0;
	public static int CURRENT_DEADLINE = 1;
	public static int ALL_DEADLINES = 0;
	public static int NO_APPROVER = 0;
	
	protected int searchByApprover;
	protected int searchBySubmitted;
	protected int searchByDeadline;
	protected int self;
	
	
	public ConferenceProposalSearchCreteria(){
		super();
		this.searchByApprover = 0;
		this.searchBySubmitted = 0;
		this.searchByDeadline = 0;
		this.self=0;
	}

	public int getSelf() {
		return self;
	}

	public void setSelf(int self) {
		this.self = self;
	}
	
	public int getSearchByApprover() {
		return searchByApprover;
	}


	public void setSearchByApprover(int searchByApprover) {
		this.searchByApprover = searchByApprover;
	}


	public int getSearchBySubmitted() {
		return searchBySubmitted;
	}


	public void setSearchBySubmitted(int searchBySubmitted) {
		this.searchBySubmitted = searchBySubmitted;
	}


	public int getSearchByDeadline() {
		return searchByDeadline;
	}


	public void setSearchByDeadline(int searchByDeadline) {
		this.searchByDeadline = searchByDeadline;
	}
	

}
