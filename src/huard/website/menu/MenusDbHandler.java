package huard.website.menu;

import java.sql.*;
import java.util.*;

import huard.website.baseDb.*;
import huard.website.util.*;

public class MenusDbHandler extends DbHandler {

	private final String MENUS_DB = "MENUS";

	public MenuCategory[] getCategories(String lang) {
		MenuCategory[] topMenuCategories = null;
		ManagedConnection connection = getConnection(MENUS_DB);
		try {
			Statement statement = connection.createStatement();
			lang = lang.substring(0, 1).toUpperCase() + lang.substring(1);
			String query = "SELECT categoryName,link,subTableName,backgroundImage,bgcolor FROM topMenu"
					+ lang + " WHERE appearsOnTopMenu=1 ORDER BY location";
			System.out.println(query);
			ResultSet resultSet = statement
					.executeQuery(query);			
			List<MenuCategory> categoriesList = new ArrayList<MenuCategory>();
			while (resultSet.next()) {
				MenuCategory category = new MenuCategory();
				category.setName(resultSet.getString("categoryName"));
				category.setLink(resultSet.getString("link"));
				category.setBackgroundImage(resultSet
						.getString("backgroundImage"));
				category.setBgcolor(resultSet.getString("bgcolor"));
				category.setTableName(resultSet.getString("subTableName"));
				categoriesList.add(category);

			}
			topMenuCategories = new MenuCategory[categoriesList.size()];
			for (int i = 0; i < topMenuCategories.length; i++) {
				topMenuCategories[i] = (MenuCategory) categoriesList.get(i);
				topMenuCategories[i].setSubCategoriesArray(getSubCategories(
						topMenuCategories[i].getName(),
						topMenuCategories[i].getTableName()));
			}
			statement.close();
		} catch (SQLException e) {
			System.out
					.print("huardSiteViewer.topMenu.DbHandler.getCategories(): "
							+ e);
			topMenuCategories = new MenuCategory[1];
			topMenuCategories[0] = new MenuCategory();
		}
		archiveConection(MENUS_DB, connection);
		return topMenuCategories;
	}

	public MenuCategory getCategoryByCategoryName(String categoryName,
			String lang) {
		MenuCategory category = new MenuCategory();
		ManagedConnection connection = getConnection(MENUS_DB);
		try {
			Statement statement = connection.createStatement();
			if (categoryName != null)
				categoryName = Utils
						.addBackslashBeforeDoubleQuots(categoryName);
			String query = "SELECT categoryName,link,subTableName,backgroundImage,bgcolor FROM topMenu"
					+ lang + " WHERE categoryName=\"" + categoryName + "\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				category.setName(resultSet.getString("categoryName"));
				category.setLink(resultSet.getString("link"));
				category.setBackgroundImage(resultSet
						.getString("backgroundImage"));
				category.setBgcolor(resultSet.getString("bgcolor"));
				category.setTableName(resultSet.getString("subTableName"));
				category.setSubCategoriesArray(getSubCategories(
						category.getName(), category.getTableName()));
			}
			statement.close();

		} catch (SQLException e) {
			System.out
					.print("huardSiteViewer.topMenu.DbHandler.getCategoryByCategoryName(): "
							+ e);
		}
		archiveConection(MENUS_DB, connection);
		return category;
	}

	public MenuSubCategory[] getSubCategories(String categoryName,
			String subTableName) {
		MenuSubCategory[] subCategories = null;
		ManagedConnection connection = getConnection(MENUS_DB);
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM " + subTableName
					+ " ORDER BY location;";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			List<MenuSubCategory> subCategoriesList = new ArrayList<MenuSubCategory>();
			while (resultSet.next()) {
				MenuSubCategory subCategory = new MenuSubCategory();
				subCategory.setName(resultSet.getString("categoryName"));
				subCategory.setLink(resultSet.getString("link"));
				if (subCategory.getLink().indexOf(".jsp") != -1
						&& resultSet.getBoolean("autoAddCategoryToLink")) {
					String categoryParamSeparator = subCategory.getLink()
							.contains("?") ? "&" : "?";
					subCategory.setLink(subCategory.getLink()
							+ categoryParamSeparator + "category="
							+ subTableName);
				}
				subCategoriesList.add(subCategory);
			}
			subCategories = new MenuSubCategory[subCategoriesList.size()];
			for (int i = 0; i < subCategories.length; i++) {
				subCategories[i] = (MenuSubCategory) subCategoriesList.get(i);
			}
			statement.close();
		} catch (SQLException e) {
			System.out
					.print("huardSiteViewer.topMenu.DbHandler.getSubCategories(): "
							+ e);
			subCategories = new MenuSubCategory[1];
			subCategories[0] = new MenuSubCategory();
		}
		archiveConection(MENUS_DB, connection);
		return subCategories;
	}

	public String getSubTableNameByCategory(String category, boolean heb) {
		String subTableName = "";
		ManagedConnection connection = getConnection(MENUS_DB);
		try {
			Statement statement = connection.createStatement();
			String table = heb ? "topMenuHeb" : "topMenuEng";
			String query = "SELECT subTableName FROM " + table
					+ " WHERE categoryName=\"" + category + "\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				subTableName = resultSet.getString(1);
			}

		} catch (SQLException e) {
			System.out
					.println("huardSiteViewer.topMenu.DbHandler.getSubTableNameByCategory(): "
							+ e);

		}
		archiveConection(MENUS_DB, connection);
		return subTableName;
	}
	
	public MenuCategory getCategoryByCategoryTableName(String categoryTableName,
			boolean heb, String caller) {
		System.out.println("Caller: " + caller + " categoryTableName: " + categoryTableName);
		MenuCategory category = new MenuCategory();
		ManagedConnection connection = getConnection(MENUS_DB);
		try {
			Statement statement = connection.createStatement();
			String tableName = "topMenu" + (heb ? "Heb" : "Eng");
			String query = "SELECT categoryName,link,subTableName,backgroundImage,bgcolor FROM  "
					+ tableName + " WHERE subTableName=\"" + categoryTableName + "\";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				category.setName(resultSet.getString("categoryName"));
				category.setLink(resultSet.getString("link"));
				category.setBackgroundImage(resultSet
						.getString("backgroundImage"));
				category.setBgcolor(resultSet.getString("bgcolor"));
				category.setTableName(resultSet.getString("subTableName"));
				category.setSubCategoriesArray(getSubCategories(
						category.getName(), category.getTableName()));
			}
			statement.close();

		} catch (SQLException e) {
			System.out
					.print("huardSiteViewer.topMenu.DbHandler.getCategoryByCategoryName(): "
							+ e);
		}
		archiveConection(MENUS_DB, connection);
		return category;
	}
}
