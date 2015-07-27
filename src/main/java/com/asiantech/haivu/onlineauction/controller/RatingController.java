package com.asiantech.haivu.onlineauction.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.service.RatingService;
import com.asiantech.haivu.onlineauction.util.Constants;

@Controller
@RequestMapping("/rating/")
public class RatingController extends ShowPage {

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "rating", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean ratingAccount(@RequestParam("point") double point, @RequestParam("accountId") long accountId) {
		boolean check = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (ratingService.ratingAccount(point, accountId, auth.getName())) {
			check = true;
		}
		return check;
	}
	
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int countRating(@RequestParam("accountId") long accountId) {
		return ratingService.countRatingByAccountRating(accountId);
	}
	
	@RequestMapping(value = "count-item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int countItemByAccount(@RequestParam("accountId") long accountId) {
		return itemService.countItemByAccount(accountId);
	}
	
	@RequestMapping(value = "top-rating")
	public String topRating(@PageableDefault(page = 1, size = 10, sort = "trust", direction = Direction.DESC) Pageable pageable, ModelMap model) {
		Page<Account> accounts = accountService.findAllAccount(pageable);
		model.put("accounts", accounts);
		model = showHomePage("Top rating", "top_rating", model);
		return Constants.TEMPLATE_HOME;
	}

}
