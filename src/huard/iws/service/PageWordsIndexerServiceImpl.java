package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.db.PageWordsIndexerDao;
import huard.iws.db.JdbcPageWordsIndexerDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.WordsTokenizer;

import java.util.Date;
import java.util.List;

public class PageWordsIndexerServiceImpl implements PageWordsIndexerService{
	
	private List<CallOfProposal> callOfProposals;

	private final long runsInterval = 36000000L;
	
	private JdbcPageWordsIndexerDao jdbcpageWordsIndexerDao;
	public PageWordsIndexerServiceImpl(){
		jdbcpageWordsIndexerDao = new JdbcPageWordsIndexerDao();
	}
	
	public void indexInfoPages(boolean init){
		long runsInterval;
		if (init) 
			runsInterval = new Date().getTime();
		else 
			runsInterval = this.runsInterval;

		if (callOfProposals==null) 
			callOfProposals = jdbcpageWordsIndexerDao.getLatelyUpdatedInfoPages(runsInterval,"localhost");
		//callOfProposals = pageWordsIndexerDao.getLatelyUpdatedInfoPages(runsInterval,configurationService.getConfigurationString("websiteDb"));
		System.out.println("InfoPagesIndexer: Indexing, Starting... Indexing "+callOfProposals.size()+" pages.");
		pageWordsIndexerDao.deleteLatelyUpdatedInfoPagesFromIndexTable(runsInterval,"localhost");
		
		for (CallOfProposal callOfProposal: callOfProposals){
			
			String text = callOfProposal.toString();
			
			System.out.println(callOfProposal.getId()+" "+text);
			
			PersonBean[] deskPersonsEnglish = personListService.getPersonsArray(pageWordsIndexerDao.getEnglishDesk(callOfProposal.getDeskId()));
			PersonBean[] deskPersonsHebrew = personListService.getPersonsArray(pageWordsIndexerDao.getHebrewDesk(callOfProposal.getDeskId()));
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

			text = replaceAll(text, "*", " * ");  //pad all * with spaces
			text = replaceAll(text, "<", " <");
			text = replaceAll(text, ">", "> ");

			WordsTokenizer wt = new WordsTokenizer(" ");
			List<String> wordsList = wt.getSubstringsList(text);
			wordsList.addAll(wt.getSubstringsList(text,2));
			wordsList.addAll(wt.getSubstringsList(text,3));

			for (int j=0; j<wordsList.size(); j++){
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

				pageWordsIndexerDao.insertWordToInfoPagesIndexTable(word,callOfProposal.getId(),configurationService.getConfigurationString("websiteDb"));
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

	public static void main (String [] args){
		System.out.println("Starting ....");
		PageWordsIndexerServiceImpl pageWordsIndexerServiceImpl = new PageWordsIndexerServiceImpl();
		boolean init = false;
		if (args.length>0){
			init = (args[0]!=null && "init".equals(args[0]));
		}
		pageWordsIndexerServiceImpl.indexInfoPages(init);
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

	public PageWordsIndexerDao getPageWordsIndexerDao() {
		return pageWordsIndexerDao;
	}


}
