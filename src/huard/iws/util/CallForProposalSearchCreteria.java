package huard.iws.util;
import java.util.Set;
import java.util.Iterator;

public class CallForProposalSearchCreteria extends SearchCreteria{
	
	protected boolean searchByTemporaryFund;
	protected int searchByFund;
	protected int searchByDesk;
	protected String searchBySubjectIds;
	protected String searchBySubmissionDateFrom;
	protected String searchBySubmissionDateTo;
	protected int searchByType;
	protected String searchBySearchWords;
	protected String searchWords;
	protected int searchByCreator;
	protected boolean searchDeleted;
	protected boolean searchExpired;
	
	
	public CallForProposalSearchCreteria(){
		super();
		this.searchByTemporaryFund = false;
		this.searchByFund = 0;
		this.searchByDesk = 0;
		this.searchBySubjectIds = "";
		this.searchBySubmissionDateFrom = "";
		this.searchBySubmissionDateTo = "";
		this.searchByType = 0;
		this.searchBySearchWords= "";
		this.searchWords="";
		this.searchByCreator=0;
		this.searchDeleted=false;
		this.searchExpired=false;
	}

	
	public boolean getSearchByTemporaryFund() {
		return searchByTemporaryFund;
	}

	public void setSearchByTemporaryFund(boolean searchByTemporaryFund) {
		this.searchByTemporaryFund = searchByTemporaryFund;
	}


	public int getSearchByFund() {
		return searchByFund;
	}

	public void setSearchByFund(int searchByFund) {
		this.searchByFund = searchByFund;
	}


	public int getSearchByDesk() {
		return searchByDesk;
	}

	public void setSearchByDesk(int searchByDesk) {
		this.searchByDesk = searchByDesk;
	}
	

	public String getSearchBySubjectIds() {
		return searchBySubjectIds;
	}

	public void setSearchBySubjectIds(String searchBySubjectIds) {
		this.searchBySubjectIds = searchBySubjectIds;
	}

	public String getSearchBySubmissionDateFrom() {
		return searchBySubmissionDateFrom;
	}

	public void setSearchBySubmissionDateFrom(String searchBySubmissionDateFrom) {
		this.searchBySubmissionDateFrom = searchBySubmissionDateFrom;
	}

	public String getSearchBySubmissionDateTo() {
		return searchBySubmissionDateTo;
	}

	public void setSearchBySubmissionDateTo(String searchBySubmissionDateTo) {
		this.searchBySubmissionDateTo = searchBySubmissionDateTo;
	}

	public int getSearchByType() {
		return searchByType;
	}

	public void setSearchByType(int searchByType) {
		this.searchByType = searchByType;
	}
	
	public String getSearchBySearchWords() {
		return searchBySearchWords;
	}

	public void setSearchBySearchWords(Set<Long> searchBySearchWords) {
		this.searchBySearchWords = BaseUtils.getStringFromLongSet(searchBySearchWords);
	}
	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
	}
	
	public int getSearchByCreator() {
		return searchByCreator;
	}

	public void setSearchByCreator(int searchByCreator) {
		this.searchByCreator = searchByCreator;
	}

	
	public boolean getSearchDeleted() {
		return searchDeleted;
	}

	public void setSearchDeleted(boolean searchDeleted) {
		this.searchDeleted = searchDeleted;
	}

	public boolean getSearchExpired() {
		return searchExpired;
	}

	public void setSearchExpired(boolean searchExpired) {
		this.searchExpired = searchExpired;
	}

}
