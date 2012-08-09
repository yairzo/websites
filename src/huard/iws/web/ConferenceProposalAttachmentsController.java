package huard.iws.web;

import huard.iws.bean.ConferenceProposalBean;
import huard.iws.bean.PersonBean;
import huard.iws.model.FinancialSupport;
import huard.iws.service.ConferenceProposalService;
import huard.iws.util.RequestWrapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ConferenceProposalAttachmentsController extends GeneralFormController{

	
	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
	throws Exception{
		logger.info("111111111111111111111111111111 id:" + request.getIntParameter("cpid", 0));
		ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean(conferenceProposalService.getConferenceProposal(request.getIntParameter("cpid", 0)));
		
	
		// this part saves the content type of the attachments
		if (request.getRequest().getContentType().indexOf("multipart")!=-1){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request.getRequest();
			Iterator fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				
				String filename = (String) fileNames.next();
				System.out.println("******************************");
				System.out.println(" filename : " + filename );
				System.out.println("******************************");
				MultipartFile file = multipartRequest.getFile(filename);
				if (filename.equals("guestsAttach") && file.getSize()>0){
					conferenceProposalBean.setGuestsAttach(file.getBytes());
					conferenceProposalBean.setGuestsAttachContentType(file.getContentType());
				}
				else if (filename.equals("programAttach") && file.getSize()>0){
					conferenceProposalBean.setProgramAttach(file.getBytes());
					conferenceProposalBean.setProgramAttachContentType(file.getContentType());
				}
				else if (filename.equals("financialAttach") && file.getSize()>0){
					conferenceProposalBean.setFinancialAttach(file.getBytes());
					conferenceProposalBean.setFinancialAttachContentType(file.getContentType());
				}
				else if (filename.equals("companyAttach") && file.getSize()>0){
					conferenceProposalBean.setCompanyAttach(file.getBytes());
					conferenceProposalBean.setCompanyAttachContentType(file.getContentType());
				}
				else if (filename.startsWith("fromAssosiate")){
					//We have to handle the binding, no auto binding here
					//String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					String aIndex = request.getParameter("attachIndex", "");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromAssosiate().size()){
						FinancialSupport financialSupport = conferenceProposalBean.getFromAssosiate().get(index);
						if (financialSupport != null){
							financialSupport.setReferenceFile(file.getBytes());
							financialSupport.setFileContentType(file.getContentType());
						}
					}				
				}
				else if (filename.startsWith("fromExternal")){
					//We have to handle the binding, no auto binding here
					//String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					String aIndex = request.getParameter("attachIndex", "");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromExternal().size()){
						FinancialSupport financialSupport = conferenceProposalBean.getFromExternal().get(index);
						if (financialSupport != null){
							financialSupport.setReferenceFile(file.getBytes());
							financialSupport.setFileContentType(file.getContentType());
						}
					}				
				}
				else if (filename.startsWith("fromAdmitanceFee") && file.getSize()>0){
					//String aIndex = filename.replaceFirst("^.*?\\[([\\d]+)\\].*?$","$1");
					String aIndex = request.getParameter("attachIndex", "");
					int index = Integer.parseInt(aIndex);
					if (index < conferenceProposalBean.getFromAdmitanceFee().size()){
						FinancialSupport financialSupport = conferenceProposalBean.getFromAdmitanceFee().get(index);
						if (financialSupport != null){
							financialSupport.setReferenceFile(file.getBytes());
							financialSupport.setFileContentType(file.getContentType());
						}
					}				
				}
			}
		}		
		
		conferenceProposalService.updateConferenceProposal(conferenceProposalBean.toConferenceProposal());

		
		//return to same page
		if (request.getBooleanParameter("ajaxSubmit", false)){
			return null;
		}
			
		Map<String, Object> newModel = new HashMap<String, Object>();
		newModel.put("id", conferenceProposalBean.getId())	;
		return new ModelAndView(new RedirectView("conferenceProposal.html"), newModel);
	}
	
	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception	{

			return new ModelAndView ( this.getFormView(), model);
		
	}
	
	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{

		ConferenceProposalBean conferenceProposalBean = new ConferenceProposalBean();
	
		return conferenceProposalBean;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class,	new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}	
	
	private ConferenceProposalService conferenceProposalService;

	public void setConferenceProposalService(ConferenceProposalService conferenceProposalService) {
		this.conferenceProposalService = conferenceProposalService;
	}
	


}
