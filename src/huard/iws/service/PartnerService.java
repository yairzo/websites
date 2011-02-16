package huard.iws.service;

import huard.iws.model.Partner;

public interface PartnerService {

	public int insertPartner( Partner partner);

	public void updatePartner (Partner partner);

	public Partner getPartner(int id);

}
