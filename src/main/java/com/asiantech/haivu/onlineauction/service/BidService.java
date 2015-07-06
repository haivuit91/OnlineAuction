package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Item;

public interface BidService {

	public static final String NAME = "bidService";

	List<Bid> findAllBid();

	List<Bid> findAllBidByItemIdAndStatusTrue(long itemId);

	List<Bid> findAllBidByAccount(Account account);
	
	List<Bid> findBidByItem(Item item);
	
	boolean acceptBid(double maximumBid, long itemId, String email);
	
	Long deleteBidByItem(Item item);

}
