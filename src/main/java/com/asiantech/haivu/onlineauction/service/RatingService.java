package com.asiantech.haivu.onlineauction.service;

import java.util.List;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Rating;

public interface RatingService {
	
	List<Rating> findAllRatingByAccount(Account account);
	
	List<Rating> fintAllRatingByAccountRating(long accountId);
	
	boolean ratingAccount(double point, long accountId, String email);
	
	int countRatingByAccountRating(long accountId);
	
	Long deleteRating(Account account);

}
