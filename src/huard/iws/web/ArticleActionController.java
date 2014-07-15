package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.ArticleService;
import huard.iws.util.RequestWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Leon
 *
 */
public class ArticleActionController extends GeneralController {

	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	private static final Logger logger = Logger.getLogger(ArticleActionController.class);

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){
		
		if (request.isRequestHasParameter("actionCommand") && request.isRequestHasParameter("articleId")) {
			if (request.getParameter("actionCommand", "").equals("changeVisibility")) {
				int articleId = request.getIntParameter("articleId", 0);
				logger.info("Changing visibility for article ID: "+articleId);
				articleService.changeVisibility(articleId);
			}
		}
		
		return null;
	}


}