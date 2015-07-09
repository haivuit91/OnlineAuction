package com.asiantech.haivu.onlineauction.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.model.Category;
import com.asiantech.haivu.onlineauction.model.CategorySub;

public interface CategorySubRepository extends JpaRepository<CategorySub, Long> {
	
	Page<CategorySub> findByCategory(Category category, Pageable pageable);
	
	CategorySub findByCateSubPath(String cateSubPath);
	
	List<CategorySub> findByCategory(Category category);
	
	Long deleteByCategory(Category category);

}
