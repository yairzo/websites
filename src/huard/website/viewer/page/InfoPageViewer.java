package huard.website.viewer.page;

import huard.iws.bean.PersonBean;
import huard.iws.service.PersonListService;
import huard.website.model.Fund;
import huard.website.model.TabledInfoPage;
import huard.website.model.Worker;
import huard.website.util.PageAccessLog;
import huard.website.util.Utils;
import huard.website.util.WordsTokenizer;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class InfoPageViewer {
	private String ardNum;
	private Worker[] workers;
	private TabledInfoPage tabledInfoPage;
	private Fund fund;
	private String[] formatedAdditionalSubDates;
	private PagesDbHandler dbHandler;
	private String foundBySearchWords;
	private final String categoryTableNameEng = "financeSources";
	private final String categoryTableNameHeb = "efsharuiot_mimun";
	private String md5;

	//private static final Logger logger = Logger.getLogger(InfoPageViewer.class);

	public InfoPageViewer() {
		dbHandler = new PagesDbHandler();
	}

	public void logAccessToPage(boolean heb, String ip) {
		PageAccessLog.logAccesToPage("Call of Proposal", getTabledInfoPage()
				.getArdNum(), getTabledInfoPage().getTitle(),
				getTabledInfoPage().isHebrew(), ip);
	}

	public TabledInfoPage getTabledInfoPage() {
		if (tabledInfoPage == null) {
			tabledInfoPage = dbHandler.getTabledInfoPageDetailsByArdNum(ardNum);
			if (foundBySearchWords != null)
				tabledInfoPage.markWords(foundBySearchWords);
		}
		return tabledInfoPage;
	}

	public boolean isAuthorized(String ipString) {
		WordsTokenizer wt = new WordsTokenizer(".");
		List<String> addressNumsList = wt.getSubstringsList(ipString);
		int[] addressNumsArray = new int[addressNumsList.size()];
		for (int i = 0; i < addressNumsArray.length; i++) {
			addressNumsArray[i] = Integer.parseInt((String) addressNumsList
					.get(i));
		}

		if (addressNumsArray[0] == 132) {
			if (addressNumsArray[1] >= 64 && addressNumsArray[1] <= 65)
				return true;
			else
				return false;
		} else if (addressNumsArray[0] == 128 && addressNumsArray[1] == 139) {
			if (addressNumsArray[2] <= 31)
				return true;
			else
				return false;
		}

		else
			return false;
	}	

	public Worker[] getWorkersByDesk() {
		if (workers == null) {
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(PersonListService.class);
			factory.setServiceUrl("rmi://localhost:1199/PersonListService");
			factory.afterPropertiesSet();

			PersonListService personListService = (PersonListService) factory
					.getObject();
			List<PersonBean> deskPersonsEnglish = personListService
					.getPersonsList(dbHandler.getDesk(
							tabledInfoPage.getDeskId()).getEnglishListId());
			List<PersonBean> deskPersonsHebrew = personListService
					.getPersonsList(dbHandler.getDesk(
							tabledInfoPage.getDeskId()).getHebrewListId());
			Map<Integer, Worker> deskWorkersMap = new LinkedHashMap<Integer, Worker>();
			for (PersonBean personBean : deskPersonsEnglish) {
				Worker worker = new Worker();
				worker.setEnglishPre(personBean.getDegreeEnglish());
				worker.setEnglishName(personBean.getFirstNameEnglish() + " "
						+ personBean.getLastNameEnglish());
				worker.setEnglishTitle(personBean.getTitle());
				worker.setPhone(personBean.getPhone());
				worker.setEmail(personBean.getEmail());
				deskWorkersMap.put(personBean.getId(), worker);
			}
			for (PersonBean personBean : deskPersonsHebrew) {
				Worker worker = deskWorkersMap.get(personBean.getId());
				if (worker == null){
					worker = new Worker();
					deskWorkersMap.put(personBean.getId(), worker);
				}
				worker.setHebrewPre(personBean.getDegreeHebrew());
				worker.setHebrewName(personBean.getFirstNameHebrew() + " "
						+ personBean.getLastNameHebrew());
				worker.setHebrewTitle(personBean.getTitle());
			}

			workers = new Worker[deskWorkersMap.size()];
			int i = 0;
			for (Iterator<Worker> workersIterator = deskWorkersMap.values()
					.iterator(); i < workers.length; i++) {
				workers[i] = workersIterator.next();
			}
		}
		return workers;
	}

	public Worker[] getWorkersByDeskAssistant() {
		Worker[] assistants = dbHandler.getWorkersByPhraseFromTitleByDesk(
				"Assistant", tabledInfoPage.getDeskId());
		return assistants;
	}

	public String getFundsBudgetOfficerEnglishName() {
		return getFundByFundNum().getBudgetOfficer();
	}

	public Worker getBudgetOfficerWorker() {
		Worker worker = new Worker();
		for (Worker aWorker : getWorkersByDesk()) {
			if (aWorker.getEnglishName().equals(
					getFundsBudgetOfficerEnglishName()))
				return aWorker;
		}
		return worker;
	}

	/*
	 * public Worker getBudgetOfficerWorker(){ String budgetOfficerName =
	 * getFundByFundNum().getBudgetOfficer(); if (budgetOfficerName!=null && !
	 * "".equals(budgetOfficerName)){ Worker [] workers =
	 * dbHandler.getWorkerByEnglishName(budgetOfficerName); if (workers !=null
	 * && foundBySearchWords!=null) workers[0].markText(foundBySearchWords); if
	 * (workers!=null) return workers[0]; } return null; }
	 */

	public boolean isHasBudgetOfficerWorker() {
		String budgetOfficerName = getFundsBudgetOfficerEnglishName();
		return Utils.isHasValue(budgetOfficerName)
				&& getBudgetOfficerWorker() != null;
	}

	public Worker getFinancialReporterWorker() {
		String financialReporterName = getFundByFundNum()
				.getFinancialReporter();
		if (financialReporterName == null)
			financialReporterName = "";
		Worker[] workers = dbHandler
				.getWorkerByEnglishName(financialReporterName);
		if (workers != null && foundBySearchWords != null)
			workers[0].markText(foundBySearchWords);
		return workers[0];
	}

	public String getMailAddressByEnglishName(String englishName) {
		return Utils.getMailAddress(englishName);
	}

	public boolean isStringHasValue(String s) {
		s = s.trim();
		if (s != null && !s.equals("null"))
			return (!s.equals(""));
		return false;
	}

	public boolean isStringHasNullMarker(String s) {
		if (s.toLowerCase().indexOf("xxxxx") != -1)
			return true;
		return false;
	}

	public Fund getFundByFundNum() {
		if (fund == null)
			fund = dbHandler.getFundByFundNum("" + tabledInfoPage.getFundNum());
		return fund;
	}

	public long getDate() {
		return new Date().getTime();
	}

	public String getInfoPageLastUpdateDate() {
		return Utils.getFormatedDate(dbHandler
				.getInfoPageLastUpdateDate(getTabledInfoPage().getArdNum()));
	}

	public boolean isHasExperimentalResearchField() {
		int[] researchFields = getResearchFieldsIntArray();
		for (int i = 0; i < researchFields.length; i++) {
			if (researchFields[i] == 1)
				if (dbHandler.getResearchFieldByNum(i + 1).isExperimental())
					return true;
		}
		return false;
	}

	public int[] getResearchFieldsIntArray() {
		int numOfResearchFields = dbHandler.getRowsCount(
				Utils.getWebsiteDatabaseName(), "ResearchFields");
		int[] researchFields = new int[numOfResearchFields];
		int j = tabledInfoPage.getResearchFields();
		for (int i = numOfResearchFields - 1; i >= 0; i--) {
			if (j % 2 == 1) {
				researchFields[i] = 1;
				j = j - 1;
			} else
				researchFields[i] = 0;
			j = j / 10;
		}
		return researchFields;
	}

	public String[] getFormatedAdditionalSubDates() {
		if (formatedAdditionalSubDates == null) {
			long[] additionalSubDates = dbHandler.getAdditionalSubDates(""
					+ ardNum);
			formatedAdditionalSubDates = new String[additionalSubDates.length];
			for (int i = 0; i < formatedAdditionalSubDates.length; i++) {
				formatedAdditionalSubDates[i] = Utils
						.getFormatedDate(additionalSubDates[i]);
			}
		}
		return formatedAdditionalSubDates;
	}

	public String getFormatedText(String text) {
		text = text.replaceAll("~", "<br>");
		WordsTokenizer elementsTokenizer = new WordsTokenizer("*");
		List<String> elementsList = elementsTokenizer.getSubstringsListNoTrim(text);
		int elementsListSize;
		if ((elementsListSize = elementsList.size()) >= 3) {
			StringBuffer textBuffer = new StringBuffer();
			int i, k = (int) elementsListSize / 3;
			for (i = 0; i < k; i++) {
				textBuffer.append((String) elementsList.get(i * 3));
				String linkText = (String) elementsList.get(i * 3 + 1);
				linkText = linkText.replaceAll("<br>", "~");
				String linkTarget = (String) elementsList.get(i * 3 + 2);
				linkTarget = linkTarget.replaceAll("<br>", "~");
				if (linkTarget.indexOf("http") != -1
						|| linkTarget.indexOf("mailto") != -1
						|| linkTarget.indexOf("ftp") != -1)
					textBuffer.append("<a href=\"" + linkTarget
							+ "\" target=\"view_window\">" + linkText + "</a>");
				else {
					linkTarget = linkTarget.trim();
					textBuffer.append("<a href=\"ftp://ard.huji.ac.il/pub/"
							+ tabledInfoPage.getFundNum() + "/" + linkTarget
							+ "\">" + linkText + "</a>");
				}

			}
			for (int j = i * 3; j < elementsListSize; j++)
				textBuffer.append("&nbsp;" + (String) elementsList.get(j));
			text = textBuffer.toString();

		}
		text = takeOffAllBackslashs(text);
		return text;
	}

	public String takeOffAllBackslashs(String s) {
		int pos;
		while ((pos = s.indexOf("\\")) != -1) {
			String s1 = s.substring(0, pos);
			s = s1.concat(s.substring(pos + 1));
		}
		return s;
	}

	/**
	 * @return
	 */
	public String getArdNum() {
		return ardNum;
	}

	/**
	 * @param string
	 */
	public void setArdNum(String string) {
		ardNum = string;
	}

	public String getFoundBySearchWords() {
		return foundBySearchWords;
	}

	public void setFoundBySearchWords(String foundBySearchWords) {
		this.foundBySearchWords = foundBySearchWords;
	}

	public String getCategory(boolean heb) {
		return heb ? categoryTableNameHeb : categoryTableNameEng;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
