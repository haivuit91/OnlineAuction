package com.asiantech.haivu.onlineauction.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.haivu.onlineauction.common.PageAbleCommon;
import com.asiantech.haivu.onlineauction.enums.Role;
import com.asiantech.haivu.onlineauction.enums.Status;
import com.asiantech.haivu.onlineauction.model.Account;
import com.asiantech.haivu.onlineauction.repository.AccountRepository;
import com.asiantech.haivu.onlineauction.service.AccountService;
import com.asiantech.haivu.onlineauction.service.VerificationMailService;
import com.asiantech.haivu.onlineauction.util.Support;

@Service(AccountService.NAME)
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountRepository accountRepository;

	@Autowired
	private VerificationMailService verificationMailSv;
	
	private Support support = new Support();

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
		Pageable page =  PageAbleCommon.customePageable(pageable);
		return accountRepository.findAll(page);
	}

	@Override
	public Account findAccountById(long accId) {
		return accountRepository.findOne(accId);
	}

	@Override
	public Account findAccountByAccountName(String accountName) {
		return accountRepository.findByAccountName(accountName);
	}

	@Override
	public Account findAccountByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public Account userRegister(Account account) {
		String pwd = passwordEncoder.encode(account.getPwd());
		String verifyCode = support.randomString(30);
		Account acc = new Account(account.getAccountName(), pwd,
				account.getFullName(), account.getEmail(), verifyCode);
		Account accountVerify = accountRepository.save(acc);
		if(accountVerify != null) {
			String verifyLink = "http://localhost:8081/OnlineAuction/account/verify-email?uid=" + accountVerify.getId() + "&code=" + verifyCode;
			verificationMailSv.sendMail(accountVerify.getEmail(), "Verify your account", verifyLink);
		}
		return accountVerify;
	}
	
	@Override
	@Transactional
	public Account addNewAccount(Account account) {
		String randomPwd = support.randomString(6);
		String verifyCode = support.randomString(30);
		String pwd = passwordEncoder.encode(randomPwd);
		Account acc = new Account(account.getAccountName(), pwd, account.getFullName(), account.getEmail(), account.getRole(), verifyCode);
		Account accountConfirm = accountRepository.save(acc);
		if(accountConfirm != null) {
			String verifyLink = "Pass: " + randomPwd + ", http://localhost:8081/OnlineAuction/account/verify-email?uid=" + accountConfirm.getId() + "&code=" + verifyCode;
			verificationMailSv.sendMail(accountConfirm.getEmail(), "Verify your account", verifyLink);
		}
		return accountConfirm;
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
		return accountRepository.findByEmailAndStatus(email, status);
	}

	@Override
	@Transactional
	public boolean updateTrustAccount(double trust, Account account) {
		Account acc = new Account(account.getId(), account.getAccountName(),
				account.getPwd(), account.getFullName(),
				account.getDateOfBirth(), account.getSex(), account.getEmail(),
				account.getStatus(), account.getRole(), trust,
				account.getVerification());
		boolean check = accountRepository.save(acc) != null ? true : false;
		return check;
	}

	@Override
	@Transactional
	public boolean updateStatusByIdAndVerification(long accountId, String verification) {
		boolean check = false;
		Account account = accountRepository.findByIdAndVerification(accountId, verification);
		if(account != null) {
			Account acc = new Account(account.getId(), account.getAccountName(), account.getPwd(), account.getFullName(), 
					account.getDateOfBirth(), account.getSex(), account.getEmail(), Status.ENABLE, account.getRole(), 
					account.getTrust(), "0");
			Account checkAccount = accountRepository.save(acc);
			if(checkAccount != null) {
				check = true;
			}
		}
		return check;
	}

}
