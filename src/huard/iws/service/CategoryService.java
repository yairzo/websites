package huard.iws.service;

import huard.iws.model.Category;

import java.util.List;

public interface CategoryService {
	
	public Category getRootCategory();

	public Category getCategory(int id);

	public List<Category> getlanguageRootCategories(int rootCategoryId);
	
	public List<Category> getCategories(int parentCategoryId);

	public void updateCategory(Category category);

	public int insertCategory(int parentId);

	public void deleteCategory(int id);

	public void moveCategoryUp (int categoryId);

	public void moveCategoryDown (int categoryId);

}
