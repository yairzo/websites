package huard.iws.service;

import huard.iws.db.CallForProposalDaoOld;
import huard.iws.model.CallForProposalOld;
import huard.iws.util.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

public class CallForProposalServiceImplOld implements CallForProposalServiceOld{

	public CallForProposalOld getCallForProposal(int id){
		return callForProposalDaoOld.getCallForProposal(id, configurationService.getConfigurationString("website", "websiteDb"));
	}

	public CallForProposalOld getCallForProposal(String title){
		return callForProposalDaoOld.getCallForProposal(title, configurationService.getConfigurationString("website", "websiteDb"));
	}

	public List<CallForProposalOld> getCallForProposals(){
		return getCallForProposals(false);
	}

	public List<CallForProposalOld> getCallForProposals(boolean open){
		return callForProposalDaoOld.getCallForProposals(configurationService.getConfigurationString("website", "websiteDb"), open);
	}

	public List<CallForProposalOld> getCallForProposals(String localeId){
		List<CallForProposalOld> localeCallForProposals = new ArrayList<CallForProposalOld>();
		for (CallForProposalOld callForProposal: getCallForProposals(true)){

			if (LanguageUtils.getLanguage(callForProposal.getTitle()).getLocaleId().equals(localeId))
				localeCallForProposals.add(callForProposal);
		}
		return localeCallForProposals;
	}

	public void insertAuthorizedMD5(String md5){
		callForProposalDaoOld.insertAuthorizedMD5(md5, configurationService.getConfigurationString("website", "websiteDb"));
	}

	private CallForProposalDaoOld callForProposalDaoOld;

	public void setCallForProposalDaoOld(CallForProposalDaoOld callForProposalDaoOld) {
		this.callForProposalDaoOld = callForProposalDaoOld;
	}

	private ConfigurationService configurationService;

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
