package huard.iws.db;

import huard.iws.model.Fund;

import java.util.List;

public interface FundDao {

	public Fund getFund(int id);

	public Fund getFundByFinancialId(int financialId);
	
	public int insertFund(Fund fund);

	public void updateFund(Fund fund);

	public void deleteFund (int fundId);

	public List<Fund> getFunds();

	public List<Fund> getTemporaryFunds();

	public List<Fund> getFundsByDeskId( int mopDeskId);

	public Fund getArdFund(int id, String server);
	
	public int getMaxFinancialIdForTemporary();


}
