package huard3.actions;
import huard3.utils.*;


public class FundDeleter {
	private String fundNum;
	private String [] fundsArdNums;
	private FundsUpdate fundsUpdate;
	
	

	public FundDeleter(){
		fundsUpdate = new FundsUpdate();
	}
	
	public void setFundNum(String fundNum){
		this.fundNum=fundNum;
	}
	
	public String getFundNum(){
		return this.fundNum;
	}
	
	
	public boolean deleteFund(){
		if (fundsArdNums==null) fundsArdNums = new FundsQuery().getFundsInfoPagesArdNums(this.fundNum);
		if ( fundsArdNums.length ==0){
			fundsUpdate.deleteFundByFundNum(this.fundNum);
			return true;
			
		}
		else return false;
	}
	public String[] getFundsArdNums(){
		return fundsArdNums;
	}



}

