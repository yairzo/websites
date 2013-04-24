package huard.iws.service;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalBean;
import huard.iws.db.FundDao;
import huard.iws.model.Fund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FundServiceImpl implements FundService{
	private List<Fund> funds;
	private Map<Integer,Fund> fundsMap;
	private Map<String,Integer> shortNameIdMap;

	public Fund getFund( int id){
		return fundDao.getFund(id);
	}

	public Fund getFundByFinancialId(int financialId){
		return fundDao.getFundByFinancialId(financialId);
	}
	
	public void updateFund (Fund fund){
		fundDao.updateFund(fund);
	}

	public int insertFund (Fund fund){
		return fundDao.insertFund(fund);
	}

	public void deleteFund (int fundId){
		fundDao.deleteFund(fundId);
	}

	public List<Fund> getFunds(){
		if (funds==null){
			funds =  fundDao.getFunds();
		}
		return funds;
	}
	
	public List<Fund> getTemporaryFunds(){
		return fundDao.getTemporaryFunds();
	}


	public Map<Integer, Fund> getFundsMap(){
		if (fundsMap == null){
			fundsMap = new HashMap<Integer, Fund>();
			for (Fund fund: getFunds()){
				fundsMap.put(fund.getId(), fund);
			}
		}
		return fundsMap;
	}

	public Map<Integer, Fund> getFundsMapByDesk(int personId){
		Map<Integer, Fund> fundsMap = new HashMap<Integer, Fund>();
		int deskId = mopDeskService.getPersonDeskId(personId);
		for (Fund fund: getFunds()){
			if (fund.getDeskId() == deskId){
				fundsMap.put(fund.getId(), fund);
			}
		}
		return fundsMap;
	}
	public Map<Integer, Fund> getFundsMapByProposals(PersonBean personBean){
		List<ProposalBean> proposalBeans = proposalListService.getProposals(personBean);
		Set<Integer> fundIds = new HashSet<Integer>();
		for (ProposalBean proposalBean: proposalBeans){
			fundIds.add(proposalBean.getFundId());
		}
		Map<Integer, Fund> fundsMap = new HashMap<Integer, Fund>();
		for (Fund fund: getFunds()){
			if (fundIds.contains(fund.getId()))
				fundsMap.put(fund.getId(), fund);
		}
		return fundsMap;
	}

	public LinkedHashMap<String, Fund> getFundsNamesMap(){
		LinkedHashMap<String,Fund> fundsNamesMap = new LinkedHashMap<String, Fund>();
		for (Fund fund: getFunds()){
			if (! fund.isTemporary()) {
				fundsNamesMap.put(fund.getShortName()+ "-"+fund.getName(), fund);
				fundsNamesMap.put(fund.getName()+ "-"+fund.getShortName(), fund);
			}
		}
		return fundsNamesMap;
	}
	
	public List<Fund> getFilteredFunds ( String term){
		List<Fund> funds = getFunds();
		List<Fund> filteredFunds = new ArrayList<Fund>();
		for (Fund fund: funds){
			if (fund !=null && (fund.getName()!=null && fund.getName().toLowerCase().contains(term.toLowerCase())) || (fund.getShortName()!=null && fund.getShortName().contains(term)))
				filteredFunds.add(fund);
		}
		return filteredFunds;
	}
	
	public Integer getFundIdByShortName(String shortName){
		return getShortNameIdMap().get(shortName);
	}

	private Map<String,Integer> getShortNameIdMap(){
		if (shortNameIdMap ==null){
			shortNameIdMap = new HashMap<String, Integer>();
			for (Fund fund: getFunds()){
				shortNameIdMap.put(fund.getShortName(), fund.getId());
			}
		}
		return shortNameIdMap;
	}

	public Fund getArdFund(int id){
		return fundDao.getArdFund(id, configurationService.getConfigurationString("website", "websiteDb"));
	}
	
	public int getMaxFinancialIdForTemporary(){
		return fundDao.getMaxFinancialIdForTemporary();
	}

	private MopDeskService mopDeskService;

	public void setMopDeskService(MopDeskService mopDeskService) {
		this.mopDeskService = mopDeskService;
	}

	private ProposalListService proposalListService;

	public void setProposalListService(ProposalListService proposalListService) {
		this.proposalListService = proposalListService;
	}


	private FundDao fundDao;

	public FundDao getFundDao() {
		return fundDao;
	}

	public void setFundDao(FundDao fundDao) {
		this.fundDao = fundDao;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
}
