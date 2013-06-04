package huard.iws.model;

public class Country {
	private int id;
	private String name;
	private int continentId;
	private String nameHebrew;


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
	public int getContinentId() {
		return continentId;
	}
	public void setContinentId(int continentId) {
		this.continentId = continentId;
	}
	public String getNameHebrew() {
		return nameHebrew;
	}
	public void setNameHebrew(String nameHebrew) {
		this.nameHebrew = nameHebrew;
	}

}
