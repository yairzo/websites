package huard3.utils;
import java.util.Date;

public class BusysListRecoredParameters{
	private int num;
	private Date creationTime;
	private String username;
	
	public BusysListRecoredParameters(int num,String username){
		this.num=num;
		creationTime = new Date();
		this.username = username;
	}
	
	public int getNum(){
		return num;
	}
	
	public Date getCreationTime(){
		return creationTime;
	}
	
	public String getUsername(){
		return username;
	}
	
	
	
	 
		

}

