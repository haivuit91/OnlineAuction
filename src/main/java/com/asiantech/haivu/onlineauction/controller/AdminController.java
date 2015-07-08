package com.asiantech.haivu.onlineauction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/admin/")
public class AdminController extends ShowPage {
	
	@Autowired
	private AccountService accountService;

//	@Autowired
//	private Mapper mapper;

	@RequestMapping(value = "dashboard")
	public String goDashboardPage(ModelMap model) {
		model.put("layout", "admin/dashboard");
		return Constants.TEMPLATE_ADMIN;
	}
	
	// ----------------- User management -----------------

	@RequestMapping(value = "user-management")
	public String goUserManagementPage(
			@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.DESC) Pageable pageable,
			Account account, ModelMap model) {
		model = getListAccount(pageable, model);
		model.put("layout", "admin/user_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-user", method = RequestMethod.GET)
	public String addNewUser(ModelMap model, Account account) {
		model.put("layout", "admin/add_edit_user");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-user", method = RequestMethod.POST)
	public String saveUser(@Valid Account account, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("layout", "admin/add_edit_user");
			return Constants.TEMPLATE_ADMIN;
		}
//		Account accountEntity = mapper.map(account, Account.class);
		accountService.addNewAccount(account);
		return "redirect:/admin/user-management";
	}
	
	// ----------------- Category management -----------------
	
	@RequestMapping(value = "category-management")
	public String goCategoryManagementPage(ModelMap model) {
		model.put("layout", "admin/category_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	// ----------------- Item management -----------------
	
	@RequestMapping(value = "item-management")
	public String goItemManagementPage(ModelMap model) {
		model.put("layout", "admin/item_management");
		return Constants.TEMPLATE_ADMIN;
	}

}
