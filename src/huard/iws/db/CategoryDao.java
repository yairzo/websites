package huard.iws.db;

import huard.iws.model.Category;

import java.util.List;

public interface CategoryDao {

	public Category getRootCategory(String localeId);

	public Category getCategory(int id);
	
	public Category getCategoryByUrl(String url);

	public int getCategoryIdByName(String name);

	public List<Category> getCategories(int parentCategoryId);

	public void updateCategory(Category category);

	public int insertCategory(int parentId);

	public void deleteCategory(Category category);

}
