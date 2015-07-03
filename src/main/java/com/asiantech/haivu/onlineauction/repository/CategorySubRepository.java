package com.asiantech.haivu.onlineauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.model.CategorySub;

public interface CategorySubRepository extends JpaRepository<CategorySub, Long> {
	
	CategorySub findByCateSubPath(String cateSubPath);

}
