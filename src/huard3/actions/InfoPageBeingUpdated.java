package huard3.actions;

import huard3.utils.ProtectRecordsInUse;

public class InfoPageBeingUpdated {
	private int ardNum;
	
	public InfoPageBeingUpdated(){
		ardNum=0;
			
	}
	

    public void setInfoPageBeingUpdated(String ardNum){
    	if(this.ardNum==0)this.ardNum=Integer.parseInt(ardNum);
    }
    
    protected void finalize(){
    	ProtectRecordsInUse.getProtector().releaseInfoPage(this.ardNum);
    }
         
}

