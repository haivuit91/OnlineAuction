package com.asiantech.haivu.onlineauction.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asiantech.haivu.onlineauction.common.PageAbleCommon;
import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.ItemRepository;
import com.asiantech.haivu.onlineauction.service.ItemService;

@Service(ItemService.NAME)
public class ItemServiceImpl implements ItemService {

	@Resource
	private ItemRepository itemRepository;

	@Override
	public Page<Item> findItemByAccountAndBidStatus(Account account, boolean bidStatus, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return itemRepository.findByAccountAndBidStatus(account, bidStatus, page);
	}

	@Override
	public Page<Item> findItemByBidStatusAndBidStartDateAndBidEndDate(boolean bidStatus, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return itemRepository.findByBidStatusAndBidStartDateBeforeAndBidEndDateAfter(bidStatus, new Date(), new Date(), page);
	}

	@Override
	public Page<Item> findItemByCategorySub(CategorySub categorySub,
			Pageable pageable) {
		return itemRepository.findByCategorySub(categorySub, pageable);
	}

	@Override
	public Item findItemById(long id) {
		return itemRepository.findOne(id);
	}

	@Override
	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public Item deleteItem(long id) {
		Item item = itemRepository.findOne(id);
		itemRepository.delete(item);
		return item;
	}

}
