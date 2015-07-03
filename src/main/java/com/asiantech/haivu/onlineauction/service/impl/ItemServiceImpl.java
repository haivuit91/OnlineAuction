package com.asiantech.haivu.onlineauction.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.common.PageAbleCommon;
import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.ItemRepository;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;

@Service(ItemService.NAME)
public class ItemServiceImpl implements ItemService {

	@Resource
	private ItemRepository itemRepository;
	
	@Autowired
	private CategorySubService categorySubService;

	@Override
	public Page<Item> findItemByAccountAndBidStatus(Account account, boolean bidStatus, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return itemRepository.findByAccountAndBidStatus(account, bidStatus, page);
	}

	@Override
	public Page<Item> findItemByBidStatusAndBidStartDateAndBidEndDate(Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return itemRepository.findByBidStatusAndBidStartDateBeforeAndBidEndDateAfter(true, new Date(), new Date(), page);
	}

	@Override
	public Page<Item> findItemByBidStatusAndBidEndDate(Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return itemRepository.findByBidStatusAndBidEndDateBefore(true, new Date(), page);
	}

	@Override
	public Page<Item> findItemByCategorySub(String cateSubPath, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		CategorySub categorySub = categorySubService.findCategorySubByPath(cateSubPath);
		return itemRepository.findByCategorySubAndBidStartDateBeforeAndBidEndDateAfter(categorySub, new Date(), new Date(), page);
	}

	@Override
	public Item findItemById(long id) {
		return itemRepository.findOne(id);
	}

	@Override
	@Transactional
	public Item addNewItem(Item item) {
		return itemRepository.save(item);
	}

	@Override
	@Transactional
	public Item updateItem(Item item) {
		return itemRepository.save(item);
	}

	@Override
	@Transactional
	public Item updateCurrentBidItem(Item item, double currentBid) {
		int count = item.getBidCounts() + 1;
		Item updateItem = new Item(item.getId(), item.getItemTitle(),
				item.getItemDesciption(), item.getItemThumbnail(),
				item.getMinimumBid(), item.getBidIncremenent(), currentBid,
				item.getBidStartDate(), item.getBidEndDate(), count,
				item.isBidStatus(), item.getAccount(), item.getCategorySub());
		return itemRepository.save(updateItem);
	}

	@Override
	@Transactional
	public Item deleteItem(long id) {
		Item item = itemRepository.findOne(id);
		itemRepository.delete(item);
		return item;
	}

}
