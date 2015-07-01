package com.asiantech.haivu.onlineauction.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.BidRepository;
import com.asiantech.haivu.onlineauction.service.BidService;

@Service(BidService.NAME)
public class BidServiceImpl implements BidService {

	@Resource
	private BidRepository bidRepository;

	@Override
	public List<Bid> findAllBid() {
		return bidRepository.findAll();
	}

	@Override
	public List<Bid> findAllBidByItemAndStatus(Item item, boolean status) {
		return bidRepository.findByItemAndStatusOrderByMaximumBidAsc(item,
				status);
	}

	@Override
	public List<Bid> findAllBidByAccount(Account account) {
		return bidRepository.findByAccountOrderByMaximumBidDesc(account);
	}

}
