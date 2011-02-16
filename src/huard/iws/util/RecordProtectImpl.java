package huard.iws.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RecordProtectImpl  implements RecordProtect{
	Map<String,RecordParams> busyRecords;
	final long EXPIRY_TIME = 2100000L; // 35 minutes
	//private static final Logger logger = Logger.getLogger(RecordProtectImpl.class);

	public RecordProtectImpl(){
		busyRecords = new HashMap<String,RecordParams>();
	}

	public boolean isRecordBusy (String type, int id, String username){
		logBusyRecords();
		checkForExpiry();
		String key = type+id;
		return (busyRecords.containsKey(key) &&
				! ((RecordParams)busyRecords.get(key)).getUsername().equals(username));
	}

	public void setRecordBusy(String type, int id, String username, Long time){
		String key = type+id;
		RecordParams recordParams = new RecordParams(username, time);
		busyRecords.put(key, recordParams);
	}

	public void freeRecord (String type, int id){
		String key = type+id;
		busyRecords.remove(key);
	}

	public void checkForExpiry(){
		Iterator it = busyRecords.entrySet().iterator();
		long now = new Date().getTime();
		while (it.hasNext()) {
		     Map.Entry pairs = (Map.Entry)it.next();
		     RecordParams recordParams = (RecordParams)pairs.getValue();
		     if (recordParams.getTime() + EXPIRY_TIME < now){
		       	it.remove();
		      }
		}
	}

	public void logBusyRecords(){
		Iterator it = busyRecords.entrySet().iterator();
		while (it.hasNext()) {
		     //Map.Entry pairs = (Map.Entry)it.next();
		 }
	}

	public void freeRecordsByUsername(String username){
		Iterator it = busyRecords.entrySet().iterator();
		while (it.hasNext()) {
		     Map.Entry pairs = (Map.Entry)it.next();
		     RecordParams recordParams = (RecordParams)pairs.getValue();
		     if (recordParams.getUsername().equals(username)){
		       	it.remove();
		      }
		}
	}

	public List<String> getBusyRecords(){
		Iterator it = busyRecords.entrySet().iterator();
		List<String> l = new ArrayList<String>();
		while (it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			RecordParams rp = (RecordParams) pairs.getValue();
			l.add(pairs.getKey()+" "+rp.getUsername()+" "+SimpleDateFormat.getDateTimeInstance().format(new Date(rp.getTime())));
		}
		return l;
	}





}
