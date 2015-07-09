package com.asiantech.haivu.onlineauction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/admin/")
public class AdminController extends ShowPage {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategorySubService categorySubService;
	
	@Autowired
	private ItemService itemService;

	// @Autowired
	// private Mapper mapper;

	@RequestMapping(value = "dashboard")
	public String goDashboardPage(ModelMap model) {
		model.put("layout", "admin/dashboard");
		return Constants.TEMPLATE_ADMIN;
	}
	
	// ----------------- User management -----------------

	@RequestMapping(value = "user-management")
	public String goUserManagementPage(
			@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.DESC) Pageable pageable, ModelMap model) {
		model = getListAccount(pageable, model);
		model.put("layout", "admin/user_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-user", method = RequestMethod.GET)
	public String addNewUser(ModelMap model, Account account) {
		model.put("layout", "admin/add_edit_user");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "edit-user/{accountId}", method = RequestMethod.GET)
	public String editUser(@PathVariable("accountId") long accountId, ModelMap model) {
		Account account = accountService.findAccountById(accountId);
		model.put("account", account);
		model.put("layout", "admin/add_edit_user");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-user", method = RequestMethod.POST)
	public String saveUser(@Valid Account account, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("layout", "admin/add_edit_user");
			return Constants.TEMPLATE_ADMIN;
		}
		// Account accountEntity = mapper.map(account, Account.class);
		accountService.addNewAccount(account);
		return "redirect:/admin/user-management";
	}
	
	@RequestMapping(value = "delete-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean delUser(@RequestParam("accountId") long accountId) {
		boolean check = false;
		if(accountService.deleteAccount(accountId)){
			check = true;
		}
		return check;
	}
	
	// ----------------- Category management -----------------
	
	@RequestMapping(value = "category-management")
	public String goCategoryManagementPage(
			@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable, ModelMap model) {
		model = getListCategory(pageable, model);
		model.put("layout", "admin/category_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value="delete-category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean deleteCategory(@RequestParam("cateId") long cateId) {
		boolean check = false;
		if(categoryService.deleteCategory(cateId)) {
			check = true;
		}
		return check;
	}
	
	@RequestMapping(value = "list-category-sub/{categoryId}")
	public String goCategorySubPage(
			@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable,
			@PathVariable("categoryId") long categoryId, ModelMap model) {
		model = getListCategorySub(categoryId, pageable, model);
		model.put("layout", "admin/category_sub_list");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "delete-category-sub", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean deleteCategorySub(@RequestParam("cateSubId") long cateSubId) {
		boolean check = false;
		if(categorySubService.deleteCategorySub(cateSubId)) {
			check = true;
		}
		return check;
	}
	
	// ----------------- Item management -----------------
	
	@RequestMapping(value = "item-management")
	public String goItemManagementPage(
			@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable, ModelMap model) {
		model = getListItem(pageable, model);
		model.put("layout", "admin/item_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "delete-item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean deleteItem(@RequestParam("itemId") long itemId) {
		boolean check = false;
		if(itemService.deleteItem(itemId)) {
			check = true;
		}
		return check;
	}

}
