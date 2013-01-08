package huard.iws.service;

import huard.iws.db.CategoryDao;
import huard.iws.model.Category;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{

	public Category getRootCategory(){
		return categoryDao.getRootCategory();
	}

	public Category getCategory(int id){
		Category category = categoryDao.getCategory(id);
		category.setSubCategories(getCategories(id));
		return category;
	}
	
	public List<Category> getlanguageRootCategories(int rootCategoryId){
		return categoryDao.getCategories(rootCategoryId);
	}
	public List<Category> getCategories(int parentCategoryId){
		List<Category> categories = categoryDao.getCategories(parentCategoryId);
		for (Category category : categories){
			category.setSubCategories(getCategories(category.getId()));
		}
		return categories;
	}

	public void updateCategory(Category category){
		categoryDao.updateCategory(category);
	}
	
	public int insertCategory(int parentId){
		return categoryDao.insertCategory(parentId);
	}
	public void deleteCategory(int id){
		categoryDao.deleteCategory(id);
	}
	public void moveCategoryUp (int categoryId){
		Category category = getCategory(categoryId);
		List<Category> categories = getCategories(category.getParentId());
		for (Category aCategory : categories){
			int aCategoryPlace;
			if (category.getCategoryOrder() - (aCategoryPlace = aCategory.getCategoryOrder()) == 1){
				aCategory.setCategoryOrder(category.getCategoryOrder());
				category.setCategoryOrder(aCategoryPlace);
				updateCategory(category);
				updateCategory(aCategory);
				break;
			}
		}
	}

	public void moveCategoryDown (int categoryId){
		Category category = getCategory(categoryId);
		List<Category> categories = getCategories(category.getParentId());
		for (Category aCategory : categories){
			int aCategoryPlace;
			if ((aCategoryPlace = aCategory.getCategoryOrder()) - category.getCategoryOrder() == 1){
				aCategory.setCategoryOrder(category.getCategoryOrder());
				category.setCategoryOrder(aCategoryPlace);
				updateCategory(category);
				updateCategory(aCategory);
				break;
			}
		}
	}
	
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

}
