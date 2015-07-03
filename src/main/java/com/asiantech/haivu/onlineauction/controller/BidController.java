package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.service.BidService;

@Controller
@RequestMapping("/bid/")
public class BidController {

	@Autowired
	private BidService bidSv;

	@RequestMapping(value = "list-bid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Bid> getListBidByItemId(@RequestParam("id") Long id) {
		List<Bid> bid = null;
		if(id != null) {
			bid = bidSv.findAllBidByItemIdAndStatusTrue(id);
		}
		return bid;
	}
	
	@RequestMapping(value = "accept-bid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean acceptBid(@RequestParam("price") double price, @RequestParam("itemId") long itemId) {
		boolean check = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (bidSv.acceptBid(price, itemId, auth.getName())) {
			check = true;
		}
		return check;
	}

}
