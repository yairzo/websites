package huard3.actions;

import huard3.utils.*;
import java.util.*;


public class LogFileViewer {
	
	private LogRecored [] logFileRecoreds;
	private String ardNum;
	
	public String getArdNum() {
		return ardNum;
	}

	public void setArdNum(String ardNum) {
		this.ardNum = ardNum;
	}

	public LogFileViewer(){
	}
	
	/*public LogRecored [] getLogFile(String username){
		if (logFileRecoreds == null) logFileRecoreds = new LogFileHandler().getLogFile(username);
		if (ardNum==null) return logFileRecoreds;
		else return getLogRecoredsByArdNum (ardNum);
	}*/
	
	public LogRecored [] getLogRecoredsByArdNum (String ardNum){
		List l = new ArrayList();
		for (int i=0; i< logFileRecoreds.length; i++){
			if (logFileRecoreds[i].getArdNum().equals(ardNum)){
				l.add(logFileRecoreds[i]);
			}
		}
		LogRecored [] lr = new LogRecored [l.size()];
		for (int i=0; i<lr.length; i++){
			lr [i] = (LogRecored)l.get(i);
		}
		return lr;		
	}

}
