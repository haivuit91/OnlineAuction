package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asiantech.haivu.onlineauction.model.Category;

public interface CategoryService {

	public static String NAME = "categoryService";

	List<Category> findAllCategory();
	
	Page<Category> findCategoryUsingPage(Pageable pageable);

	Category findCategoryById(long id);

	Category saveCategory(Category category);

	boolean deleteCategory(long id);

}
