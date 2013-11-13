package huard3.actions;
import huard3.utils.*;
import java.io.*;
import java.util.*;


public class TabledInfoPageHandler extends InfoPageHandler {
	private TabledInfoPage tabledInfoPage;
	private String [] filesArray;

	
	
	
	public TabledInfoPageHandler(){
		super();
	}
	
	public TabledInfoPage getTabledInfoPageByArdNum(){
		if (tabledInfoPage==null){
			 if (!isNewRecored()) tabledInfoPage = infoPagesQuery.getTabledInfoPageDetailsByArdNum(ardNum);
			 else tabledInfoPage = new TabledInfoPage( infoPagesQuery.getInfoPageDetailsByArdNum(ardNum));
		}
		if (Utils.isHebrew(tabledInfoPage.getTitle())) tabledInfoPage = replaceHtmlTagsWithHebrewTags(tabledInfoPage);
		return tabledInfoPage;
	}
	
	public String [] getFundsFormsFileNames(){
		if (filesArray == null){
			List filesList = new ArrayList();
			String [] envp = new String[1];
			envp [0] = "USER=root";
			try{
				
				
				String [] commands  = new String [] {"bash", "-c", "/bin/ls /var/ftp/pub/" + tabledInfoPage.getFundNum()+  
				 		">" + Utils.getHomeDirPath() +"/junk/" + tabledInfoPage.getFundNum() + "FormsList.txt"};
				
				Utils.executeCommand(commands,envp);
				BufferedReader br = new BufferedReader(new FileReader(Utils.getHomeDirPath() +"/junk/" + tabledInfoPage.getFundNum() + "FormsList.txt"));
				String line;
				while ((line = br.readLine())!=null){
					filesList.add(line);
				}
				br.close();
			}
			catch (IOException ioe){
				System.out.println(ioe);
				String [] sa = new String[1];
				sa[0]="";
				return sa;
			}
		
			filesArray = new String [filesList.size()];
			for (int i=0; i < filesArray.length; i++){
				filesArray[i] = (String)filesList.get(i);
			}
			
		}
		return filesArray;
    	
	}
	
	public boolean isNewRecored(){
		return !infoPagesQuery.isRecoredExistsInTable(ardNum, "TabledInfoPages");
	}
	
	public boolean isHebrewDoc(){
		return Utils.isHebrew(getTabledInfoPageByArdNum().getTitle());
	}
	
	public String handleHebrew(String str){
		return Utils.moveHebrewCharsFromAsciiToHebrewCharset(str);
	}

	
	public TabledInfoPage replaceHtmlTagsWithHebrewTags(TabledInfoPage tabledInfoPage){
		tabledInfoPage.setSubDateDetails(getHtmlWithHebrewTags(tabledInfoPage.getSubDateDetails()));
		tabledInfoPage.setDeskAndContact(getHtmlWithHebrewTags(tabledInfoPage.getDeskAndContact()));
		tabledInfoPage.setForms(getHtmlWithHebrewTags(tabledInfoPage.getForms()));
		tabledInfoPage.setDescription(getHtmlWithHebrewTags(tabledInfoPage.getDescription()));
		tabledInfoPage.setMaxFundingPeriod(getHtmlWithHebrewTags(tabledInfoPage.getMaxFundingPeriod()));
		tabledInfoPage.setAmountOfGrant(getHtmlWithHebrewTags(tabledInfoPage.getAmountOfGrant()));
		tabledInfoPage.setEligibilityRequirements(getHtmlWithHebrewTags(tabledInfoPage.getEligibilityRequirements()));
		tabledInfoPage.setActivityLocation(getHtmlWithHebrewTags(tabledInfoPage.getActivityLocation()));
		tabledInfoPage.setPossibleCollaboration(getHtmlWithHebrewTags(tabledInfoPage.getPossibleCollaboration()));
		tabledInfoPage.setBudgetDetails(getHtmlWithHebrewTags(tabledInfoPage.getBudgetDetails()));
		tabledInfoPage.setAdditionalInformation(getHtmlWithHebrewTags(tabledInfoPage.getAdditionalInformation()));		
		return tabledInfoPage;
			
	}
	
	
	public String getHtmlWithHebrewTags(String html){
				char [] charArray = html.toCharArray();
				boolean insideTag = false;
				StringBuffer fixedTagsText = new StringBuffer();
				for (int i=0; i< charArray.length; i++){
					if (charArray[i]=='<') insideTag = true;
					if (charArray[i]=='>') insideTag = false;
					if (insideTag==true){
						if (charArray[i]=='h') charArray[i]=1499;
						else if (charArray[i]=='p') charArray[i]=1508;
						else if (charArray[i]=='b' && (int)charArray[i+1]=='r') {
							charArray[i] = 1512;
							charArray[i+1] = 1513;					
						}
						else if (charArray[i]=='u' && charArray[i+1]=='l') {
							charArray[i] = 1512;
							charArray[i+1] = 1512;
						} 
						else if (charArray[i]=='o' && charArray[i+1]=='l') {
							charArray[i] = 1512;
							charArray[i+1] = 1502;
						}
						else if (charArray[i]=='l' && charArray[i+1]=='i') {
							charArray[i] = 1513;
							charArray[i+1] = 1493;
						} 
						else if (charArray[i]=='b')  {
							charArray[i] = 1491;
						} 
						else if (charArray[i]=='u') {
							charArray[i] = 1511;
						} 
					}
					fixedTagsText.append(charArray[i]);	
				}
				return fixedTagsText.toString();
			}
}
	
	

	
	

	