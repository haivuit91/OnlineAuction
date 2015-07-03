package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.CategorySub;

public interface CategorySubService {

	public static final String NAME = "categorySubService";

	List<CategorySub> findAllCategorySub();

	CategorySub findCategorySubById(long id);
	
	CategorySub findCategorySubByPath(String cateSubPath);

	CategorySub saveCategorySub(CategorySub categorySub);

	CategorySub deleteCategorySub(long id);

}
