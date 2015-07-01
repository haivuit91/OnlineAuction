package com.asiantech.haivu.onlineauction.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageAbleCommon {
	public static Pageable customePageable(Pageable pageable) {
		int number = pageable.getPageNumber() < 0 ? 0 : pageable.getPageNumber() - 1;
		Pageable page = new PageRequest(number, pageable.getPageSize(), pageable.getSort());
		return page;
	}
}
