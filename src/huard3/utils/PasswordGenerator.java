package huard3.utils;


public class PasswordGenerator {
	private int length;
	
	public PasswordGenerator(int length){
		this.length = length;
	}
	
	public String getPassword(){
		StringBuffer password = new StringBuffer();
		for (int i=0; i<length; i++){
			double randomNum;
			char c=' ';
			while (!  Character.isLetterOrDigit(c)){ 
				randomNum = Math.random();
				c = ((char)(48+74*randomNum));
			}
			password.append(c);
		}
		return password.toString();
	}

	

}
