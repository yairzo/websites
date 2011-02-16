package huard.iws.bean;

import huard.iws.model.Country;
import huard.iws.model.Institute;
import huard.iws.service.UniverseService;
import huard.iws.util.ApplicationContextProvider;

public class InstituteBean {
	private int id;
	private String name;
	private String city;
	private String state;
	private int countryId;
	private int continentId;

	private Country country;

	public InstituteBean(){
		this.id = 0;
		this.name = "";
		this.city = "";
		this.state = "";
		this.countryId = 0;
		this.continentId = 0;
	}



	public InstituteBean( Institute institute){
		this.id = institute.getId();
		this.name = institute.getName();
		this.city = institute.getCity();
		this.state = institute.getState();
		this.countryId = institute.getCountryId();
		this.continentId = institute.getContinentId();
	}

	public Institute toInstitute(){
		Institute institute = new Institute();
		institute.setId(id);
		institute.setName(name);
		institute.setCity(city);
		institute.setState(state);
		institute.setCountryId(countryId);
		institute.setContinentId(continentId);
		return institute;
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

	public Country getCountry() {
		if (country == null){
			Object obj = ApplicationContextProvider.getContext().getBean("universeService");
			UniverseService universeService = (UniverseService)obj;
			country = universeService.getCountry(this.countryId);
		}


		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}



}
