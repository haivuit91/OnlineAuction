package com.asiantech.haivu.onlineauction.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.CategorySub;
import com.asiantech.haivu.onlineauction.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Page<Item> findByAccountAndBidStatus(Account account, boolean bidStatus, Pageable pageable);

	Page<Item> findByBidStatusAndBidStartDateBeforeAndBidEndDateAfter( boolean bidStatus, Date bidStartDate, 
			Date bidEndDate, Pageable pageable);
	
	Page<Item> findByBidStatusAndBidEndDateBefore(boolean bidStatus, Date bidEndDate, Pageable pageable);

	Page<Item> findByCategorySubAndBidStartDateBeforeAndBidEndDateAfterAndBidStatus(CategorySub categorySub, 
			Date bidStartDate, Date bidEndDate, boolean bidStatus, Pageable pageable);
	
	List<Item> findByCategorySub(CategorySub categorySub);
	
	List<Item> findByAccount(Account account);
	
	List<Item> findByBidStatusAndBidEndDateBefore(boolean bidStatus, Date bidEndDate);
	
	Long deleteByCategorySub(CategorySub categorySub);
	
	Long deleteByAccount(Account account);
	
}
