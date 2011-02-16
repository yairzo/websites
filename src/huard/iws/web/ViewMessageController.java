package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ViewMessageController  extends GeneralController{

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
		Map<String, Object> model, PersonBean userPersonBean){
		model.put("userPersonBean", userPersonBean);
		return new ModelAndView("viewMessage",model);
	}

}
