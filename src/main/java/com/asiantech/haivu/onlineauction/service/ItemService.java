package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;

public interface ItemService {

	public static String NAME = "itemService";
	
	Page<Item> findAllItem(Pageable pageable);

	Page<Item> findItemByAccountAndBidStatus(String email, boolean status, Pageable pageable);

	Page<Item> findItemByBidStatusAndBidStartDateAndBidEndDate(Pageable pageable);
	
	Page<Item> findItemByBidStatusAndBidEndDate(Pageable pageable);

	Page<Item> findItemByCategorySub(String cateSubName, Pageable pageable);

	Item findItemById(long id);
	
	List<Item> findAllItemByCategorySub(CategorySub categorySub);
	
	Item saveItem(Item item, MultipartFile file, String email);
	
	Item updateCurrentBidItem(Item item, double currentBid);

	boolean deleteItem(long id);
	
	boolean deleteItemByCategorySub(CategorySub categorySub);

}
