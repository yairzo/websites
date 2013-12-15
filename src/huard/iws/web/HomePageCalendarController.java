package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class HomePageCalendarController extends GeneralWebsiteController {


	protected ModelAndView handleRequestWebsite(
			RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean personBean) throws Exception
	{
	
		return new ModelAndView ("homePageCalendar",model);
	}

}
