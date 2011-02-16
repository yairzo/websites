package huard.iws.model;



public class Institute {
	private int id;
	private String name;
	private String city;
	private String state;
	private int countryId;
	private int continentId;

	public Institute(){
		this.id = 0;
		this.name = "";
		this.city = "";
		this.state = "";
		this.countryId = 0;
		this.continentId = 0;
	}


	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getContinentId() {
		return continentId;
	}
	public void setContinentId(int continentId) {
		this.continentId = continentId;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

}
