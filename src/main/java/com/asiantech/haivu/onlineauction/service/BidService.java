package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;

public interface BidService {

	public static final String NAME = "bidService";

	List<Bid> findAllBid();

	List<Bid> findAllBidByItemIdAndStatusTrue(Long itemId);

	List<Bid> findAllBidByAccount(Account account);
	
	boolean acceptBid(double maximumBid, Long itemId, String email);

}
