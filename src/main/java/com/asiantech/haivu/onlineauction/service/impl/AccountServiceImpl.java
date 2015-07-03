package com.asiantech.haivu.onlineauction.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.enums.Role;
import com.asiantech.haivu.onlineauction.enums.Status;
import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.repository.AccountRepository;
import com.asiantech.haivu.onlineauction.service.AccountService;

@Service(AccountService.NAME)
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountRepository accRepository;

	public List<String> getRoles(Role role) {
		List<String> roles = new ArrayList<>();
		roles.add(role.name());
		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	@Override
	public Page<Account> findAllAccount(Pageable pageable) {
		return accRepository.findAll(pageable);
	}

	@Override
	public Account findAccountById(long accId) {
		return accRepository.findOne(accId);
	}

	@Override
	public Account findAccountByAccountName(String accountName) {
		return accRepository.findByAccountName(accountName);
	}

	@Override
	public Account findAccountByEmail(String email) {
		return accRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public Account saveAccount(Account account) {
		return accRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		Account accLogin = findAccountByEmailAndStatus(email, Status.ENABLE);
		User user = new User(accLogin.getEmail(), accLogin.getPwd(), true,
				true, true, true, getAuthorities(accLogin.getRole()));
		return user;
	}

	@Override
	public Account findAccountByEmailAndStatus(String email, Status status) {
		return accRepository.findByEmailAndStatus(email, status);
	}

	@Override
	@Transactional
	public boolean updateTrustAccount(double trust, Account account) {
		Account acc = new Account(account.getId(), account.getAccountName(),
				account.getPwd(), account.getFullName(),
				account.getDateOfBirth(), account.getSex(), account.getEmail(),
				account.getStatus(), account.getRole(), trust,
				account.getVerification());
		boolean check = accRepository.save(acc) != null ? true : false;
		return check;
	}

}
