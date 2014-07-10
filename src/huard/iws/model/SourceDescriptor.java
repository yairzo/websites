package huard.iws.model;


/**
 * 
 * @author Leon
 *
 */
public class SourceDescriptor {

	private int id;
	private String name;
	private boolean active;
	
	public SourceDescriptor() {
		this.id = 0;
		this.name = "";
		this.active = false;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
