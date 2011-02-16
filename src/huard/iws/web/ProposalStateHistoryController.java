package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.bean.ProposalStateBean;
import huard.iws.model.ProposalState;
import huard.iws.service.ProposalStateHistoryService;
import huard.iws.util.RequestWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


public class ProposalStateHistoryController extends GeneralController{

	//private static final Logger logger = Logger.getLogger(ProposalStateHistoryController.class);

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){

		List<ProposalStateBean> proposalStateBeans = new ArrayList<ProposalStateBean>();
		int proposalId = request.getIntParameter("proposalId", 0);
		if (proposalId > 0){
			List<ProposalState> proposalStates = proposalStateHistoryService.getProposalStates(proposalId);
			for (ProposalState proposalState: proposalStates){
				proposalStateBeans.add(new ProposalStateBean(proposalState));
			}
		}
		model.put("proposalStates", proposalStateBeans);
		return new ModelAndView ("proposalStateHistory", model);
	}


	private ProposalStateHistoryService proposalStateHistoryService;

	public void setProposalStateHistoryService(
			ProposalStateHistoryService proposalStateHistoryService) {
		this.proposalStateHistoryService = proposalStateHistoryService;
	}

}
