package huard.website.menu;

public class TopMenu {

	private MenuCategory [] categoriesArrayHeb;
	private MenuCategory [] categoriesArrayEng;
	
	private MenusDbHandler dbHandler;

	public TopMenu (){
		dbHandler = new MenusDbHandler();
	}

	public MenuCategory[] getCategoriesArray(String lang){
		MenuCategory [] categoriesArray = null;
		if (lang.equals("heb")){
			if (categoriesArrayHeb == null){
				categoriesArrayHeb = dbHandler.getCategories(lang);
				calculateCategoriesWidths(lang);
			}
			categoriesArray = categoriesArrayHeb;
		}
		else if (lang.equals("eng")){
			if (categoriesArrayEng == null){
				categoriesArrayEng = dbHandler.getCategories(lang);
				calculateCategoriesWidths(lang);
			}
			categoriesArray = categoriesArrayEng;
		}		
		return categoriesArray;
	}

	private void calculateCategoriesWidths(String lang){
		MenuCategory [] categoriesArray = null;
		if (lang.equals("heb"))
			categoriesArray = categoriesArrayHeb;
		else if (lang.equals("eng"))
			categoriesArray = categoriesArrayEng;
		double totalSize=0;
		for (MenuCategory menuCategory: categoriesArray){
			totalSize += menuCategory.getName().length() + 4.5;
		}
		for(MenuCategory menuCategory: categoriesArray){
			double categoryCategoryWidth = (double)((menuCategory.getName().length()+4.5)/totalSize)*930;
			menuCategory.setWidth((int)categoryCategoryWidth);
		}		
	}

	public String markApostrofWithBackSlash(String str){
				return str.replaceAll("'","\\\\'");
	}
}
