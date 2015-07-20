package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/item/")
public class ItemController extends ShowPage {

	@Autowired
	private CategorySubService categorySubSv;

	@Autowired
	private ItemService itemSv;

	@ModelAttribute("allCategorySub")
	public List<CategorySub> populateCategorySub() {
		return this.categorySubSv.findAllCategorySub();
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String goItemManagementPage(@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.DESC) Pageable pageable,
			ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Page<Item> item = itemSv.findItemByAccountAndBidStatus(auth.getName(), true, pageable);
		model.put("listItem", item);
		model = showUserPage("My product", "user/item_list", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "add-new-item", method = RequestMethod.GET)
	public String goAddItemPage(Item item, ModelMap model) {
		model = showUserPage("Add new item", "user/add_edit_item", model);
		return Constants.TEMPLATE_HOME;
	}
	
	@RequestMapping(value = "edit-item/{itemId}", method = RequestMethod.GET)
	public String goEditItemPage(@PathVariable("itemId") long itemId, ModelMap model) {
		Item item = itemSv.findItemById(itemId);
		model.put("item", item);
		model = showUserPage("Edit item", "user/add_edit_item", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "save-item", method = RequestMethod.POST)
	public String saveItem(@Valid Item item, @RequestParam("file") MultipartFile file, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model = showUserPage("Add new item", "user/add_edit_item", model);
			return Constants.TEMPLATE_HOME;
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		itemSv.saveItem(item, file, auth.getName());
		return "redirect:/item/list?success";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean deleteItem(@RequestParam("itemId") long itemId) {
		boolean check = false;
		if(itemSv.deleteItem(itemId)) {
			check = true;
		}
		return check;
	}

}
