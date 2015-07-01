package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.CategorySub;

public interface CategorySubService {

	public static final String NAME = "categorySubService";

	List<CategorySub> findAllCategorySub();

	CategorySub findCategorySubbyId(long id);

	CategorySub saveCategorySub(CategorySub categorySub);

	CategorySub deleteCategorySub(long id);

}
