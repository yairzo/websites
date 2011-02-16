package huard.iws.service;

import huard.iws.db.PartnerListDao;
import huard.iws.model.Partner;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class PartnerListServiceImpl implements PartnerListService{

	public List<Partner> getPartners(){
		return partnerListDao.getPartners();
	}

	public LinkedHashMap<String, Partner> getPartnersMap(){
		List<Partner> partners = getPartners();
		LinkedHashMap<String, Partner> partnersMap = new LinkedHashMap<String, Partner>();
		for (Partner partner: partners){
			partnersMap.put(partner.getName(), partner);
		}
		return partnersMap;
	}

	public Partner getPartnerByName(String partnerName){
		return getPartnersMap().get(partnerName);
	}

	public LinkedHashSet<String> getPartnersNames(){
		LinkedHashSet<String> partners = new LinkedHashSet<String>();
		for (Partner partner: partnerListDao.getPartners()){
			partners.add(partner.getName());
		}
		return partners;
	}








	private PartnerListDao partnerListDao;

	public void setPartnerListDao(PartnerListDao partnerListDao) {
		this.partnerListDao = partnerListDao;
	}

}
