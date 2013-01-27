package huard.iws.db;

import huard.iws.model.Category;

import java.util.List;

public interface CategoryDao {

	public Category getRootCategory();

	public Category getCategory(int id);
	
	public Category getCategoryByUrl(String url);

	public List<Category> getCategories(int parentCategoryId);

	public void updateCategory(Category category);

	public int insertCategory(int parentId);

	public void deleteCategory(int id);

}
