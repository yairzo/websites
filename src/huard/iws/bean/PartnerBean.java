package huard.iws.bean;

import huard.iws.model.Institute;
import huard.iws.model.Partner;
import huard.iws.service.InstituteService;
import huard.iws.util.ApplicationContextProvider;

public class PartnerBean {

	private int id;
	private String degree;
	private String name;
	private int instituteId;

	private InstituteBean instituteBean;
	private String instituteName;
	private InstituteBean additionalIntituteBean;

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public PartnerBean(){
		this.id = 0;
		this.degree = "";
		this.name = "";
		this.instituteId = 0;
	}

	public PartnerBean(Partner partner){
		this.id = partner.getId();
		this.degree = partner.getDegree();
		this.name = partner.getName();
		this.instituteId = partner.getInstituteId();
	}

	public Partner toPartner(){
		Partner partner = new Partner();
		partner.setId(id);
		partner.setDegree(degree);
		partner.setName(name);
		partner.setInstituteId(instituteId);
		return partner;
	}

	public String getFullDegreeName(){
		return this.degree+" "+this.name;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public InstituteBean getInstituteBean() {
		if (instituteBean == null){
			Object obj = ApplicationContextProvider.getContext().getBean("instituteService");
			InstituteService instituteService = (InstituteService)obj;
			Institute institute = instituteService.getInstitute(this.instituteId);
			this.instituteBean = new InstituteBean(institute);
		}
		return instituteBean;
	}

	public void setInstituteBean(InstituteBean instituteBean) {
		this.instituteBean = instituteBean;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public InstituteBean getAdditionalIntituteBean() {
		return additionalIntituteBean;
	}

	public void setAdditionalIntituteBean(InstituteBean additionalIntituteBean) {
		this.additionalIntituteBean = additionalIntituteBean;
	}



}
