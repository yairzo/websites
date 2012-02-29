package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PageWordsIndexerDao;
import huard.iws.model.TextualPage;
import huard.iws.util.WordsTokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PageTextualWordsIndexerServiceImpl implements PageTextualWordsIndexerService{
	
	private List<TextualPage> textualPages;

	private final long runsInterval = 36000000L;
	

	public void indexTextualPages(boolean init){
		long runsInterval;
		if (init) 
			runsInterval = new Date().getTime();
		else 
			runsInterval = this.runsInterval;
		System.out.println(runsInterval);
		if (textualPages==null) 
			textualPages = pageWordsIndexerDao.getLatelyUpdatedPubPages(runsInterval,configurationService.getConfigurationString("websiteDb"));
		
		if (textualPages.size()>0) 
			pageWordsIndexerDao.deleteLatelyUpdatedPubPagesFromIndexTable(textualPages, init,configurationService.getConfigurationString("websiteDb"));
		
		System.out.println("PubPagesIndexer Indexing "+textualPages.size()+" pages.");

		int counter=0;
		String columnsvalues="";
		for (TextualPage textualPage: textualPages){

			System.out.println("Working on PubPage: "+textualPage.getId());
			
			String text = textualPage.toString();
			
			if (textualPage.isWraper()) { // get list Id for contact person list
				if(textualPage.getSourceToWrap().indexOf("id=")>-1){
					String link = textualPage.getSourceToWrap();
					String listId = link.substring(link.indexOf("id=")+3,link.indexOf("&"));
					System.out.println("listId:" +listId);
					PersonBean[] deskPersonsHebrew = personListService.getPersonsArray(new Integer(listId).intValue());
					for (PersonBean personBean : deskPersonsHebrew) {
						text = text.concat(personBean.getDegreeFullNameHebrew()+" ");
						text = text.concat(personBean.getDepartment()+" ");
						text = text.concat(personBean.getPhone()+" ");
					}
				}
				System.out.println("111111111111111 text: "+ text);
			}

			text = text.replaceAll("\\*", " * ");  //pad all * with spaces
			text = text.replaceAll("<", " <");
			text = text.replaceAll(">", "> ");
			
			WordsTokenizer wt = new WordsTokenizer(" ");
			List<String> wordsList = wt.getSubstringsList(text);
			wordsList.addAll(wt.getSubstringsList(text,2));
			wordsList.addAll(wt.getSubstringsList(text,3));
			wordsList.addAll(wt.getSubstringsList(text,4));
			List<String> extendedWordsList = new ArrayList<String>();
			for (String word : wordsList){
				if (word.indexOf("<")==-1 && word.indexOf(">")==-1){
					word = word.replaceAll("\\(","");
					word = word.replaceAll("\\)", "");
					word = word.replaceAll(",", "");
					word = word.replaceAll("\\.", "");
					word = word.replaceAll("~", "");
					word = word.replaceAll(":", "");
					word = word.replaceAll("&nbsp;"," ");
					word = word.replaceAll("&quot;", "\"");
					word = word.replaceAll(";", "");
					word = word.replaceAll("'", "");
					int pos;
					if ((pos=word.indexOf("-"))!=-1) {
						extendedWordsList.add(word.substring(0,pos));
						extendedWordsList.add(word.substring(pos+1));
						extendedWordsList.add(word.replaceAll("-"," "));
					}
					if ((pos=word.indexOf("&"))!=-1 && word.indexOf(";")==-1) {
						extendedWordsList.add(word.substring(0,pos));
						extendedWordsList.add(word.substring(pos+1));
						extendedWordsList.add(word.replaceAll("&",""));
					}
					if ((pos=word.indexOf("/"))!=-1) {
						extendedWordsList.add(word.substring(0,pos));
						extendedWordsList.add(word.substring(pos+1));
						extendedWordsList.add(word.replaceAll("/"," "));
					}
					word = word.trim();
					
					if(!word.equals("")){
						counter++;
						if (counter>1)
							columnsvalues += ",";
						columnsvalues += "('" + word + "'," + textualPage.getId() + ")";
					}
					if(counter==100){
						pageWordsIndexerDao.insertWordToTextualPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
						counter=0;
						columnsvalues="";
					}					
				}
			}
			//if finished wordlist loop and still left less then 100 words
			if(counter>0 && counter<100){
				pageWordsIndexerDao.insertWordToTextualPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
				counter=0;
				columnsvalues="";
			}
			for (String word : extendedWordsList){
				if(!word.equals("")){
					counter++;
					if (counter>1)
						columnsvalues += ",";
					columnsvalues += "('" + word + "'," + textualPage.getId() + ")";
				}
				if(counter==100){
					pageWordsIndexerDao.insertWordToTextualPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
					counter=0;
					columnsvalues="";
				}					
			}
			//if finished extendedWordsList loop and still left less then 100 words
			if(counter>0 && counter<100){
				pageWordsIndexerDao.insertWordToTextualPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
				counter=0;
				columnsvalues="";
			}
		}//loop over pages

		pageWordsIndexerDao.purgePubPagesIndexTable(configurationService.getConfigurationString("websiteDb"));
		System.out.println("PubPageIndexer Indexing Finished successfully");
	}
	
	public void executeCommand (String externCommand) throws IOException {
		Process proc = Runtime.getRuntime().exec(externCommand);
		try {
			proc.waitFor();
		}
		catch (InterruptedException e) {
			System.out.println("InterruptedException raised: "+e.getMessage());
		}
	}
	
	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	
	private PersonListService personListService;
	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}
	
	private PageWordsIndexerDao pageWordsIndexerDao;
	public void setPageWordsIndexerDao(PageWordsIndexerDao pageWordsIndexerDao) {
		this.pageWordsIndexerDao = pageWordsIndexerDao;
	}

}
