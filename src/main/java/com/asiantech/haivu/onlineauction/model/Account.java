package com.asiantech.haivu.onlineauction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.asiantech.haivu.onlineauction.enums.Role;
import com.asiantech.haivu.onlineauction.enums.Status;

@Entity
@Table(name = "account")
public class Account extends Model {

	@Column(name = "account_name", length = 255)
	@NotNull(message = "The account name is required and can't be empty")
	@Size(min = 3, message = "The account name must be more than 3 characters long")
	private String accountName;

	@Column(name = "password", length = 1000)
	@NotNull(message = "The password is required and can't be empty")
	@Size(min = 6, message = "The password must be more than 6 characters long")
	private String pwd;

	@Column(name = "full_name", length = 100)
	private String fullName;

	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	@Column(name = "sex")
	private int sex;

	@Column(name = "email", length = 100)
	@NotNull(message = "The email address is required and can't be empty")
	@Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "The input is not a valid email address")
	private String email;

	@Column(name = "status", length = 50)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "role", length = 50)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "trust", length = 100)
	private double trust;

	@Column(name = "verification", length = 1000)
	private String verification;

	public Account() {
		super();
	}

	public Account(String accountName, String pwd, String fullName,
			Date dateOfBirth, int sex, String email, Status status, Role role,
			double trust, String verification) {
		super();
		this.accountName = accountName;
		this.pwd = pwd;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.email = email;
		this.status = status;
		this.role = role;
		this.trust = trust;
		this.verification = verification;
	}

	/**
	 * @param accountName
	 * @param pwd
	 * @param fullName
	 * @param email
	 * @param verification
	 *            Create account with role User
	 */
	public Account(String accountName, String pwd, String fullName,
			String email, String verification) {
		this(accountName, pwd, fullName, null, 0, email, Status.ENABLE,
				Role.ROLE_USER, 0, verification);
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

}
