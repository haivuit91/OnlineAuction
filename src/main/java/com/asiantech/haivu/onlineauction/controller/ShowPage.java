package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;

public class ShowPage {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategorySubService categorySubService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ItemService itemService;

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
	
	public ModelMap getListCategory(Pageable pageable, ModelMap model) {
		Page<Category> categoryPage = categoryService.findCategoryUsingPage(pageable);
		model.put("listCategory", categoryPage);
		return model;
	}
	
	public ModelMap getListCategorySub(long categoryId, Pageable pageable, ModelMap model) {
		Page<CategorySub> categorySubPage = categorySubService.findCategorySubUsingPage(categoryId, pageable);
		model.put("listCategorySub", categorySubPage);
		return model;
	}
	
	public ModelMap getListItem(Pageable pageable, ModelMap model) {
		Page<Item> listItem = itemService.findAllItem(pageable);
		model.put("listItem", listItem);
		return model;
	}

}
