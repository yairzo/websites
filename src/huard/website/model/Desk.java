package huard.website.model;

import huard.website.baseDb.*;

public class Desk {

	private String id;
	private String englishName;
	private String hebrewName;
	private int englishListId;
	private int hebrewListId;
	private Worker [] workersHebrewOrdered;
	private Worker [] workersEnglishOrdered;
	private DbHandler dbHandler;

	public Desk(){

	}


	public Desk(String id){
		dbHandler = new DbHandler();
		this.id = id;
		englishName = dbHandler.getDeskEnglishName(id);
		hebrewName = dbHandler.getDeskHebrewName(id);
		workersHebrewOrdered = dbHandler.getWorkersByDeskId(id,true);
		workersEnglishOrdered = dbHandler.getWorkersByDeskId(id,false);
	}

	public String getEnglishName() {
		return englishName;
	}

	public String getHebrewName() {
		return hebrewName;
	}

	public String getName(boolean heb){
		return heb ? hebrewName : englishName;
	}

	public String getId() {
		return id;
	}

	public Worker[] getWorkersHebrewOrdered() {
		return workersHebrewOrdered;
	}
	public Worker[] getWorkersEnglishOrdered() {
		return workersEnglishOrdered;
	}

	public Worker [] getOrderedWorkers(boolean heb){
		return heb ? workersHebrewOrdered : workersEnglishOrdered;
	}

	public int getEnglishListId() {
		return englishListId;
	}


	public void setEnglishListId(int englishListId) {
		this.englishListId = englishListId;
	}


	public int getHebrewListId() {
		return hebrewListId;
	}


	public void setHebrewListId(int hebrewListId) {
		this.hebrewListId = hebrewListId;
	}


	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public void setHebrewName(String hebrewName) {
		this.hebrewName = hebrewName;
	}

}
