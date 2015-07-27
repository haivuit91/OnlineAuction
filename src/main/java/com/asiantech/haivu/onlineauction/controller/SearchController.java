package com.asiantech.haivu.onlineauction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("search")
public class SearchController extends ShowPage {

	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String auctionSearch(
			@PageableDefault(page = 1, size = 8, sort = "id", direction = Direction.DESC) Pageable pageable,
			@RequestParam("id") Long id, @RequestParam("key") String key, ModelMap model) {
		Page<Item> item = itemService.searchItem(id, key, pageable);
		int count = item.getTotalPages();
		model.put("listItem", item);
		model = showHomePage(count + " results for \"" + key + "\"", "auction/auction_list", model);
		return Constants.TEMPLATE_HOME;
	}

}
