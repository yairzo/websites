package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PageWordsIndexerDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.WordsTokenizer;

import java.util.Date;
import java.util.List;

public class PageWordsIndexerServiceImpl implements PageWordsIndexerService{
	
	private List<CallOfProposal> callOfProposals;

	private final long runsInterval = 36000000L;
	

	public void indexInfoPages(boolean init){
		long runsInterval;
		if (init) 
			runsInterval = new Date().getTime();
		else 
			runsInterval = this.runsInterval;

		if (callOfProposals==null) 
			callOfProposals = pageWordsIndexerDao.getLatelyUpdatedInfoPages(runsInterval,configurationService.getConfigurationString("websiteDb"));
	
		System.out.println("InfoPagesIndexer: Indexing "+callOfProposals.size()+" pages.");
		if (callOfProposals.size()>0) 
			pageWordsIndexerDao.deleteLatelyUpdatedInfoPagesFromIndexTable(callOfProposals,init,configurationService.getConfigurationString("websiteDb"));
		
		int counter=0;
		String columnsvalues="";
		
		//TODO: rewrite the code: before iterating the callOfProposals, build a HashMap<String, String>. The key will be the desk id, 
		// the value will be a string that contains all the names + titles + emails  in hebrew + english
		//than in the loop just call personsMap.get("<deskId>")
		
		
		for (CallOfProposal callOfProposal: callOfProposals){
			
			String text = callOfProposal.toString();
			
			PersonBean[] deskPersonsEnglish = personListService.getPersonsArray(pageWordsIndexerDao.getEnglishDesk(callOfProposal.getDeskId(),configurationService.getConfigurationString("websiteDb")));
			PersonBean[] deskPersonsHebrew = personListService.getPersonsArray(pageWordsIndexerDao.getHebrewDesk(callOfProposal.getDeskId(),configurationService.getConfigurationString("websiteDb")));
			for (PersonBean personBean : deskPersonsEnglish) {
				text = text.concat(personBean.getDegreeEnglish()+" ");
				text = text.concat(personBean.getFirstNameEnglish()+ " ");
				text = text.concat(personBean.getLastNameEnglish()+ " ");
				text = text.concat(personBean.getTitle()+ " ");
				text = text.concat(personBean.getPhone()+ " ");
				text = text.concat(personBean.getEmail()+ " ");
			}
			for (PersonBean personBean : deskPersonsHebrew) {
				text = text.concat(personBean.getDegreeHebrew()+" ");
				text = text.concat(personBean.getFirstNameHebrew()+" ");
				text = text.concat(personBean.getLastNameHebrew()+" ");
				text = text.concat(personBean.getTitle()+" ");
			}
			
			System.out.println("111111111111111 text: "+ text);

			text = replaceAll(text, "*", " * ");  //pad all * with spaces
			text = replaceAll(text, "<", " <");
			text = replaceAll(text, ">", "> ");

			WordsTokenizer wt = new WordsTokenizer(" ");
			List<String> wordsList = wt.getSubstringsList(text);
			wordsList.addAll(wt.getSubstringsList(text,2));
			wordsList.addAll(wt.getSubstringsList(text,3));
			for (int j=0; j<wordsList.size(); j++){
				counter++;
				String word = (String)wordsList.get(j);
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
				int pos;
				if ((pos=word.indexOf("-"))!=-1) {
					wordsList.add(word.substring(0,pos));
					wordsList.add(word.substring(pos+1));
					wordsList.add(replaceAll(word,"-",""));
				}
				if ((pos=word.indexOf("&"))!=-1) {
					wordsList.add(word.substring(0,pos));
					wordsList.add(word.substring(pos+1));
					wordsList.add(replaceAll(word,"&",""));
				}
				if ((pos=word.indexOf("/"))!=-1) {
					wordsList.add(word.substring(0,pos));
					wordsList.add(word.substring(pos+1));
					wordsList.add(replaceAll(word,"/",""));
				}
		//TODO: let's talk about the counter issue I would like to rewrite it
				
				if(!word.equals("")){
					if (counter>1)
						columnsvalues += ",";
					columnsvalues += "('" + word + "'," + callOfProposal.getId() + ")";
				}
				else
					counter--;
				if(counter==100){
					pageWordsIndexerDao.insertWordToInfoPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
					counter=0;
					columnsvalues="";
				}
			}
		}
		if(counter>0 && counter<100){
			pageWordsIndexerDao.insertWordToInfoPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
			counter=0;
			columnsvalues="";
		}
		pageWordsIndexerDao.purgeInfoPagesIndexTable(configurationService.getConfigurationString("websiteDb"));
		System.out.println("InfoPagesIndexer:Indexing Success");
	}


	public String replaceAll(String s, String regexp, String replacement){
		StringBuffer sb = new StringBuffer();
		int pos;
		while ((pos = s.indexOf(regexp))!=-1){
			sb.append(s.substring(0, pos)+replacement);
			s = s.substring(pos+regexp.length());
		}
		sb.append(s);
		return sb.toString();
	}

	public String replaceOnEdges(String s, String regexp, String replacement){
		if (s.length()>regexp.length() && s.substring(0,regexp.length()).equals(regexp)){
			s = replacement+s.substring(regexp.length());
		}
		if (s.length()>regexp.length() && s.substring(s.length()-regexp.length()).equals(regexp)){
		 	s = s.substring(0,s.length()-regexp.length())+replacement;
		}
		return s;
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
