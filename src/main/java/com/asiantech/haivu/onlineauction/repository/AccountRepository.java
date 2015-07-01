package com.asiantech.haivu.onlineauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiantech.haivu.onlineauction.enums.Status;
import com.asiantech.haivu.onlineauction.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByEmailAndStatus(String email, Status status);

	Account findByAccountName(String accountName);

	Account findByEmail(String email);

}
