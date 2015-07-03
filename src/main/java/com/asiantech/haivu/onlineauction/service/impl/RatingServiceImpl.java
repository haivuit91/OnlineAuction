package com.asiantech.haivu.onlineauction.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.model.Rating;
import com.asiantech.haivu.onlineauction.repository.RatingRepository;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Resource
	private RatingRepository ratingRepository;

	@Autowired
	private AccountService accountService;

	@Override
	public List<Rating> findAllRatingByAccount(Account account) {
		return ratingRepository.findByAccount(account);
	}

	@Override
	public List<Rating> fintAllRatingByAccountRating(long accountId) {
		return ratingRepository.findByAccountRating(accountId);
	}

	@Override
	@Transactional
	public boolean ratingAccount(double point, long accountId, String email) {
		boolean check = false;
		Account account = accountService.findAccountByEmail(email);
		Account acc = accountService.findAccountById(accountId);
		if(!account.getEmail().equals(acc.getEmail())) {
			Rating rating = ratingRepository.findByAccountRatingAndAccount(
					accountId, account);
			if (rating != null) {
				Rating ratingtmp = new Rating(point, rating);
				ratingRepository.save(ratingtmp);
				if (updateTrustAccount(point, acc)) {
					check = true;
				}
			} else {
				Rating ratingtmp = new Rating(0, point, accountId, account);
				ratingRepository.save(ratingtmp);
				if (updateTrustAccount(point, acc)) {
					check = true;
				}
			}
		}
		return check;
	}

	private boolean updateTrustAccount(double point, Account account) {
		double trust = averageTrust(point, account);
		boolean check = accountService.updateTrustAccount(trust, account) ? true
				: false;
		return check;
	}
	
	public double averageTrust(double point, Account account) {
		double trust, sum = 0, count = 0;
		long accountId = account.getId();
		List<Rating> listRating = ratingRepository.findByAccountRating(accountId);
		for (Rating rating : listRating) {
			sum += rating.getPoint();
			count++;
		}
		NumberFormat formatter = new DecimalFormat("#0.0");
		trust = Double.parseDouble(formatter.format(sum / count));
		return trust;
	}

}
