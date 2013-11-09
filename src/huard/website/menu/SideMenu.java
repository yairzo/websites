package huard.website.menu;

import huard.website.util.Utils;


public class SideMenu {

	private MenuCategory category;
	private boolean categoryChanged;
	private MenuSubCategory [] lowerSideMenuSubCategories;
	private MenusDbHandler dbHandler;	

	public SideMenu(){
		dbHandler = new MenusDbHandler();
	}

	public MenuSubCategory [] getUpperSideMenuSubCategories(String categoryTableName, boolean heb){
		this.category = getCategory(categoryTableName, heb);
		// handle the case of non exists category in category paramter
		System.out.println("category name: " + this.category.getName());
		if (this.category.getName().isEmpty())
			this.category = dbHandler.getCategoryByCategoryTableName(Utils.DEFAULT_PUB_PAGE_CATEGORY, Utils.HEB, "getUpperSideMenuSubCategories()");
		return this.category.getSubCategoriesArray();
	}


	public MenuSubCategory [] getLowerSideMenuSubCategories(String categoryName, boolean heb, boolean languageChanged){
		if (lowerSideMenuSubCategories==null || languageChanged || categoryChanged){
			//System.out.println("huardSiteViewer.topMenu.SideMenu.getLowerSideMenuSubCategories(): categoryChanged: " + categoryChanged);
			lowerSideMenuSubCategories = dbHandler.getSubCategories(categoryName,"lowerSideMenu"+ (heb ? "Heb" : "Eng"));
			categoryChanged = false;
		}
		return lowerSideMenuSubCategories;
	}

	public MenuSubCategory [] getSubCategories(String subTableName,boolean lang)
	{
		if(lang)
			return dbHandler.getSubCategories("","harashut_lemop");
			else
			return dbHandler.getSubCategories("","mainPage");

	}

	public MenuCategory getCategory(String categoryTableName, boolean heb){
		if (categoryTableName!=null && (this.category==null || ! this.category.getTableName().equals(categoryTableName))){
			this.category = dbHandler.getCategoryByCategoryTableName(categoryTableName, heb, "getCategory()");
			categoryChanged=true;
		}
		return this.category;
	}
}
