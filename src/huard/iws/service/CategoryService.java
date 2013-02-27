package huard.iws.service;

import huard.iws.model.Category;

import java.util.List;

public interface CategoryService {
	
	public Category getRootCategory(String localeId);

	public Category getCategory(int id);

	public Category getCategoryByUrl(String url);

	public int getCategoryIdByName(String name);
	
	public List<Category> getlanguageRootCategories(int rootCategoryId);
	
	public List<Category> getCategories(int parentCategoryId);

	public void updateCategory(Category category);

	public int insertCategory(int parentId);

	public void deleteCategory(int id);

	public void moveCategoryUp (int categoryId);

	public void moveCategoryDown (int categoryId);

	public Category getTopCategory(Category category,String localeId);

}
