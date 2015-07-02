package com.asiantech.haivu.onlineauction.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.BidRepository;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.BidService;
import com.asiantech.haivu.onlineauction.service.ItemService;

@Service(BidService.NAME)
public class BidServiceImpl implements BidService {

	@Resource
	private BidRepository bidRepository;

	@Autowired
	private ItemService itemSv;
	
	@Autowired
	private AccountService accountSv;

	@Override
	public List<Bid> findAllBid() {
		return bidRepository.findAll();
	}

	@Override
	public List<Bid> findAllBidByItemIdAndStatusTrue(Long itemId) {
		Item item = itemSv.findItemById(itemId);
		return bidRepository.findByItemAndStatusOrderByMaximumBidAsc(item, true);
	}

	@Override
	public List<Bid> findAllBidByAccount(Account account) {
		return bidRepository.findByAccountOrderByMaximumBidDesc(account);
	}

	@Override
	public boolean acceptBid(double maximumBid, Long itemId, String email) {
		boolean check = false;
		Item item = itemSv.findItemById(itemId);
		Account account = accountSv.findAccountByEmail(email);
		if(item != null && account != null) {
			if(checkItemBidByAccount(item, account)) {
				Bid bid = new Bid(maximumBid, item, account);
				check = (bidRepository.save(bid) != null) ? true : false;
				itemSv.updateCurrentBidItem(item, maximumBid);
			}
		}
		return check;
	}
	
	private boolean checkItemBidByAccount(Item item, Account account) {
		boolean check = false;
		if(item.getAccount() != account) {
			check = true;
		}
		return check;
	}

}
