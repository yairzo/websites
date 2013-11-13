package huard3.actions;
import huard3.utils.FundsUpdate;
import huard3.utils.ProtectRecordsInUse;
import huard3.utils.WordsTokenizer;

import java.util.List;


public class FundValidator {
	private FundsUpdate fundsUpdate;
	private Fund fund;
	private boolean detailsAreAllright;
	private String originalFundNum;
	private boolean aNewFund;
	
	
	public FundValidator(){
		fundsUpdate = new FundsUpdate();
		fund = new Fund();
		detailsAreAllright=true;
		aNewFund=false;
		
	}
	
	
	public void setOriginalFundNum(String originalFundNum){
		this.originalFundNum=originalFundNum;
		
	}
	
	public String getOriginalFundNum(){
		return this.originalFundNum;
	}
	
	
	
	
	public void setFundNum(String fundNum){
		fund.setFundNum(Integer.parseInt(fundNum));		
		
	}
	
	
	public String getFundNum(){
		return ""+fund.getFundNum();
			

	}
	
	
	public void setShortName(String fundShortName){
		fund.setShortName(fundShortName);
	}
	
	public String getShortName(){
		if(!fund.getShortName().equals("")) return fund.getShortName();
		else{
			detailsAreAllright=false;
			return "No short name was entered";
		}
	}
	
	public void setFullName(String fundFullName){
		fund.setFullName(fundFullName);
	}
	
	public String getFullName(){
		if(!fund.getFullName().equals("")) return fund.getFullName();
		else{
			detailsAreAllright=false;
			return "No full name was entered";
		}
	}
	
	/*public void setHasForms(boolean hasForms){
		fund.setHasForms(hasForms);
	}
	
	public boolean isHasForms(){
		return fund.isHasForms();
	}*/
	
	
		
	public void setWebAddress(String webAddress){
		fund.setWebAddress(webAddress);
	}
	
	public String getWebAddress(){
		if(!fund.getWebAddress().equals("")) return fund.getWebAddress();
		else{
			detailsAreAllright=false;
			return "No web address was entered";
		}
	}
	public void setPhoneNum(String phoneNum){
		fund.setPhoneNum(phoneNum);
	}
	
	public String getPhoneNum(){
		if(!fund.getPhoneNum().equals("")) return fund.getPhoneNum();
		else{
			detailsAreAllright=false;
			return "No phone number was entered";
		}
	}
	
	public void setFax(String fax){
		fund.setFax(fax);
	}
	
	public String getFax(){
		if(!fund.getFax().equals("")) return fund.getFax();
		else{
			detailsAreAllright=false;
			return "No fax number was entered";
		}
	}
	
	public void setContact(String contact){
		fund.setContact(contact);
	}
	
	public String getContact(){
		if(!fund.getContact().equals("")) return fund.getContact();
		else{
			detailsAreAllright=false;
			return "No contact was entered";
		}
	}
	
	
	public void setMailAddress(String mailAddress){
		fund.setMailAddress(mailAddress);
	}
	
	public String getMailAddress(){
		if(!fund.getMailAddress().equals("")) return fund.getMailAddress();
		else{
			detailsAreAllright=false;
			return "No mail address was entered";
		}
	}
	
	public void setDeskId(String deskId){
		fund.setDeskId(deskId);
	}
	
	public String getDeskId(){
		if(!(fund.getDeskId().equals("")))return fund.getDeskId();
		else{
			detailsAreAllright=false;
			return "No Desk Id Was Entered";
		}
		
	}
	
	public void setDocOwner(String docOwner){
		fund.setDocOwner(docOwner);
	}
	
	public String getDocOwner(){
		if(!(fund.getDocOwner().equals("")))return fund.getDocOwner();
		else{
			detailsAreAllright=false;
			return "No Document Owner Was Entered";
		}
	}
	
	public void setKeywords(String keywords){
		fund.setKeywords(keywords);
	}
	
	public String getKeywords(){
		return fund.getKeywords();
	}
	
	
	public String[] getKeywordsArray(){
       if(!(fund.getKeywords().equals(""))){
       	WordsTokenizer wt= new WordsTokenizer(",");
		List list = wt.getSubstringsList(fund.getKeywords()) ;
		String[] keywords = new String[list.size()];
		for ( int i = 0 ; i < list.size() ; ++i ){
			keywords[i]=""+list.get( i );
		}
		
		return keywords;
       }
       else {
       	detailsAreAllright=false;
       	String[] errStr = new String[1];
       	errStr[0]="No keywords where entered, please enter few";
       	return errStr;
       }       	
	}
	
	public void setANewFund(String aNewFund){
		if(aNewFund!=null){
			this.aNewFund=true;
		}
		
	}
	
	public boolean isANewFund(){
		return this.aNewFund;
	}
	
	public void setBudgetOfficer(String budgetOfficer){
		fund.setBudgetOfficer(budgetOfficer);
	}
	
	public String getBudgetOfficer(){
		return fund.getBudgetOfficer();
	}
	
	public void setFinancialReporter(String financialReporter){
		fund.setFinancialReporter(financialReporter);
	}
	
	public String getFinancialReporter(){
		return fund.getFinancialReporter();
	}
	
	
	
	public boolean isDetailsAreAllright(){
    	return detailsAreAllright;
    }
    
    public boolean updateFundDetailsInDatabase(){
    	return fundsUpdate.updateFundDetailsInDatabase(fund,aNewFund);
    }
    
    public boolean insertFundKeywordsIntoDatabase(){
    	return fundsUpdate.insertFundKeywordsIntoDatabase(fund,this.getKeywordsArray());
    }
    
    public boolean isFundStillHoldedByMe(String username){
    	return ProtectRecordsInUse.getProtector().isFundStillHoldedByMe(fund.getFundNum(),username);
    }
    
    public void releaseFund(){
		ProtectRecordsInUse.getProtector().releaseFund(fund.getFundNum());
	}
    
       
		
		

}

