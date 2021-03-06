package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PagesWordsIndexerDao;
import huard.iws.model.AList;
import huard.iws.model.CallForProposalOld;
import huard.iws.model.Desk;
import huard.iws.model.OrganizationUnit;
import huard.iws.model.TextualPageOld;
import huard.iws.util.WordsTokenizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class PagesWordsIndexerServiceImpl implements PagesWordsIndexerService{

	private static final Logger logger = Logger.getLogger(PagesWordsIndexerServiceImpl.class);
	
	private final long RUNS_INTERVAL = 36000000L;
	
	public void indexInfoPages(boolean init){
		long runsInterval;
		if (init) 
			runsInterval = new Date().getTime();
		else 
			runsInterval = RUNS_INTERVAL;

		List<CallForProposalOld> callForProposals = pagesWordsIndexerDao.getLatelyUpdatedInfoPages(runsInterval,configurationService.getConfigurationString("website", "websiteDb"));

		System.out.println("InfoPagesIndexer: Indexing "+callForProposals.size()+" pages.");
		if (callForProposals.size()>0) 
			pagesWordsIndexerDao.deleteLatelyUpdatedInfoPagesFromIndexTable(callForProposals,init,configurationService.getConfigurationString("website", "websiteDb"));

		Map<String, String> desksPersonsMap = new HashMap<String, String>();
		List<Desk> desks = pagesWordsIndexerDao.getDesks(configurationService.getConfigurationString("website", "websiteDb"));
		for (Desk desk: desks){
			try{
				List<PersonBean> deskPersonsEnglish = personListService.getPersonsList(desk.getEnglishListId());
				List<PersonBean> deskPersonsHebrew = personListService.getPersonsList(desk.getHebrewListId());
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

		int counter = 0;
		for (CallForProposalOld callForProposal: callForProposals){
			logger.info("Call of porposal: " + callForProposal.getId());
			String text = callForProposal.toString();

			if(callForProposal.getDeskId()!=null && desksPersonsMap.get(callForProposal.getDeskId())!=null)
				text += " " + desksPersonsMap.get(callForProposal.getDeskId());
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
			counter += pagesWordsIndexerDao.insertWordsToInfoPagesIndexTable(actualWordsList, callForProposal.getId(), 
					configurationService.getConfigurationString("website", "websiteDb"));
		}
		pagesWordsIndexerDao.purgeInfoPagesIndexTable(configurationService.getConfigurationString("website", "websiteDb"));
		System.out.println("InfoPagesIndexer: Indexed " + counter + " words in " + callForProposals.size() + "call of proposals");
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
		List<TextualPageOld> textualPages = pagesWordsIndexerDao.getLatelyUpdatedPubPages(runsInterval,configurationService.getConfigurationString("website", "websiteDb"));

		if (textualPages.size()>0) 
			pagesWordsIndexerDao.deleteLatelyUpdatedPubPagesFromIndexTable(textualPages, init,configurationService.getConfigurationString("website", "websiteDb"));

		logger.info("PubPagesIndexer Indexing "+textualPages.size()+" pages.");

		int counter=0;
		
		for (TextualPageOld textualPage: textualPages){

			logger.info("Working on PubPage: "+textualPage.getId());

			String text = textualPage.toString();

			logger.info("isWrapper: " + textualPage.isWraper());
			logger.info("source to wrap: " + textualPage.getSourceToWrap());
			if (textualPage.isWraper() && textualPage.getSourceToWrap().matches("^.*?viewList.htm.*?id=([\\d]+).*?$")){
				String link = textualPage.getSourceToWrap();
				System.out.println("Link: " + link);
				String aListId = link.replaceFirst("^.*?id=([\\d]+).*?$", "$1");
				System.out.println("A list Id: " + aListId);
				int listId = Integer.parseInt(aListId);
				
				AList aList  = listService.getList(listId);
				// persons list
				if (aList.getListTypeId() == AList.PERSONS_LIST_TYPE_ID){
					List<PersonBean> deskPersonsHebrew = personListService.getPersonsList(listId);
					for (PersonBean personBean : deskPersonsHebrew) {
						text = text.concat(personBean.getDegreeFullNameHebrew()+" ");
						text = text.concat(personBean.getDepartment()+" ");
						text = text.concat(personBean.getPhone()+" ");
					}
				}
				else{//organization unit list
					List<OrganizationUnit> organizationUnitList = organizationUnitService.getOrganizationUnits(listId,"");
					for (OrganizationUnit organizationUnit : organizationUnitList) {
						text = text.concat(organizationUnit.getNameHebrew()+" ");
						text = text.concat(organizationUnit.getNameEnglish()+" ");
					}
				}
				//System.out.println("texts: " + text);
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
			counter += pagesWordsIndexerDao.insertWordsToPubPagesIndexTable(actualWordsList, textualPage.getId(), getWebsiteDb());
		}//loop over pages

		pagesWordsIndexerDao.purgePubPagesIndexTable(getWebsiteDb());
		logger.info("Textual pages indexed " + counter + " words in " + textualPages.size() + " textual pages");
	}
	
	private String getWebsiteDb(){
		return configurationService.getConfigurationString("website", "websiteDb");
	}


	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	private OrganizationUnitService organizationUnitService;
	public void setOrganizationUnitService(
			OrganizationUnitService organizationUnitService) {
		this.organizationUnitService = organizationUnitService;
	}


	private PersonListService personListService;
	public void setPersonListService(PersonListService personListService) {
		this.personListService = personListService;		
	}

	private ListService listService;
	public void setListService(ListService listService) {
		this.listService = listService;
	}


	private PagesWordsIndexerDao pagesWordsIndexerDao;
	public void setPagesWordsIndexerDao(PagesWordsIndexerDao pagesWordsIndexerDao) {
		this.pagesWordsIndexerDao = pagesWordsIndexerDao;
	}

	

}
