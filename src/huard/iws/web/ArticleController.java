package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.model.Article;
import huard.iws.service.ArticleService;
import huard.iws.util.LanguageUtils;
import huard.iws.util.RequestWrapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Leon
 *
 */
public class ArticleController extends GeneralController {

	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	private static final Logger logger = Logger.getLogger(ArticleController.class);

	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map<String, Object> model, PersonBean userPersonBean){
		

		// TODO filter which articles to show
		List<Article> articles = articleService.getArticles();
		
		model.put("listOfArticles", articles);
		logger.debug("Displaying "+articles.size()+" articles");
		LanguageUtils.applyLanguage(model, request, response, "iw_IL");

		return new ModelAndView ("articles", model);
	}


}