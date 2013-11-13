package huard3.utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProtectRecordsInUse {
	private List busyInfoPages;
	private List busyFunds;
	private List busyPubPages;
	
	
	private static ProtectRecordsInUse protector = null;
	
	private ProtectRecordsInUse (){
		busyInfoPages = new ArrayList();
		busyFunds = new ArrayList();
		busyPubPages = new ArrayList();
	}
	
	public static synchronized ProtectRecordsInUse getProtector(){
		if (protector==null) protector = new ProtectRecordsInUse();
		return protector;
	}
	
	public synchronized boolean isInfoPageBusy(int ardNum){
		for (int i=0;i<busyInfoPages.size();i++){
			if (((BusysListRecoredParameters)busyInfoPages.get(i)).getNum()==ardNum) return true;
		}
		return false;
	}
	
	public synchronized boolean isFundBusy(int fundNum){
		for (int i=0;i<busyFunds.size();i++){
			if (((BusysListRecoredParameters)busyFunds.get(i)).getNum()==fundNum) return true;
		}
		return false;
	}
	
	public synchronized boolean isPubPageBusy(int ardNum){
		for (int i=0;i<busyPubPages.size();i++){
			if (((BusysListRecoredParameters)busyPubPages.get(i)).getNum()==ardNum) return true;
		}
		return false;
	}
	
	public synchronized void setInfoPageAsBusy (int ardNum, String username){
		BusysListRecoredParameters recordParam = new BusysListRecoredParameters(ardNum,username);
		busyInfoPages.add(recordParam);
	}
	
	public synchronized void setFundAsBusy (int fundNum, String username){
		BusysListRecoredParameters recordParam = new BusysListRecoredParameters(fundNum,username);
		busyFunds.add(recordParam);
	}
	
	public synchronized void setPubPageAsBusy (int ardNum, String username){
		BusysListRecoredParameters recordParam = new BusysListRecoredParameters(ardNum,username);
		busyPubPages.add(recordParam);
	}
	
	public synchronized void releaseInfoPage (int ardNum){
		for (int i=0;i<busyInfoPages.size();i++){
			if (((BusysListRecoredParameters)busyInfoPages.get(i)).getNum()==ardNum) busyInfoPages.remove(i);
		}
	}
	
	public synchronized void releaseFund (int fundNum){
		for (int i=0;i<busyFunds.size();i++){
			if (((BusysListRecoredParameters)busyFunds.get(i)).getNum()==fundNum) busyFunds.remove(i);
		}
	}
	
	public synchronized void releasePubPage (int ardNum){
		for (int i=0;i<busyPubPages.size();i++){
			if (((BusysListRecoredParameters)busyPubPages.get(i)).getNum()==ardNum) busyPubPages.remove(i);
		}
	}
	
	public synchronized void releaseAllInfoPages(){
		busyInfoPages.clear();		
		
	}
	
	public synchronized void releaseAllPubPages(){
		busyPubPages.clear();		
	}
	
	public void releaseInfoPagesHoldedToLong(){
		Date now = new Date();
		for (int i=0;i<busyInfoPages.size();i++){
			if (now.getTime()>((BusysListRecoredParameters)busyInfoPages.get(i)).getCreationTime().getTime()+3600000) releaseInfoPage(((BusysListRecoredParameters)busyInfoPages.get(i)).getNum());
		}
	}
	
	public void releaseFundsHoldedToLong(){
		Date now = new Date();
		for (int i=0;i<busyFunds.size();i++){
			if (now.getTime()>((BusysListRecoredParameters)busyFunds.get(i)).getCreationTime().getTime()+3600000) releaseFund(((BusysListRecoredParameters)busyFunds.get(i)).getNum());
		}
	}
	
	public void releasePubPagesHoldedToLong(){
		Date now = new Date();
		for (int i=0;i<busyPubPages.size();i++){
			if (now.getTime()>((BusysListRecoredParameters)busyPubPages.get(i)).getCreationTime().getTime()+3600000) releasePubPage(((BusysListRecoredParameters)busyPubPages.get(i)).getNum());
		}
	}
	public boolean isInfoPageStillHoldedByMe(int ardNum, String username){
		for (int i=0;i<busyInfoPages.size();i++){
			if ((((BusysListRecoredParameters)busyInfoPages.get(i)).getNum()==ardNum)&&(((BusysListRecoredParameters)busyInfoPages.get(i)).getUsername().equals(username))) return true;
		}
		return false;
	}
	
	public boolean isFundStillHoldedByMe(int fundNum, String username){
		for (int i=0;i<busyFunds.size();i++){
			if ((((BusysListRecoredParameters)busyFunds.get(i)).getNum()==fundNum)&&(((BusysListRecoredParameters)busyFunds.get(i)).getUsername().equals(username))) return true;
		}
		return false;
	}
	
	
	public boolean isPubPageStillHoldedByMe(int ardNum, String username){
		for (int i=0;i<busyPubPages.size();i++){
			if ((((BusysListRecoredParameters)busyPubPages.get(i)).getNum()==ardNum)&&(((BusysListRecoredParameters)busyPubPages.get(i)).getUsername().equals(username))) return true;
		}
		return false;
	}
	
	public String getUserOccupyingInfoPage(int ardNum){
		BusysListRecoredParameters recoredParameters;
		for (int i=0;i<busyInfoPages.size();i++){
			if ( (recoredParameters=(BusysListRecoredParameters)busyInfoPages.get(i)).getNum()==ardNum) return recoredParameters.getUsername();
		}
		return "Unknown";
		
	}
	
	public String getUserOccupyingPubPage(int ardNum){
		BusysListRecoredParameters recoredParameters;
		for (int i=0;i<busyPubPages.size();i++){
			if ( (recoredParameters=(BusysListRecoredParameters)busyPubPages.get(i)).getNum()==ardNum) return recoredParameters.getUsername();
		}
		return "Unknown";
		
		}
	public BusysListRecoredParameters [] getBusyInfoPagesArray(){
		BusysListRecoredParameters [] busyInfoPagesArray = new BusysListRecoredParameters[busyInfoPages.size()];
		for (int i=0; i<busyInfoPagesArray.length; i++){
			busyInfoPagesArray[i] = (BusysListRecoredParameters)busyInfoPages.get(i);
		}
		return busyInfoPagesArray;
		
	}
	
	public BusysListRecoredParameters [] getBusyPubPagesArray(){
			BusysListRecoredParameters [] busyPubPagesArray = new BusysListRecoredParameters[busyPubPages.size()];
			for (int i=0; i<busyPubPagesArray.length; i++){
				busyPubPagesArray[i] = (BusysListRecoredParameters)busyPubPages.get(i);
			}
			return busyPubPagesArray;
		
		}
	
	
	
	
	public static void main(String[] args){
		
		ProtectRecordsInUse.getProtector().releaseFundsHoldedToLong();
	}
	
		
	
		     

}

