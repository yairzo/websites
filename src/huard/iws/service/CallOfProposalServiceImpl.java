package huard.iws.service;

import huard.iws.db.CallOfProposalDao;
import huard.iws.model.CallOfProposal;
import huard.iws.util.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

public class CallOfProposalServiceImpl implements CallOfProposalService{

	public CallOfProposal getCallOfProposal(int id){
		return callOfProposalDao.getCallOfProposal(id, configurationService.getConfigurationString("websiteDb"));
	}

	public CallOfProposal getCallOfProposal(String title){
		return callOfProposalDao.getCallOfProposal(title, configurationService.getConfigurationString("websiteDb"));
	}

	public List<CallOfProposal> getCallsOfProposals(){
		return getCallsOfProposals(false);
	}

	public List<CallOfProposal> getCallsOfProposals(boolean open){
		return callOfProposalDao.getCallsOfProposals(configurationService.getConfigurationString("websiteDb"), open);
	}

	public List<CallOfProposal> getCallsOfProposals(String localeId){
		List<CallOfProposal> localeCallsOfProposals = new ArrayList<CallOfProposal>();
		for (CallOfProposal callOfProposal: getCallsOfProposals(true)){

			if (LanguageUtils.getLanguage(callOfProposal.getTitle()).getLocaleId().equals(localeId))
				localeCallsOfProposals.add(callOfProposal);
		}
		return localeCallsOfProposals;
	}

	public void insertAuthorizedMD5(String md5){
		callOfProposalDao.insertAuthorizedMD5(md5, configurationService.getConfigurationString("websiteDb"));
	}

	private CallOfProposalDao callOfProposalDao;

	public void setCallOfProposalDao(CallOfProposalDao callOfProposalDao) {
		this.callOfProposalDao = callOfProposalDao;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
