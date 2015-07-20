package com.asiantech.haivu.onlineauction.taskscheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.service.BidService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.service.VerificationMailService;

public class SendMailToWinner {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private BidService bidService;

	@Autowired
	private VerificationMailService verificationMailSv;
	
	public void run() {
		List<Item> itemList = itemService.findItemListStop();
		if(!itemList.isEmpty()) {
			for (Item item : itemList) {
				findBidByItem(item);
			}
		}
	}

	private void findBidByItem(Item item) {
		List<Bid> bidList = bidService.findAllBidByItemIdAndStatusTrue(item.getId());
		if(!bidList.isEmpty()) {
			String email = "";
			double i = 0;
			for (Bid bid : bidList) {
				if(bid.getMaximumBid() > i) {
					email = bid.getAccount().getEmail();
					i = bid.getMaximumBid();
				}
			}
			String content = "You win: product " + item.getItemTitle() + " with price " + i + "$.";
			verificationMailSv.verifyEmail(email, "You win", content);
			Thread t = new Thread(verificationMailSv);
	        t.start();
			itemService.disableBidStatus(item);
		}
	}

}
