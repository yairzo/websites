package huard.iws.model;

import java.util.List;

public class FundInDay{
	String fundShortName;
	List<CallForProposal> callForProposals;
	
	public String getFundShortName(){
		return fundShortName;
	}
	public void setFundShortName(String fundShortName){
		this.fundShortName= fundShortName;
	}
	public String getTrimmedName(){
		String trimmedName =  fundShortName.substring(0, Math.min(fundShortName.length(), 10));
		if (fundShortName.length() > 10)
			trimmedName += "...";
		return trimmedName;
	}	
	public List<CallForProposal> getCallForProposals() {
		return callForProposals;
	}

	public void setCallForProposals(List<CallForProposal> callForProposals) {
		this.callForProposals = callForProposals;
	}
}



