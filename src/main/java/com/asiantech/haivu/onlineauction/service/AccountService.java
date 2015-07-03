package com.asiantech.haivu.onlineauction.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.asiantech.haivu.onlineauction.enums.Status;
import com.asiantech.haivu.onlineauction.model.Account;

public interface AccountService extends UserDetailsService {

	public static String NAME = "accountService";

	Page<Account> findAllAccount(Pageable pageable);

	Account findAccountById(long id);

	Account findAccountByAccountName(String accountName);

	Account findAccountByEmail(String email);

	Account findAccountByEmailAndStatus(String email, Status status);

	Account saveAccount(Account account);
	
	boolean updateTrustAccount(double trust, Account account);

}
