package huard.iws.model;

import java.util.ArrayList;
import java.util.List;

public class AList{
	private int id;
	private String name;
	private String displayName;
	private boolean displayNameAligned;
	private String owner;
	private boolean sendMailToListEnabled;
	private boolean sortEnabled;
	private boolean compound;
	private boolean isPublic;
	private boolean isOpen;
	private int listTypeId;
	private List<AList> sublists;
	private String preface;
	private String footer;
	private long lastUpdate;
	private int personListType;
	
	public static final int PERSONS_LIST_TYPE_ID = 1;
	public static final int ORGANIZATION_UNIT_LIST_TYPE_ID = 2;

	private int location;

	public AList(){
		this.id = 0;
		this.name = "";
		this.displayName = "";
		this.displayNameAligned = false;
		this.owner = "";
		this.sendMailToListEnabled = false;
		this.sortEnabled = false;
		this.compound = false;
		this.sublists = new ArrayList<AList>();
		this.location = 0;
		this.preface = "";
		this.footer = "";
		this.lastUpdate = 0;
		this.personListType =0;
	}

	public AList(AList aList){
		this.id = aList.getId();
		this.name = aList.getName();
		this.displayName = aList.getDisplayName();
		this.displayNameAligned = aList.isDisplayNameAligned();
		this.owner = aList.getOwner();
		this.sendMailToListEnabled = aList.isSendMailToListEnabled();
		this.sortEnabled = aList.isSortEnabled();
		this.compound = aList.isCompound();
		this.sublists = aList.getSublists();
		this.location = aList.getLocation();
		this.preface = aList.getPreface();
		this.footer = aList.getFooter();
		this.lastUpdate = aList.getLastUpdate();
		this.personListType = aList.getPersonListType();
	}

	public boolean isSortEnabled() {
		return sortEnabled;
	}
	public void setSortEnabled(boolean sortEnabled) {
		this.sortEnabled = sortEnabled;
	}
	public boolean isSendMailToListEnabled() {
		return sendMailToListEnabled;
	}
	public void setSendMailToListEnabled(boolean sendMailToListEnabled) {
		this.sendMailToListEnabled = sendMailToListEnabled;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isDisplayNameAligned() {
		return displayNameAligned;
	}

	public void setDisplayNameAligned(boolean displayNameAligned) {
		this.displayNameAligned = displayNameAligned;
	};

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public int getListTypeId() {
		return listTypeId;
	}

	public void setListTypeId(int listTypeId) {
		this.listTypeId = listTypeId;
	}

	public boolean isCompound() {
		return compound;
	}

	public void setCompound(boolean compound) {
		this.compound = compound;
	}

	public List<AList> getSublists(){
		return sublists;
	}

	public void setSublists(List <AList> sublists){
		this.sublists = sublists;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getPreface() {
		return preface;
	}

	public void setPreface(String preface) {
		this.preface = preface;
	}
	
	public int getPersonListType() {
		return personListType;
	}

	public void setPersonListType(int personListType) {
		this.personListType = personListType;
	}
}
