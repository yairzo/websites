package huard3.utils;
import java.util.*;
import java.text.*;


public class BusysListViewer {
	private BusysListRecoredParameters [] busyInfoPagesArray;
	
	public BusysListRecoredParameters [] getBusyInfoPagesArray(){
		if (busyInfoPagesArray==null) busyInfoPagesArray = ProtectRecordsInUse.getProtector().getBusyInfoPagesArray();
		return busyInfoPagesArray;
	}
	public String getFormatTime(Date date){
		return DateFormat.getTimeInstance(1,Locale.FRANCE).format(date);
	}

}
