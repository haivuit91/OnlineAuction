package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;

public class ShowPage {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategorySubService categorySubService;
	
	@Autowired
	private AccountService accountService;

	public ModelMap showHomePage(String breadcrumbs, String layout, ModelMap model) {
		// Get list Category
		List<Category> listCategory = categoryService.findAllCategory();
		// Get list CategorySub
		List<CategorySub> listCategorySub = categorySubService.findAllCategorySub();
		// Set object view
		model.put("listCate", listCategory);
		model.put("listCateSub", listCategorySub);
		model.put("layout", layout);
		model.put("breadcrumbs", breadcrumbs);
		return model;
	}
	
	public ModelMap getListAccount(Pageable pageable, ModelMap model) {
		Page<Account> accountPage = accountService.findAllAccount(pageable);
		model.put("listAccount", accountPage);
		return model;
	}

}
