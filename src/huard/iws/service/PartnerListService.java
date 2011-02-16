package huard.iws.service;

import huard.iws.model.Partner;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;


public interface PartnerListService {

	public List<Partner> getPartners();

	public LinkedHashSet<String> getPartnersNames();

	public LinkedHashMap<String, Partner> getPartnersMap();

	public Partner getPartnerByName(String partnerName);





}
