package huard.iws.service;

import huard.iws.util.RecordProtect;

import java.util.Date;
import java.util.List;

public class RecordProtectServiceImpl  implements RecordProtectService{
	//private static final Logger logger = Logger.getLogger(RecordProtectServiceImpl.class);
	private RecordProtect recordProtect;

	public void setRecordProtect(RecordProtect recordProtect) {
		this.recordProtect = recordProtect;
	}

	public boolean isRecordBusy(String type, int id, String username){
		return recordProtect.isRecordBusy(type, id, username);
	}


	public void setRecordBusy(String type, int id, String username){
		recordProtect.setRecordBusy(type, id, username, new Date().getTime());
	}

	public void freeRecord(String type, int id){
		recordProtect.freeRecord(type, id);
	}

	public void log(){
		//TODO: logs
	}

	public List<String> getBusyRecords(){
		return recordProtect.getBusyRecords();
	}

	public void freeRecordsByUsername(String username){
		recordProtect.freeRecordsByUsername(username);
	}
}
