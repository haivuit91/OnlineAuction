package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Item;

public interface BidService {

	public static final String NAME = "bidService";

	List<Bid> findAllBid();

	List<Bid> findAllBidByItemAndStatus(Item item, boolean status);

	List<Bid> findAllBidByAccount(Account account);

}
