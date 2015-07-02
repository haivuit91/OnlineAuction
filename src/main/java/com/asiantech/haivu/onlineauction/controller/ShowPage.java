package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;

public class ShowPage {

	@Autowired
	private CategoryService categorySv;

	@Autowired
	private CategorySubService categorySubSv;

	public ModelMap showHomePage(String breadcrumbs, String layout, ModelMap model) {
		// Get list Category
		List<Category> listCategory = categorySv.findAllCategory();
		// Get list CategorySub
		List<CategorySub> listCategorySub = categorySubSv.findAllCategorySub();
		// Set object view
		model.put("listCate", listCategory);
		model.put("listCateSub", listCategorySub);
		model.put("layout", layout);
		model.put("breadcrumbs", breadcrumbs);
		return model;
	}

}
