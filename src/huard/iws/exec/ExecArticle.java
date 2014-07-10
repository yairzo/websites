package huard.iws.exec;

import huard.iws.service.ArticleService;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class ExecArticle {

	private ArticleService articleService;	
	
	public ExecArticle(){
		try{
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(ArticleService.class);
			factory.setServiceUrl("rmi://localhost:1199/ArticleService");
			factory.afterPropertiesSet();
			articleService = (ArticleService)factory.getObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main (String [] args){
		
		
		
		String usagePhrase = "Usage: ExecArticle findArticles|findAuthors [argument]";
		
		if (args.length < 1 || args.length > 2) {
			System.out.println(usagePhrase);
			System.exit(1);
		}
		
		ExecArticle execArticleService = new ExecArticle();
		if (execArticleService.getArticleService() == null) {
			System.out.println("Probably rmi lookup failed. exiting !");
			System.exit(1);
		}
		
		if (args[0].equals("findArticles"))
			findArticles(execArticleService.getArticleService());
		else if (args[0].equals("findAuthors")) {
			System.out.println("Not Implemented Yet");
			System.exit(1);
		}
		else {
			System.out.println(usagePhrase);
			System.exit(1);
		}
	}	

	private ArticleService getArticleService() {
		return this.articleService;
	}


	private static void findArticles(ArticleService as) {
		as.updateLeastUpdatedAuthor();
	}
	
}
