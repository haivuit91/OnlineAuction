package com.asiantech.haivu.onlineauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class Rating extends Model {

	@Column(name = "point", length = 100)
	@NotNull
	private double point;

	@Column(name = "account_rating", length = 1000)
	@NotNull
	private long accountRating;

	@ManyToOne
	private Account account;

	public Rating() {
		super();
	}

	public Rating(double point, long accountRating, Account account) {
		super();
		this.point = point;
		this.accountRating = accountRating;
		this.account = account;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public long getAccountRating() {
		return accountRating;
	}

	public void setAccountRating(long accountRating) {
		this.accountRating = accountRating;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
