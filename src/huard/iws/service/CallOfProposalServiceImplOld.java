package huard.iws.service;

import huard.iws.db.CallOfProposalDaoOld;
import huard.iws.model.CallOfProposalOld;
import huard.iws.util.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

public class CallOfProposalServiceImplOld implements CallOfProposalServiceOld{

	public CallOfProposalOld getCallOfProposal(int id){
		return callOfProposalDaoOld.getCallOfProposal(id, configurationService.getConfigurationString("websiteDb"));
	}

	public CallOfProposalOld getCallOfProposal(String title){
		return callOfProposalDaoOld.getCallOfProposal(title, configurationService.getConfigurationString("websiteDb"));
	}

	public List<CallOfProposalOld> getCallsOfProposals(){
		return getCallsOfProposals(false);
	}

	public List<CallOfProposalOld> getCallsOfProposals(boolean open){
		return callOfProposalDaoOld.getCallsOfProposals(configurationService.getConfigurationString("websiteDb"), open);
	}

	public List<CallOfProposalOld> getCallsOfProposals(String localeId){
		List<CallOfProposalOld> localeCallsOfProposals = new ArrayList<CallOfProposalOld>();
		for (CallOfProposalOld callOfProposal: getCallsOfProposals(true)){

			if (LanguageUtils.getLanguage(callOfProposal.getTitle()).getLocaleId().equals(localeId))
				localeCallsOfProposals.add(callOfProposal);
		}
		return localeCallsOfProposals;
	}

	public void insertAuthorizedMD5(String md5){
		callOfProposalDaoOld.insertAuthorizedMD5(md5, configurationService.getConfigurationString("websiteDb"));
	}

	private CallOfProposalDaoOld callOfProposalDaoOld;

	public void setCallOfProposalDaoOld(CallOfProposalDaoOld callOfProposalDaoOld) {
		this.callOfProposalDaoOld = callOfProposalDaoOld;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
