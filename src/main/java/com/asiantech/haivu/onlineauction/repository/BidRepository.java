package com.asiantech.haivu.onlineauction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Item;

public interface BidRepository extends JpaRepository<Bid, Long> {

	List<Bid> findByItemAndStatusOrderByMaximumBidAsc(Item item, boolean status);

	List<Bid> findByAccountOrderByMaximumBidDesc(Account account);
	
	Bid findByAccountAndItem(Account account, Item item);

}
