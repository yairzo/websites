package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PagesWordsIndexerDao;
import huard.iws.model.CallOfProposal;
import huard.iws.model.Desk;
import huard.iws.model.TextualPage;
import huard.iws.util.WordsTokenizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagesWordsIndexerServiceImpl implements PagesWordsIndexerService{
	
	private final long RUNS_INTERVAL = 36000000L;	

	public void indexInfoPages(boolean init){
		long runsInterval;
		if (init) 
			runsInterval = new Date().getTime();
		else 
			runsInterval = RUNS_INTERVAL;

		List<CallOfProposal> callOfProposals = pagesWordsIndexerDao.getLatelyUpdatedInfoPages(runsInterval,configurationService.getConfigurationString("websiteDb"));
	
		System.out.println("InfoPagesIndexer: Indexing "+callOfProposals.size()+" pages.");
		if (callOfProposals.size()>0) 
			pagesWordsIndexerDao.deleteLatelyUpdatedInfoPagesFromIndexTable(callOfProposals,init,configurationService.getConfigurationString("websiteDb"));
		
		//TODO: rewrite the code: before iterating the callOfProposals, build a HashMap<String, String>. The key will be the desk id, 
		// the value will be a string that contains all the names + titles + emails  in hebrew + english
		//than in the loop just call personsMap.get("<deskId>")
		Map<String, String> desksPersonsMap = new HashMap<String, String>();
		List<Desk> desks = pagesWordsIndexerDao.getDesks(configurationService.getConfigurationString("websiteDb"));
		for (Desk desk: desks){
			try{
				PersonBean[] deskPersonsEnglish = personListService.getPersonsArray(desk.getEnglishListId());
				PersonBean[] deskPersonsHebrew = personListService.getPersonsArray(desk.getHebrewListId());
				String value ="";
				for (PersonBean personBean : deskPersonsEnglish) {
					value = value.concat(personBean.getDegreeEnglish()+" ");
					value = value.concat(personBean.getFirstNameEnglish()+ " ");
					value = value.concat(personBean.getLastNameEnglish()+ " ");
					value = value.concat(personBean.getTitle()+ " ");
					value = value.concat(personBean.getPhone()+ " ");
					value = value.concat(personBean.getEmail()+ " ");
				}
				for (PersonBean personBean : deskPersonsHebrew) {
					value = value.concat(personBean.getDegreeHebrew()+" ");
					value = value.concat(personBean.getFirstNameHebrew()+" ");
					value = value.concat(personBean.getLastNameHebrew()+" ");
					value = value.concat(personBean.getTitle()+" ");
				}
				desksPersonsMap.put(desk.getId(),value);
			}
			catch(Exception e){
				System.out.println("Exception: "+ e.getMessage());
			}
		}
		
		int counter=0;
		String columnsvalues="";
		
		for (CallOfProposal callOfProposal: callOfProposals){
			
			String text = callOfProposal.toString();
			
			if(callOfProposal.getDeskId()!=null && desksPersonsMap.get(callOfProposal.getDeskId())!=null)
				text.concat(desksPersonsMap.get(callOfProposal.getDeskId()));
			
			text = replaceAll(text, "*", " * ");  //pad all * with spaces
			text = replaceAll(text, "<", " <");
			text = replaceAll(text, ">", "> ");

			WordsTokenizer wt = new WordsTokenizer(" ");
			List<String> wordsList = wt.getSubstringsList(text);
			wordsList.addAll(wt.getSubstringsList(text,2));
			wordsList.addAll(wt.getSubstringsList(text,3));
			List<String> actualWordsList = wt.getSubstringsList(text);

			
			for (String word: wordsList){
				word=word.trim();
				word = replaceAll(word, "(", "");
				word = replaceAll(word, ")", "");
				word = replaceAll(word, ",", "");
				word = replaceAll(word, ".", "");
				word = replaceAll(word, "&quot;", "\"");
				word = replaceAll(word, ";", "");
				word = replaceAll(word, "~", "");
				word = replaceAll(word, ":", "");
				word = replaceAll(word, "'", "");
				word = replaceOnEdges(word, "\\\"","");
				
				actualWordsList.add(word);
				int pos;
				
				if ((pos=word.indexOf("-"))!=-1) {
					actualWordsList.add(word.substring(0,pos));
					actualWordsList.add(word.substring(pos+1));
					actualWordsList.add(replaceAll(word,"-",""));
				}
				if ((pos=word.indexOf("&"))!=-1) {
					actualWordsList.add(word.substring(0,pos));
					actualWordsList.add(word.substring(pos+1));
					actualWordsList.add(replaceAll(word,"&",""));
				}
				if ((pos=word.indexOf("/"))!=-1) {
					actualWordsList.add(word.substring(0,pos));
					actualWordsList.add(word.substring(pos+1));
					actualWordsList.add(replaceAll(word,"/",""));
				}
			}
			for (String word: actualWordsList){
				if(!word.isEmpty()){
					counter++;
					if (!columnsvalues.isEmpty())
						columnsvalues += ",";
					columnsvalues += "('" + word + "'," + callOfProposal.getId() + ")";
				}
				if(counter%100==0 || counter==callOfProposals.size()){
					pagesWordsIndexerDao.insertWordToInfoPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
					columnsvalues="";
				}
			}
		}
		pagesWordsIndexerDao.purgeInfoPagesIndexTable(configurationService.getConfigurationString("websiteDb"));
		System.out.println("InfoPagesIndexer: Indexed " + counter + " words in " + callOfProposals.size() + "call of proposals");
	}


	private String replaceAll(String s, String regexp, String replacement){
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos = s.indexOf(regexp))!=-1){
			sb.append(s.substring(0, pos)+replacement);
			s = s.substring(pos+regexp.length());
		}
		sb.append(s);
		return sb.toString();
	}

	private String replaceOnEdges(String s, String regexp, String replacement){
		if (s.length()>regexp.length() && s.substring(0,regexp.length()).equals(regexp)){
			s = replacement+s.substring(regexp.length());
		}
		if (s.length()>regexp.length() && s.substring(s.length()-regexp.length()).equals(regexp)){
		 	s = s.substring(0,s.length()-regexp.length())+replacement;
		}
		return s;
	}
	
	public void indexTextualPages(boolean init){
		long runsInterval;
		if (init) 
			runsInterval = new Date().getTime();
		else 
			runsInterval = RUNS_INTERVAL;
		List<TextualPage> textualPages = pagesWordsIndexerDao.getLatelyUpdatedPubPages(runsInterval,configurationService.getConfigurationString("websiteDb"));
		
		if (textualPages.size()>0) 
			pagesWordsIndexerDao.deleteLatelyUpdatedPubPagesFromIndexTable(textualPages, init,configurationService.getConfigurationString("websiteDb"));
		
		System.out.println("PubPagesIndexer Indexing "+textualPages.size()+" pages.");

		int counter=0;
		String columnsvalues="";
		for (TextualPage textualPage: textualPages){

			System.out.println("Working on PubPage: "+textualPage.getId());
			
			String text = textualPage.toString();
			
			if (textualPage.isWraper()) { // get list Id for contact person list
				if(textualPage.getSourceToWrap().indexOf("id=")!=-1){
					String link = textualPage.getSourceToWrap();
					String listId = link.replaceFirst("[?&]id=([\\d]+).*?", "$1");
					System.out.println("listId:" +listId);
					PersonBean[] deskPersonsHebrew = personListService.getPersonsArray(new Integer(listId).intValue());
					for (PersonBean personBean : deskPersonsHebrew) {
						text = text.concat(personBean.getDegreeFullNameHebrew()+" ");
						text = text.concat(personBean.getDepartment()+" ");
						text = text.concat(personBean.getPhone()+" ");
					}
				}
			}

			text = text.replaceAll("\\*", " * ");  //pad all * with spaces
			text = text.replaceAll("<", " <");
			text = text.replaceAll(">", "> ");
			
			WordsTokenizer wt = new WordsTokenizer(" ");
			List<String> wordsList = wt.getSubstringsList(text);
			wordsList.addAll(wt.getSubstringsList(text,2));
			wordsList.addAll(wt.getSubstringsList(text,3));
			wordsList.addAll(wt.getSubstringsList(text,4));
			List<String> actualWordsList = new ArrayList<String>();
			
			for (String word : wordsList){
				if (word.indexOf("<")!=-1 && word.indexOf(">")!=-1)
					continue;
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
					actualWordsList.add(word.substring(0,pos));
					actualWordsList.add(word.substring(pos+1));
					actualWordsList.add(word.replaceAll("-"," "));
				}
				if ((pos=word.indexOf("&"))!=-1 && word.indexOf(";")==-1) {
					actualWordsList.add(word.substring(0,pos));
					actualWordsList.add(word.substring(pos+1));
					actualWordsList.add(word.replaceAll("&",""));
				}
				if ((pos=word.indexOf("/"))!=-1) {
					actualWordsList.add(word.substring(0,pos));
					actualWordsList.add(word.substring(pos+1));
					actualWordsList.add(word.replaceAll("/"," "));
				}
				word = word.trim();
				actualWordsList.add(word);
			}
			for (String word: actualWordsList){
				if(!word.isEmpty()){
					counter++;
					if (!columnsvalues.isEmpty())
						columnsvalues += ",";
					columnsvalues += "('" + word + "'," + textualPage.getId() + ")";
				}
				if(counter%100 == 0 || counter == wordsList.size()){
					pagesWordsIndexerDao.insertWordToTextualPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
					columnsvalues="";
				}				
			}
		}//loop over pages

		pagesWordsIndexerDao.purgePubPagesIndexTable(configurationService.getConfigurationString("websiteDb"));
		System.out.println("Textual pages indexed " + counter + " words in " + textualPages.size() + " textual pages");
	}


	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	
	private PersonListService personListService;
	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;
	}

	private PagesWordsIndexerDao pagesWordsIndexerDao;
	public void setPagesWordsIndexerDao(PagesWordsIndexerDao pagesWordsIndexerDao) {
		this.pagesWordsIndexerDao = pagesWordsIndexerDao;
	}

}
