package com.asiantech.haivu.onlineauction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;

public interface ItemService {

	public static String NAME = "itemService";

	Page<Item> findItemByAccountAndBidStatus(Account account, boolean status, Pageable pageable);

	Page<Item> findItemByBidStatusAndBidStartDateAndBidEndDate(boolean status, Pageable pageable);

	Page<Item> findItemByCategorySub(CategorySub categorySub, Pageable pageable);

	Item findItemById(long id);

	Item saveItem(Item item);

	Item deleteItem(long id);

}
