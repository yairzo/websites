package huard3.utils;
import huard3.utils.io.AsciiFileReader;
import huard3.utils.io.AsciiFileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PageHandler {
       
       //private int[] allArdNumsArray;
       
       
       
       public PageHandler(){
       	
       }
       
             	  	
        
        public void printFile(String src){
        	try{
        		BufferedReader htmlBR = new BufferedReader(new FileReader(src));
        		String line;
        		while((line=htmlBR.readLine())!=null){
        			System.out.println(line);
        		}
        		htmlBR.close();
        		
        	}
        	catch(IOException ioe){
        		
        	}
        	
        }
        
        /* old verision of send html to site that cuts everything outside th body tags
        
        public boolean sendHtmlToSite(String src, String dest){
        	try{      
           		BufferedReader htmlBR = new BufferedReader(new AsciiFileReader(src));
         		BufferedWriter htmlBW = new BufferedWriter(new AsciiFileWriter(dest));
        		String line;
        		boolean copyThisLine=false;
        		        		
        		while ((line=htmlBR.readLine())!=null){
        			            	    	
        	        	if(line.indexOf("</BODY>")!=-1||line.indexOf("</Body>")!=-1||line.indexOf("</body>")!=-1){
        	    	    	copyThisLine=false;
        	        	}
        	    	
        	        	if (copyThisLine) {
        	        		htmlBW.write(line);
        	    	    	htmlBW.newLine();
        	    	    	System.out.println(line);
        	        	}
        	        	
        	        	if((line.indexOf("<BODY>")!=-1)||(line.indexOf("<Body>")!=-1)||(line.indexOf("<body>")!=-1)){
        	    	    	copyThisLine=true;
        	        	}
        	  	}
        	  	htmlBR.close();
        	  	htmlBW.close();
        	  	deleteFile(src);
        	  	return true;
        	  	
        	}
        	catch(IOException ioe){
        		System.out.println("Couldn't write to file"+ioe);
        		return false;
            }
        }
        */
        
	public boolean sendHtmlToSite(String src, String dest){
		try{      
			BufferedReader htmlBR = new BufferedReader(new AsciiFileReader(src));
			BufferedWriter htmlBW = new BufferedWriter(new AsciiFileWriter(dest));
			String line;
			while ((line=htmlBR.readLine())!=null){
        		htmlBW.write(line);
				htmlBW.newLine();
			}	
			htmlBR.close();
			htmlBW.close();
			deleteFile(src);
			return true;
        	}
		catch(IOException ioe){
			System.out.println("Couldn't write to file"+ioe);
			return false;
		}
	}	
        		
        
       
        
        public boolean deleteFile(String src){
        
        		File fileToDelete = new File(src);
            	return fileToDelete.delete();
            
        }
            
        public boolean moveFile (String src, String dest){
        	try{
        		BufferedReader htmlBR = new BufferedReader(new AsciiFileReader(src));
         		BufferedWriter htmlBW = new BufferedWriter(new AsciiFileWriter(dest));
        		String line;
        		while((line=htmlBR.readLine())!=null){
        			htmlBW.write(line);
        			htmlBW.newLine();
        		}
        		htmlBR.close();
        		htmlBW.close();
        		deleteFile(src);
        		return true;
        	}
        	catch(IOException ioe){
        		System.out.println(ioe);
        		return false;
        		  		
        	}
      }
      
	public boolean copyFile(String src, String dest){
		try{
			BufferedReader htmlBR = new BufferedReader(new AsciiFileReader(src));
			BufferedWriter htmlBW = new BufferedWriter(new AsciiFileWriter(dest));
			String line;
			while((line=htmlBR.readLine())!=null){
				htmlBW.write(line);
				htmlBW.newLine();
			}
			htmlBR.close();
			htmlBW.close();
			return true;
		}
		catch(IOException ioe){
			System.out.println(ioe);
			return false;
    	}
	 }
      			
}
