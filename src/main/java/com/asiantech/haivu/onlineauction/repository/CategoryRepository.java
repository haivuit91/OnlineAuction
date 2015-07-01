package com.asiantech.haivu.onlineauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
