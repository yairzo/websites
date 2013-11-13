
package huard3.utils;
import java.util.*;

public class Ip {
	private int [] fields;
		
	public Ip(String ip){
		fields = new int[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		int i=0;
		while (st.hasMoreTokens()){
			fields[i] = Integer.parseInt(st.nextToken());
			i++;
		}
	}
	
	public long getAsLongValue(){
		long ip = (long)(fields[0]*1e9 + fields[1]*1e6 + fields[2]* 1e3 + fields[3]);
		return ip;
	}

}
