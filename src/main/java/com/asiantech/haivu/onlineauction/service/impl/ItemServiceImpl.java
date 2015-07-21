package com.asiantech.haivu.onlineauction.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.asiantech.haivu.onlineauction.common.PageAbleCommon;
import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Bid;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;
import com.asiantech.haivu.onlineauction.repository.ItemRepository;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.BidService;
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
	private BidService bidService;
	
	@Autowired
	private HandleImage handleImg;

	@Override
	public Page<Item> findAllItem(Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		return itemRepository.findAll(page);
	}

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
		return itemRepository.findByCategorySubAndBidStartDateBeforeAndBidEndDateAfterAndBidStatus(categorySub, new Date(), new Date(), true, page);
	}

	@Override
	public Page<Item> searchItem(Long id, String keyWord, Pageable pageable) {
		Pageable page = PageAbleCommon.customePageable(pageable);
		if(id != 0) {
			CategorySub categorySub = categorySubService.findCategorySubById(id);
			return itemRepository.findByCategorySubAndBidStartDateBeforeAndBidEndDateAfterAndBidStatusAndItemTitleContaining(categorySub, 
					new Date(), new Date(), true, keyWord, page);
		}
		return itemRepository.findByBidStartDateBeforeAndBidEndDateAfterAndBidStatusAndItemTitleContaining(new Date(), 
				new Date(), true, keyWord, page);
	}

	@Override
	public Item findItemById(long id) {
		return itemRepository.findOne(id);
	}

	@Override
	public List<Item> findAllItemByCategorySub(CategorySub categorySub) {
		return itemRepository.findByCategorySub(categorySub);
	}

	@Override
	public List<Item> findAllItemByAccount(Account account) {
		return itemRepository.findByAccount(account);
	}

	@Override
	public List<Item> findItemListStop() {
		return itemRepository.findByBidStatusAndBidEndDateBefore(true, new Date());
	}

	@Override
	@Transactional
	public String saveItem(Item item, MultipartFile file, String email) {
		String imageName = "default.png";
		Account account = accountService.findAccountByEmail(email);
		if (item.getId() == 0) {
			if (handleImg.uploadFileHandler(file)) {
				imageName = file.getOriginalFilename();
			}
			return actionSave(false, item, imageName, account);
		}
		if (handleImg.uploadFileHandler(file)) {
			imageName = file.getOriginalFilename();
		} else {
			Item imgItem = itemRepository.findOne(item.getId());
			imageName = imgItem.getItemThumbnail();
		}
		return actionSave(true, item, imageName, account);
	}

	private String actionSave(boolean edit, Item item, String imageName, Account account) {
		String acction = "edit";
		Item itemTmp;
		if (!edit) {
			itemTmp = new Item(item.getItemTitle(), item.getItemDescription(),
					imageName, item.getMinimumBid(), item.getBidIncremenent(),
					item.getBidStartDate(), item.getBidEndDate(), item.isBidStatus(), account,
					item.getCategorySub());
			acction = "add";
		} else {
			Item accItem = itemRepository.findOne(item.getId());
			itemTmp = new Item(item.getId(), item.getItemTitle(),
					item.getItemDescription(), imageName,
					item.getMinimumBid(), item.getBidIncremenent(),
					accItem.getCurrentBid(), item.getBidStartDate(),
					item.getBidEndDate(), accItem.getBidCounts(),
					item.isBidStatus(), accItem.getAccount(),
					item.getCategorySub());
		}
		itemRepository.save(itemTmp);
		return acction;
	}

	@Override
	@Transactional
	public Item updateCurrentBidItem(Item item, double currentBid) {
		int count = item.getBidCounts() + 1;
		Item updateItem = new Item(item.getId(), item.getItemTitle(),
				item.getItemDescription(), item.getItemThumbnail(),
				item.getMinimumBid(), item.getBidIncremenent(), currentBid,
				item.getBidStartDate(), item.getBidEndDate(), count,
				item.isBidStatus(), item.getAccount(), item.getCategorySub());
		return itemRepository.save(updateItem);
	}

	@Override
	@Transactional
	public Item disableBidStatus(Item item) {
		Item updateItem = new Item(item.getId(), item.getItemTitle(),
				item.getItemDescription(), item.getItemThumbnail(),
				item.getMinimumBid(), item.getBidIncremenent(), item.getCurrentBid(),
				item.getBidStartDate(), item.getBidEndDate(), item.getBidCounts(),
				false, item.getAccount(), item.getCategorySub());
		return itemRepository.save(updateItem);
	}

	@Override
	@Transactional
	public boolean deleteItem(long id) {
		boolean check = false;
		Item item = itemRepository.findOne(id);
		if (item != null) {
			List<Bid> listBid = bidService.findBidByItem(item);
			if (!listBid.isEmpty()) {
				if (bidService.deleteBidByItem(item) != 0) {
					itemRepository.delete(item);
					check = true;
				}
			} else {
				itemRepository.delete(item);
				check = true;
			}
		}
		return check;
	}

	@Override
	@Transactional
	public boolean deleteItemByCategorySub(CategorySub categorySub) {
		boolean check = false;
		List<Item> item = itemRepository.findByCategorySub(categorySub);
		if (!item.isEmpty()) {
			for (Item itemList : item) {
				List<Bid> listBid = bidService.findBidByItem(itemList);
				if (!listBid.isEmpty()) {
					if (bidService.deleteBidByItem(itemList) != 0) {
						itemRepository.deleteByCategorySub(categorySub);
						check = true;
					}
				} else {
					itemRepository.deleteByCategorySub(categorySub);
					check = true;
				}
			}
		}
		return check;
	}

	@Override
	public boolean deleteItemByAccount(Account account) {
		boolean check = false;
		List<Item> item = itemRepository.findByAccount(account);
		if (!item.isEmpty()) {
			for (Item itemList : item) {
				List<Bid> listBid = bidService.findBidByItem(itemList);
				if (!listBid.isEmpty()) {
					if (bidService.deleteBidByItem(itemList) != 0) {
						itemRepository.deleteByAccount(account);
						check = true;
					}
				} else {
					itemRepository.deleteByAccount(account);
					check = true;
				}
			}
		}
		return check;
	}

}
