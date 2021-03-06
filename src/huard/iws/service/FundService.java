package huard.iws.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import huard.iws.bean.PersonBean;
import huard.iws.model.Fund;

public interface FundService {

	public Fund getFund( int id);

	public Fund getFundByFinancialId(int financialId);

	public void updateFund (Fund fund);

	public int insertFund (Fund fund);

	public void deleteFund (int fundId);

	public List<Fund> getFunds();

	public List<Fund> getTemporaryFunds();

	public List<Fund> getNonTemporaryFunds();

	public List<Fund> getFilteredFunds(String term);

	public Map<Integer, Fund> getFundsMap();

	public Map<Integer, Fund> getFundsMapByDesk(int personId);

	public Map<Integer, Fund> getFundsMapByProposals(PersonBean personBean);

	public LinkedHashMap<String, Fund> getFundsNamesMap();

	public Integer getFundIdByShortName(String shortName);

	public Fund getArdFund(int id);

	public int getMaxFinancialIdForTemporary();

}
