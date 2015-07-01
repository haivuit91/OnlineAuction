package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.Category;

public interface CategoryService {

	public static String NAME = "categoryService";

	List<Category> findAllCategory();

	Category findCategoryById(long id);

	Category saveCategory(Category category);

	Category deleteCategory(long id);

}
