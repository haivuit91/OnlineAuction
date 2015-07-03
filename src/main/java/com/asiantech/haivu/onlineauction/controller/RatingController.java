package com.asiantech.haivu.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiantech.haivu.onlineauction.model.Rating;
import com.asiantech.haivu.onlineauction.service.RatingService;

@Controller
@RequestMapping("/rating/")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@RequestMapping(value = "rating", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean ratingAccount(@RequestParam("point") double point, @RequestParam("accountId") long accountId) {
		boolean check = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (ratingService.ratingAccount(point, accountId, auth.getName())) {
			check = true;
		}
		return check;
	}
	
	@RequestMapping(value = "count", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int countRating(@RequestParam("accountId") long accountId) {
		List<Rating> listRating = ratingService.fintAllRatingByAccountRating(accountId);
		int count = listRating.size();
		return count;
	}

}
