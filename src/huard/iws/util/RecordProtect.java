package huard.iws.util;

import java.util.List;

public interface RecordProtect {

	public boolean isRecordBusy (String type, int id, String username);

	public void setRecordBusy(String type, int id, String username, Long time);

	public void freeRecord (String type, int id);

	public void freeRecordsByUsername(String username);

	public List<String> getBusyRecords();

}
