package huard.iws.service;

import huard.iws.util.DateUtils;

public class GeneralServiceImpl implements GeneralService{
	
	public String getLastUpdate(){
		long lastUpdateTime = Math.max(callForProposalService.getCallForProposalsLastUpdate().getTime(), 
				textualPageService.getTextualPagesLastUpdate().getTime());
		return DateUtils.formatDate(lastUpdateTime, "dd/MM/yyyy");
	}
	
	
	private CallForProposalService callForProposalService;


	public void setCallForProposalService(
			CallForProposalService callForProposalService) {
		this.callForProposalService = callForProposalService;
	}
	
	private TextualPageService textualPageService;


	public void setTextualPageService(TextualPageService textualPageService) {
		this.textualPageService = textualPageService;
	}
	
	

}
