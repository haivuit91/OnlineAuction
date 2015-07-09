package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;

public interface CategorySubService {

	public static final String NAME = "categorySubService";

	List<CategorySub> findAllCategorySub();
	
	Page<CategorySub> findCategorySubUsingPage(long categoryId, Pageable pageable);

	CategorySub findCategorySubById(long id);
	
	CategorySub findCategorySubByPath(String cateSubPath);
	
	List<CategorySub> findCategorySubByCategory(Category category);

	CategorySub saveCategorySub(CategorySub categorySub);

	boolean deleteCategorySub(long id);
	
	boolean deleteCategorySubByCategory(Category category);

}
