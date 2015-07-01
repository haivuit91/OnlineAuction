package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/auctions/")
public class AuctionController {

	@Autowired
	private CategoryService categorySv;

	@Autowired
	private CategorySubService categorySubSv;

	@Autowired
	private AccountService accountSv;

	@Autowired
	private ItemService itemSv;

	@Autowired
	private BidService bidSv;

	@RequestMapping(value = "all", method = RequestMethod.GET)
	public String goAuctionListPage(@PageableDefault(page = 1, size = 5, sort = "id", direction = Direction.DESC) Pageable pageable,
			HttpSession session, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Account account = accountSv.findAccountByEmail(auth.getName());
			session.setAttribute("sessionAccName", account.getAccountName());
		}

		Page<Item> item = itemSv.findItemByBidStatusAndBidStartDateAndBidEndDate(true, pageable);

		// use prop in Page interface of String
		int current = item.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, item.getTotalPages());

		model.put("listItem", item);
		model.put("beginIndex", begin);
		model.put("endIndex", end);
		model.put("currentIndex", current);

		return showHomePage("All", "auction/auction_list", model);
	}

	@RequestMapping(value = "item/{id}", method = RequestMethod.GET)
	public String goAuctionDetailPage(@PathVariable Long id, ModelMap model) {
		Item item = itemSv.findItemById(id);
		List<Bid> listBid = bidSv.findAllBidByItemAndStatus(item, true);

		// TODO move to service
		int totalBid = (listBid.size() > 0) ? listBid.size() : 0;
		String currentMember = (listBid.size() > 0) ? listBid.get(1)
				.getAccount().getEmail() : "";

		model.put("totalBid", totalBid);
		model.put("currentMember", currentMember);
		model.put("item", item);
		return showHomePage("Auction detail", "auction/auction_detail", model);
	}

	@RequestMapping(value = "finished", method = RequestMethod.GET)
	public String goAuctionFinishPage(ModelMap model) {
		return showHomePage("Auction finish", "auction/auction_finish", model);
	}

	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkLogin() {
		boolean check = false;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			check = true;
		}
		return check;
	}

	@RequestMapping(value = "accept", method = RequestMethod.POST)
	@ResponseBody
	public String acceptBid(@RequestBody Bid jsonBid) {
		return "ok";
	}

	public String showHomePage(String breadcrumbs, String layout, ModelMap model) {
		// Get list Category
		List<Category> listCategory = categorySv.findAllCategory();
		// Get list CategorySub
		List<CategorySub> listCategorySub = categorySubSv.findAllCategorySub();
		// Set object view

		// TODO extract to method return a ModelMap
		model.put("listCate", listCategory);
		model.put("listCateSub", listCategorySub);
		model.put("layout", layout);
		model.put("breadcrumbs", breadcrumbs);
		return Constants.TEMPLATE_HOME;
	}

}
