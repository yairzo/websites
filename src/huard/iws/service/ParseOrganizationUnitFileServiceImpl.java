package huard.iws.service;

import huard.iws.model.OrganizationUnit;
import huard.iws.model.OrganizationUnitAttribution;

import java.io.BufferedReader;
import java.io.FileReader;


public class ParseOrganizationUnitFileServiceImpl implements ParseOrganizationUnitFileService{

public void parseFile(String typeId,String listId,String file){
	
    try{
    	//String strFile = "/home/mop/work/מרכזי מחקר.csv";

    	BufferedReader br = new BufferedReader( new FileReader(file));
    	String strLine = "";
       
    	//read comma separated file line by line
    	while( (strLine = br.readLine()) != null){
    		System.out.println("strLine: " + strLine);
    		OrganizationUnit ou = new OrganizationUnit ();   
    		String[] tokens = strLine.split(";");
    		for (int i = 0; i < tokens.length; i++) {
    			String token = tokens[i];
    			if (i==0)//name of ou
    				ou.setNameHebrew(token);
    			if (i==1){//faculty
    				int faculty=0;
    				try{
    					faculty = facultyService.getFacultyByNameHebrew(token);
    				}
    				catch(Exception e){
    					System.out.println("exception in ParseOrganizationUnitFileServiceImpl: cant find faculty");
    				}
    				finally{
    					ou.setFacultyId(faculty);
    				}
    			}
    			if (i==2)//site
    				ou.setWebsiteUrl(token);
    			if (i==3)//email
    				ou.setEmail(token);
    			if (i==4)//manager
    				ou.setContact(token);
    			//System.out.println("Token #" + i + ": " + token);
    		}
    		ou.setTypeId(new Integer(typeId).intValue());
    		ou.setNameEnglish("");
    		ou.setPhone("");
    		ou.setFax("");
    		ou.setAddress("");

    		int ouid=0;
    		try{//check if exists
    			OrganizationUnit ouInDb = organizationUnitService.getOrganizationUnit(ou.getNameHebrew());
    			ou.setId(ouInDb.getId());
    		}
    		catch(Exception e){// when not found same name
    			ouid = organizationUnitService.insertOrganizationUnit();
    			ou.setId(ouid);
    		}
    		finally{
    			organizationUnitService.updateOrganizationUnit(ou);
    			OrganizationUnitAttribution oua = new OrganizationUnitAttribution();
    			oua.setOrganizationUnitId(ou.getId());
    			oua.setListId(new Integer(listId).intValue());
    			organizationUnitService.insertOrganizationUnitAttribution(oua);
    		}
    	}
    }
    catch(Exception e){
    	System.out.println("Exception while reading csv file: " + e);                  
    }

}
    
private OrganizationUnitService organizationUnitService;
public void setOrganizationUnitService(OrganizationUnitService organizationUnitService) {
	this.organizationUnitService = organizationUnitService;
}
private FacultyService facultyService;
public void setFacultyService(FacultyService facultyService) {
	this.facultyService = facultyService;
}
}
