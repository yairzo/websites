package huard3.actions;
import huard3.utils.FundsQuery;
import huard3.utils.ProtectRecordsInUse;

public class FundsListQuery {
	private Fund[] fundsSortedByFundNum;
	private Fund[] fundsSortedByFundName;
	private FundsQuery fundsQuery;
	
	public FundsListQuery(){
		fundsQuery = new FundsQuery();
	}
	
	public Fund[] getFundsSortedByFundNum(){
		if (fundsSortedByFundNum==null) fundsSortedByFundNum = fundsQuery.getFundsSortedByFundNum();
		return fundsSortedByFundNum;
	}
	
	public Fund[] getFundsSortedByFundName(){
		if (fundsSortedByFundName==null) fundsSortedByFundName = fundsQuery.getFundsSortedByFullName();
		return fundsSortedByFundName;
	}
	
	public boolean isFundBusy(int fundNum){
		return ProtectRecordsInUse.getProtector().isFundBusy(fundNum);		
	}
	
	public void releaseFundsHoldedToLong(){
		ProtectRecordsInUse.getProtector().releaseFundsHoldedToLong();
	}
	
	public static void main(String[] args){
		FundsListQuery flq = new FundsListQuery();
		System.out.println(flq.getFundsSortedByFundName()[0]);
	}
	
	public boolean isFundStillHoldedByMe(int fundNum,String username){
    	return ProtectRecordsInUse.getProtector().isFundStillHoldedByMe(fundNum,username);
    	
    }
	
	
	
	

}

