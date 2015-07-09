package com.asiantech.haivu.onlineauction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByAccount(Account account);

	List<Rating> findByAccountRating(long accountId);
	
	Rating findByAccountRatingAndAccount(long accountId, Account account);
	
	int countByAccountRating(long accountId);
	
	Long deleteByAccount(Account account);

}
