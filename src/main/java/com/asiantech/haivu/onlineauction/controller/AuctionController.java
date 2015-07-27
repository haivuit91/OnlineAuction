package com.asiantech.haivu.onlineauction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/auctions/")
public class AuctionController extends ShowPage {

	@Autowired
	private CategorySubService categorySubSv;

	@Autowired
	private ItemService itemSv;

	@RequestMapping(value = "all")
	public String goAuctionListPage(
			@PageableDefault(page = 1, size = 8, sort = "id", direction = Direction.DESC) Pageable pageable, ModelMap model) {
		Page<Item> item = itemSv.findItemByBidStatusAndBidStartDateAndBidEndDate(pageable);
		model.put("listItem", item);
		model = showHomePage("All", "auction/auction_list", model);
		return Constants.TEMPLATE_HOME;
	}
	
	@RequestMapping(value = "{cateSubPath}", method = RequestMethod.GET)
	public String goAuctionByCateGoryPage(
			@PathVariable String cateSubPath,
			@PageableDefault(page = 1, size = 8, sort = "id", direction = Direction.DESC) Pageable pageable,
			ModelMap model) {
		Page<Item> item = itemSv.findItemByCategorySub(cateSubPath, pageable);
		CategorySub cateSub = categorySubSv.findCategorySubByPath(cateSubPath);
		String category = cateSub.getCateSubName();
		model.put("listItem", item);
		model = showHomePage(category, "auction/auction_list", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "item/{id}", method = RequestMethod.GET)
	public String goAuctionDetailPage(@PathVariable Long id, ModelMap model) {
		Item item = itemSv.findItemById(id);
		model.put("item", item);
		model = showHomePage("Auction detail", "auction/auction_detail", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "user/{accountId}", method = RequestMethod.GET)
	public String goAuctionByUserPage(@PageableDefault(page = 1, size = 10, sort = "bidStatus", direction = Direction.DESC) Pageable pageable,
			@PathVariable Long accountId, ModelMap model) {
		Page<Item> items = itemSv.findItemByAccount(accountId, pageable);
		model.put("listItem", items);
		model = showHomePage("Auction detail", "auction/auction_user", model);
		return Constants.TEMPLATE_HOME;
	}

	@RequestMapping(value = "finished")
	public String goAuctionFinishPage(
			@PageableDefault(page = 1, size = 8, sort = "id", direction = Direction.DESC) Pageable pageable,
			ModelMap model) {
		Page<Item> items = itemSv.findItemByBidStatusAndBidEndDate(pageable);
		model.put("listItem", items);
		model = showHomePage("Auction finish", "auction/auction_finish", model);
		return Constants.TEMPLATE_HOME;
	}
	
	@RequestMapping(value = "winners")
	public String goAuctionWinnerPage(
			@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.DESC) Pageable pageable,
			ModelMap model) {
		Page<Item> item = itemSv.findItemByBidStatusAndBidEndDate(pageable);
		model.put("listItem", item);
		model = showHomePage("Winner", "auction/auction_result", model);
		return Constants.TEMPLATE_HOME;
	}

}
