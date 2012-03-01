package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PageWordsIndexerDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.WordsTokenizer;
import huard.iws.model.Desk;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
		Map<String, String> personsMap = new HashMap<String, String>();
		List<Desk> desks = pageWordsIndexerDao.getDesks(configurationService.getConfigurationString("websiteDb"));
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
				System.out.println("111111111111111 desk: "+ desk.getId() + " , value:" + value);
				personsMap.put(desk.getId(),value);
			}
			catch(Exception e){
				System.out.println("Exception: "+ e.getMessage());
			}
		}
		
		for (CallOfProposal callOfProposal: callOfProposals){
			
			String text = callOfProposal.toString();
			
			if(callOfProposal.getDeskId()!=null && personsMap.get(callOfProposal.getDeskId())!=null)
				text.concat(personsMap.get(callOfProposal.getDeskId()));
			
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
				
				if(!word.equals("")){
					if (!columnsvalues.equals(""))
						columnsvalues += ",";
					columnsvalues += "('" + word + "'," + callOfProposal.getId() + ")";
				}
				if(counter%100==0 || counter==callOfProposals.size()){
					pageWordsIndexerDao.insertWordToInfoPagesIndexTable(columnsvalues,configurationService.getConfigurationString("websiteDb"));
					columnsvalues="";
				}
			}
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
