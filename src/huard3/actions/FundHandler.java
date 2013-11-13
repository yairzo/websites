package huard3.actions;
import huard3.utils.FundsQuery;
import huard3.utils.*;
import huard3.utils.ProtectRecordsInUse;

public class FundHandler {
	
	private FundsQuery fundsQuery;
	
	private Fund fund;
	private String fundNum;
	private boolean aNewFund;
	private String[] allDeskIds;
	private String[] allDocOwners;
	
	public FundHandler(){
		fundsQuery = new FundsQuery();
	}
	
	
	
	public void setFundNum(String fundNum){
		this.fundNum=fundNum;
	}
	
	public String getFundNum(){
		return this.fundNum;
	}
	
	
	public Fund getFundByFundNum(){
		if(fund==null){
			if (fundNum==null){
				fund = new Fund();
				fund.setFundNum(fundsQuery.getFirstFreeFundNum());
				this.fundNum=""+fund.getFundNum();
				this.aNewFund=true;
			}
			else fund=fundsQuery.getFundByFundNum(this.fundNum);
		}
		return fund;
	}
	
	public String[] getAllDeskIds(){
		if (allDeskIds==null) allDeskIds= fundsQuery.getAllDeskIds();
		return allDeskIds;
	}
	
	public String[] getAllDocOwners(){
		if (allDocOwners==null) allDocOwners= fundsQuery.getAllUsersFirstLetterUpperCased();
		return allDocOwners;
	}
	

	
	public boolean isANewFund(){
		return aNewFund;
	}
	
	public void setFundAsBusy(String username){
		if (fundNum!=null)ProtectRecordsInUse.getProtector().setFundAsBusy(Integer.parseInt(this.fundNum),username);
	}
	
	public boolean isFundBusy(){
		if (fundNum!=null) return ProtectRecordsInUse.getProtector().isFundBusy(Integer.parseInt(this.fundNum));
		else return false;
	}
	
	public boolean isFundStillHoldedByMe(String username){
    	if (fundNum!=null) return ProtectRecordsInUse.getProtector().isFundStillHoldedByMe(Integer.parseInt(this.fundNum),username);
    	else return false;
    }
	
	
	public Worker [] getWorkersByTitle(String title){
		return new WorkersQuery().getWorkersByTitle(title);	
	}

	
	
	

}

