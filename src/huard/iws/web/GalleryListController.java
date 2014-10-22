package huard.iws.web;

import huard.iws.bean.ImageGalleryItemBean;
import huard.iws.bean.PersonBean;
import huard.iws.bean.FundBean;
import huard.iws.bean.RegistrationFormBean;
import huard.iws.service.FundService;
import huard.iws.service.ImageGalleryService;
import huard.iws.util.ListView;
import huard.iws.util.RequestWrapper;
import huard.iws.util.SearchCreteria;
import huard.iws.model.Fund;
import huard.iws.model.ImageGalleryItem;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class GalleryListController extends GeneralFormController {


	protected ModelAndView onSubmit(Object command,
			Map<String, Object> model, RequestWrapper request, PersonBean userPersonBean)
			throws Exception{
		Map<String,Object> newModel = new HashMap<String, Object>();
		String action=request.getParameter("action","");
		if(action.equals("save")){
			ImageGalleryItem imageGallery = new ImageGalleryItem();
			imageGallery.setText(request.getParameter("galleryName",""));
			imageGallery.setParentId(0);
			imageGallery.setLevel(0);
			imageGallery.setIsLink(request.getIntParameter("galleryType",0)==0?false:true);
			imageGalleryService.insertImageGalleryItem(imageGallery, userPersonBean);
		}
		if(action.equals("update")){
			ImageGalleryItem imageGallery = imageGalleryService.getImageGalleryItem(request.getIntParameter("category", 0), userPersonBean);
			imageGallery.setText(request.getParameter("gallery"+request.getIntParameter("category", 0),""));
			imageGalleryService.updateImageGalleryItem(imageGallery, userPersonBean);
		}
		if(action.equals("delete")){
			imageGalleryService.deleteImageGalleryItem(request.getIntParameter("category", 0), userPersonBean);
		}
		return new ModelAndView(new RedirectView("galleryList.html"),newModel);
	}

	protected ModelAndView onShowForm(RequestWrapper request, HttpServletResponse response,
			PersonBean userPersonBean, Map<String, Object> model) throws Exception
	{

		List<ImageGalleryItem> galleries = imageGalleryService.getGalleries();
		model.put("galleries", galleries);

		return new ModelAndView ("galleryList",model);
	}

	protected Object getFormBackingObject(
			RequestWrapper request, PersonBean userPersonBean) throws Exception{
		FundListControllerCommand command = new FundListControllerCommand();
		return command;
	}

	public class FundListControllerCommand{
		private SearchCreteria searchCreteria = new SearchCreteria();
		private ListView listView = new ListView();

		public SearchCreteria getSearchCreteria() {
			return searchCreteria;
		}
		public void setSearchCreteria(SearchCreteria searchCreteria) {
			this.searchCreteria = searchCreteria;
		}
		public ListView getListView() {
			return listView;
		}
		public void setListView(ListView listView) {
			this.listView = listView;
		}

	}
	
	private ImageGalleryService imageGalleryService;

	public void setImageGalleryService(ImageGalleryService imageGalleryService) {
		this.imageGalleryService = imageGalleryService;
	}

}
