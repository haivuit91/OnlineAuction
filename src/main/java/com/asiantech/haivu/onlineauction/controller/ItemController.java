package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategoryService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.util.Constants;
import com.asiantech.haivu.onlineauction.util.HandleImage;

@Controller
@RequestMapping("/item/")
public class ItemController {

	@Autowired
	private CategoryService categorySv;

	@Autowired
	private CategorySubService categorySubSv;

	@Autowired
	private AccountService accountSv;

	@Autowired
	private ItemService itemSv;

	@Autowired
	private HandleImage handleImg;

	@ModelAttribute("allCategorySub")
	public List<CategorySub> populateCategorySub() {
		return this.categorySubSv.findAllCategorySub();
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String goItemManagementPage(Integer page, Integer size,
			ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		// TODO move to service
		int pageDefault = (page != null) ? page - 1 : 0;
		int sizeDefault = (size != null) ? size : 5;

		Account account = accountSv.findAccountByEmail(auth.getName());

		Page<Item> item = itemSv.findItemByAccountAndBidStatus(account, true,
				new PageRequest(pageDefault, sizeDefault, new Sort(new Order(
						Direction.DESC, "id"))));

		// TODO using prop in Page of spring
		int current = item.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, item.getTotalPages());

		model.put("listItem", item);
		model.put("beginIndex", begin);
		model.put("endIndex", end);
		model.put("currentIndex", current);

		return showHomePage("My product", "user/item_list", model);
	}

	@RequestMapping(value = "add-new-item", method = RequestMethod.GET)
	public String goAddItemPage(Item item, ModelMap model) {
		return showHomePage("Add new item", "user/add_edit_item", model);
	}

	@RequestMapping(value = "add-new-item", method = RequestMethod.POST)
	public String saveItem(@Valid Item item,
			@RequestParam("file") MultipartFile file,
			BindingResult bindingResult, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String imageName = "default.jpg";
		Account account = accountSv.findAccountByEmail(auth.getName());

		// TODO move head function
		if (bindingResult.hasErrors()) {
			return showHomePage("Add new item", "user/add_edit_item", model);
		}
		if (handleImg.uploadFileHandler(file)) {
			imageName = file.getOriginalFilename();
		}

		// TODO execution in service
		Item itemTmp = new Item(item.getItemTitle(), item.getItemDesciption(),
				imageName, item.getMinimumBid(), item.getBidIncremenent(),
				item.getBidStartDate(), item.getBidEndDate(), account,
				null);
		itemSv.saveItem(itemTmp);
		model.put("msg", "Add new Item successfully");
		return "redirect:/item/list?success";
	}

	public String showHomePage(String breadcrumbs, String layout, ModelMap model) {
		// Get list Category
		List<Category> listCategory = categorySv.findAllCategory();
		// Get list CategorySub
		List<CategorySub> listCategorySub = categorySubSv.findAllCategorySub();
		// Set object view
		model.put("listCate", listCategory);
		model.put("listCateSub", listCategorySub);
		model.put("layout", layout);
		model.put("breadcrumbs", breadcrumbs);
		return Constants.TEMPLATE_HOME;
	}

}
