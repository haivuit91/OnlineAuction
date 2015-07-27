package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.BidService;
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
	
	@Autowired
	private BidService bidService;

	// @Autowired
	// private Mapper mapper;

	@ModelAttribute("allCategory")
	public List<Category> populateCategory() {
		return this.categoryService.findAllCategory();
	}
	
	@ModelAttribute("allCategorySub")
	public List<CategorySub> populateCategorySub() {
		return this.categorySubService.findAllCategorySub();
	}

	@RequestMapping(value = "dashboard")
	public String goDashboardPage(ModelMap model) {
		model.put("layout", "admin/dashboard");
		return Constants.TEMPLATE_ADMIN;
	}
	
	// ----------------- User management -----------------

	@RequestMapping(value = "user-management")
	public String goUserManagementPage(
			@PageableDefault(page = 1, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable, ModelMap model) {
		model = getListAccount(pageable, model);
		model.put("layout", "admin/user_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-user", method = RequestMethod.GET)
	public String addNewUser(ModelMap model, Account account) {
		model.put("action", "Add new User");
		model.put("layout", "admin/add_edit_user");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "edit-user/{accountId}", method = RequestMethod.GET)
	public String editUser(@PathVariable("accountId") long accountId, ModelMap model) {
		Account account = accountService.findAccountById(accountId);
		model.put("account", account);
		model.put("action", "Edit User");
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
			@PageableDefault(page = 1, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, ModelMap model) {
		model = getListCategory(pageable, model);
		model.put("layout", "admin/category_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-category", method = RequestMethod.GET)
	public String addNewCategory(Category category, ModelMap model) {
		model.put("action", "Add new Category");
		model.put("layout", "admin/add_edit_category");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "edit-category/{cateId}", method = RequestMethod.GET)
	public String editCategory(@PathVariable("cateId") long cateId, ModelMap model) {
		Category category = categoryService.findCategoryById(cateId);
		model.put("category", category);
		model.put("action", "Edit Category");
		model.put("layout", "admin/add_edit_category");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-category", method = RequestMethod.POST)
	public String saveCategory(@Valid Category category, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("action", "Add new Category");
			model.put("layout", "admin/add_edit_category");
			return Constants.TEMPLATE_ADMIN;
		}
		categoryService.saveCategory(category);
		return "redirect:/admin/category-management?success";
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
	
	// ----------------- Category sub management -----------------
	
	@RequestMapping(value = "list-category-sub/{categoryId}")
	public String goCategorySubPage(
			@PageableDefault(page = 1, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			@PathVariable("categoryId") long categoryId, ModelMap model) {
		model = getListCategorySub(categoryId, pageable, model);
		model.put("categoryId", categoryId);
		model.put("layout", "admin/category_sub_list");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-category-sub/{categoryId}", method = RequestMethod.GET)
	public String addNewCategorySub(@PathVariable("categoryId") long categoryId, CategorySub categorySub, ModelMap model) {
		model.put("categoryId", categoryId);
		model.put("action", "Add new Category sub");
		model.put("layout", "admin/add_edit_category_sub");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "edit-category-sub/{categoryId}/{cateSubId}", method = RequestMethod.GET)
	public String editCategorySub(@PathVariable("categoryId") long categoryId, @PathVariable("cateSubId") long cateSubId, ModelMap model) {
		CategorySub categorySub = categorySubService.findCategorySubById(cateSubId);
		model.put("categorySub", categorySub);
		model.put("categoryId", categoryId);
		model.put("action", "Edit Category sub");
		model.put("layout", "admin/add_edit_category_sub");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-category-sub/{categoryId}", method = RequestMethod.POST)
	public String saveCategorySub(@Valid CategorySub categorySub, @PathVariable("categoryId") long categoryId, 
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("action", "Add new Category sub");
			model.put("layout", "admin/add_edit_category_sub");
			return Constants.TEMPLATE_ADMIN;
		}
		categorySubService.saveCategorySub(categorySub, categoryId);
		return "redirect:/admin/list-category-sub/" + categoryId + "?success";
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
			@PageableDefault(page = 1, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, ModelMap model) {
		model = getListItem(pageable, model);
		model.put("layout", "admin/item_management");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "item-management/{itemId}")
	public String goItemDetailPage(ModelMap model, @PathVariable("itemId") long itemId) {
		Item item = itemService.findItemById(itemId);
		List<Bid> bidList =  bidService.findBidByItem(item);
		model.put("item", item);
		model.put("bidList", bidList);
		model.put("layout", "admin/item_detail");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-item", method = RequestMethod.GET)
	public String addNewItem(Item item, ModelMap model) {
		model.put("action", "Add new Item");
		model.put("layout", "admin/add_edit_item");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "edit-item/{itemId}", method = RequestMethod.GET)
	public String editItem(@PathVariable("itemId") long itemId, ModelMap model) {
		Item item = itemService.findItemById(itemId);
		model.put("item", item);
		model.put("action", "Edit Item");
		model.put("layout", "admin/add_edit_item");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "add-new-item", method = RequestMethod.POST)
	public String saveItem(@Valid Item item, @RequestParam("file") MultipartFile file, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("action", "Add new Item");
			model.put("layout", "admin/add_edit_item");
			return Constants.TEMPLATE_ADMIN;
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		itemService.saveItem(item, file, auth.getName());
		return "redirect:/admin/item-management?success";
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
	
	// ----------------- Info update -----------------
	
	@RequestMapping(value = "profile")
	public String goProfile(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountService.findAccountByEmail(auth.getName());
		model.put("account", account);
		model.put("layout", "admin/profile");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "profile", method = RequestMethod.POST)
	public String changeInfo(@Valid Account account, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("layout", "admin/profile");
			return Constants.TEMPLATE_ADMIN;
		}
		accountService.addNewAccount(account);
		return "redirect:/admin/profile";
	}
	
	@RequestMapping(value = "change-password")
	public String goChangePwdPage(ModelMap model) {
		model.put("layout", "admin/change_password");
		return Constants.TEMPLATE_ADMIN;
	}
	
	@RequestMapping(value = "change-password", method = RequestMethod.POST)
	public String changePwdPage(@RequestParam("currentPwd") String currentPwd, @RequestParam("newPwd") String newPwd, 
			@RequestParam("rePwd") String rePwd, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String msg = "";
		if (!accountService.changePassword(currentPwd, newPwd, auth.getName())) {
			model.put("errorCurrentPwd", true);
			msg = "Your current password was incorrect.";
		} else if (newPwd == "") {
			model.put("errorNewPwd", true);
			msg = "The new password is required and can't be empty.";
		} else if (!newPwd.equals(rePwd)) {
			model.put("errorRePwd", true);
			msg = "Your re enter password was incorrect.";
		} else {
			model.put("success", true);
			msg = "Change password successfully.";
		}
		model.put("changePwd", true);
		model.put("message", msg);
		model.put("layout", "admin/change_password");
		return Constants.TEMPLATE_ADMIN;
	}

}
