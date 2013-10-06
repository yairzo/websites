package huard.iws.util;
import java.util.Set;

public class CallForProposalSearchCreteria extends SearchCreteria{
	
	protected boolean searchByTemporaryFund;
	protected int searchByFund;
	protected int searchByDesk;
	protected String searchBySubjectIds;
	protected boolean searchByAllSubjects;
	protected String searchBySubmissionDateFrom;
	protected String searchBySubmissionDateTo;
	protected boolean searchByAllYear;
	protected int searchByType;
	protected String searchBySearchWords;
	protected String searchWords;
	protected int searchByCreator;
	protected boolean searchDeleted;
	protected boolean searchExpired;
	protected boolean searchOpen;
	protected int searchByTargetAudience;
	protected String searchByCountryIds;
	protected boolean searchByAllCountries;
	protected int limit;
	
	
	public CallForProposalSearchCreteria(){
		super();
		this.searchByTemporaryFund = false;
		this.searchByFund = 0;
		this.searchByDesk = 0;
		this.searchBySubjectIds = "";
		this.searchByAllSubjects = false;
		this.searchBySubmissionDateFrom = "";
		this.searchBySubmissionDateTo = "";
		this.searchByAllYear = false;
		this.searchByType = 0;
		this.searchBySearchWords= "";
		this.searchWords="";
		this.searchByCreator=0;
		this.searchDeleted = false;
		this.searchExpired = false;
		this.searchOpen=true;
		this.searchByTargetAudience=0;
		this.searchByCountryIds="";
		this.searchByAllCountries = false;
		this.limit=0;
	}	
	
	public boolean isDefault() {
		return (super.isDefault()
			&& !searchByTemporaryFund
			&& searchByFund == 0
			&& searchByDesk == 0
			&& searchBySubjectIds.isEmpty()
			&& !searchByAllSubjects
			&& searchBySubmissionDateFrom.isEmpty()
			&& searchBySubmissionDateTo.isEmpty()
			&& !searchByAllYear 
			&& searchByType == 0
			&& searchBySearchWords.isEmpty()
			&& searchWords.isEmpty()
			&& searchByCreator == 0
			&& !searchDeleted
			&& !searchExpired
			&& searchOpen
			&& searchByTargetAudience == 0
			&& searchByCountryIds.isEmpty()
			&& !searchByAllCountries);	
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

	public boolean getSearchByAllSubjects() {
		return searchByAllSubjects;
	}

	public void setSearchByAllSubjects(boolean searchByAllSubjects) {
		this.searchByAllSubjects = searchByAllSubjects;
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

	public boolean getSearchByAllYear() {
		return searchByAllYear;
	}

	public void setSearchByAllYear(boolean searchByAllYear) {
		this.searchByAllYear = searchByAllYear;
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

	public boolean getSearchOpen() {
		return searchOpen;
	}

	public void setSearchOpen(boolean searchOpen) {
		this.searchOpen = searchOpen;
	}
	

	public int getSearchByTargetAudience() {
		return searchByTargetAudience;
	}
	
	public void setSearchByTargetAudience(int searchByTargetAudience) {
		this.searchByTargetAudience = searchByTargetAudience;
	}

	public void setSearchByCountryIds(String searchByCountryIds) {
		this.searchByCountryIds = searchByCountryIds;
	}

	public String getSearchByCountryIds() {
		return searchByCountryIds;
	}
	
	public boolean getSearchByAllCountries() {
		return searchByAllCountries;
	}

	public void setSearchByAllCountries(boolean searchByAllCountries) {
		this.searchByAllCountries = searchByAllCountries;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
