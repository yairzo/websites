package huard.iws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import huard.iws.bean.PersonBean;
import huard.iws.db.ConferenceProposalDao;
import huard.iws.model.ConferenceProposal;

public class ConferenceProposalServiceImpl implements ConferenceProposalService{
	
	private List<ConferenceProposal> conferenceProposals;
	
	private Map<Integer,ConferenceProposal> conferenceProposalsMap;
	
	public ConferenceProposal getConferenceProposal( int id){
		return conferenceProposalDao.getConferenceProposal(id);
	}

	public ConferenceProposal getVersionConferenceProposal(int confId, int verId){
		return conferenceProposalDao.getVersionConferenceProposal(confId,verId);
	}

	public int getPreviousVersion(int confId, int verId){
		return conferenceProposalDao.getPreviousVersion(confId, verId);
	}

	public int getNextVersion(int confId, int verId){
		return conferenceProposalDao.getNextVersion(confId, verId);
	}

	public int getFirstVersion(int confId){
		return conferenceProposalDao.getFirstVersion(confId);
	}

	public int getLastVersion(int confId){
		return conferenceProposalDao.getLastVersion(confId);
	}

	public void updateConferenceProposal (ConferenceProposal conferenceProposal){
		conferenceProposalDao.updateConferenceProposal(conferenceProposal);
	}

	public int insertConferenceProposal (ConferenceProposal conferenceProposal){
		return conferenceProposalDao.insertConferenceProposal(conferenceProposal);
	}

	public void deleteConferenceProposal (int id){
		conferenceProposalDao.deleteConferenceProposal(id);
	}

	public List<ConferenceProposal> getConferenceProposals(){
		if (conferenceProposals==null){
			conferenceProposals =  conferenceProposalDao.getConferenceProposals();
		}
		return conferenceProposals;
	}

	public List<ConferenceProposal> getConferenceProposalsByPerson(int personId){
		if (conferenceProposals==null){
			conferenceProposals =  conferenceProposalDao.getConferenceProposalsByPerson(personId);
		}
		return conferenceProposals;
	}


	public Map<Integer, ConferenceProposal> getConferenceProposalsMap(){
		if (conferenceProposalsMap == null){
			conferenceProposalsMap = new HashMap<Integer, ConferenceProposal>();
			for (ConferenceProposal confproposal: getConferenceProposals()){
				conferenceProposalsMap.put(confproposal.getId(), confproposal);
			}
		}
		return conferenceProposalsMap;
	}

	public Map<Integer, ConferenceProposal> getConferenceProposalsByPersonMap(int personId){
		Map<Integer, ConferenceProposal> conferenceProposalsMap = new HashMap<Integer, ConferenceProposal>();
		for (ConferenceProposal confproposal: getConferenceProposals()){
			conferenceProposalsMap.put(confproposal.getId(), confproposal);
		}
		return conferenceProposalsMap;
	}
	
	public int getMaxGrade(int approverId){
		return conferenceProposalDao.getMaxGrade(approverId);
	}


	private ConferenceProposalDao conferenceProposalDao;

	public ConferenceProposalDao getConferenceProposalDao() {
		return conferenceProposalDao;
	}

	public void setConferenceProposalDao(ConferenceProposalDao conferenceProposalDao) {
		this.conferenceProposalDao = conferenceProposalDao;
	}


}
