package com.asiantech.haivu.onlineauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.enums.Status;
import com.asiantech.haivu.onlineauction.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByEmailAndStatus(String email, Status status);

	Account findByEmail(String email);
	
	Account findByIdAndVerification(long accountId, String verification);
	
	Account findByVerification(String verification);

}
