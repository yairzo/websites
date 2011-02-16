package huard.iws.util;

public class RecordParams {
	private String username;
	private Long time;

	public RecordParams(String username, Long time){
		this.username = username;
		this.time = time;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
