package huard.website.util;

public class WebsiteManager {

	private Runtime runtime;

	public WebsiteManager(){
		runtime = Runtime.getRuntime();
	}

	public String getFreeMemory(){
		return ""+runtime.freeMemory();
	}

	public String getMaxMemory(){
		return ""+runtime.maxMemory();
	}

	public String getUsedMemory(){
		return ""+(runtime.totalMemory() - runtime.freeMemory());
	}

	public String getTotalMemory(){
		return ""+runtime.totalMemory();
	}

}
