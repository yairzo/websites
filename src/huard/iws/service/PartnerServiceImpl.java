package huard.iws.service;

import huard.iws.db.PartnerDao;
import huard.iws.model.Partner;

public class PartnerServiceImpl implements PartnerService{

	public int insertPartner (Partner partner){
		return partnerDao.insertPartner(partner);
	}

	public void updatePartner (Partner partner){
		partnerDao.updatePartner(partner);
	}

	public Partner getPartner(int id){
		return partnerDao.getPartner(id);
	}

	private PartnerDao partnerDao;

	public void setPartnerDao(PartnerDao partnerDao) {
		this.partnerDao = partnerDao;
	}

}
