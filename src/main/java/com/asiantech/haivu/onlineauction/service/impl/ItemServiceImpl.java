package com.asiantech.haivu.onlineauction.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.asiantech.haivu.onlineauction.common.PageAbleCommon;
import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.ItemRepository;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.CategorySubService;
import com.asiantech.haivu.onlineauction.service.ItemService;
import com.asiantech.haivu.onlineauction.util.HandleImage;

@Service(ItemService.NAME)
public class ItemServiceImpl implements ItemService {

	@Resource
	private ItemRepository itemRepository;
	
	@Autowired
	private CategorySubService categorySubService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HandleImage handleImg;

	@Override
	public Page<Item> findItemByAccountAndBidStatus(String email, boolean bidStatus, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		Account account = accountService.findAccountByEmail(email);
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
	public Item saveItem(Item item, MultipartFile file, String email) {
		String imageName = "default.jpg";
		Account account = accountService.findAccountByEmail(email);
		Item itemTmp = null;
		if (handleImg.uploadFileHandler(file)) {
			imageName = file.getOriginalFilename();
		}
		if (item.getId() == 0) {
			itemTmp = new Item(item.getItemTitle(), item.getItemDesciption(),
					imageName, item.getMinimumBid(), item.getBidIncremenent(),
					item.getBidStartDate(), item.getBidEndDate(), account,
					item.getCategorySub());
		} else {
			itemTmp = new Item(item.getId(), item.getItemTitle(),
					item.getItemDesciption(), imageName,
					item.getMinimumBid(), item.getBidIncremenent(),
					item.getCurrentBid(), item.getBidStartDate(),
					item.getBidEndDate(), item.getBidCounts(),
					item.isBidStatus(), account,
					item.getCategorySub());
		}
		return itemRepository.save(itemTmp);
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
