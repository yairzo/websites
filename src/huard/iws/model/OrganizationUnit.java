package huard.iws.model;

public class OrganizationUnit {
	private int id;
	private int typeId;
	private String nameHebrew;
	private String nameEnglish;
	private String email;
	private String websiteUrl;
	private String phone;
	private String fax;
	private String address;
	private String contact;
	private int placeInList;
	private int facultyId;
	private String imageUrl;
	private String shortName;
	private String description;
	private String descriptionSummary;


	public OrganizationUnit(){
		this.imageUrl="";
		this.shortName="";
		this.description="";
		this.descriptionSummary="";
		
	}


	public void prepareForView(){
		//if (nameHebrew!=null) nameHebrew = nameHebrew.replaceAll("\"", "&quot;");
		if (nameEnglish!=null) nameEnglish = nameEnglish.replaceAll("\"", "&quot;");
		if (address!=null) address = address.replaceAll("\"", "&quot;");
	}


	public int getPlaceInList() {
		return placeInList;
	}
	public void setPlaceInList(int placeInList) {
		this.placeInList = placeInList;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameEnglish() {
		return nameEnglish;
	}
	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}
	public String getNameHebrew() {
		return nameHebrew;
	}
	public void setNameHebrew(String nameHebrew) {
		this.nameHebrew = nameHebrew;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public String getWebsiteUrl() {
		return websiteUrl;
	}


	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public int getFacultyId() {
		return facultyId;
	}


	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionSummary() {
		return descriptionSummary;
	}
	public void setDescriptionSummary(String descriptionSummary) {
		this.descriptionSummary = descriptionSummary;
	}

	public class OrganizationUnitType{
		private int id;
		private String nameHebrew;
		private String nameEnglish;

		public OrganizationUnitType(){
			this.id = 0;
			this.nameHebrew = "";
			this.nameEnglish ="";
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNameEnglish() {
			return nameEnglish;
		}
		public void setNameEnglish(String nameEnglish) {
			this.nameEnglish = nameEnglish;
		}
		public String getNameHebrew() {
			return nameHebrew;
		}
		public void setNameHebrew(String nameHebrew) {
			this.nameHebrew = nameHebrew;
		}

	}
}
