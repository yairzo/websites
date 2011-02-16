package huard.iws.service;

import java.util.List;

public interface RecordProtectService {

	public boolean isRecordBusy(String type, int id, String username);

	public void setRecordBusy(String type, int id, String username);

	public void freeRecord(String type, int id);

	public void log();

	public void freeRecordsByUsername(String username);

	public List<String> getBusyRecords();

}
