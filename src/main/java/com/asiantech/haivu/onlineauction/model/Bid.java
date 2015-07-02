package com.asiantech.haivu.onlineauction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bid")
public class Bid extends Model {

	@Column(name = "time_bid")
	private Date timeBid;

	@Column(name = "maximum_bid")
	@NotNull(message = "The maximum bid is required and can't be empty")
	private double maximumBid;

	@Column(name = "status")
	private boolean status;

	@ManyToOne
	private Item item;

	@ManyToOne
	private Account account;

	public Bid() {
		super();
	}

	public Bid(Date timeBid, double maximumBid, boolean status, Item item, Account account) {
		super();
		this.timeBid = timeBid;
		this.maximumBid = maximumBid;
		this.status = status;
		this.item = item;
		this.account = account;
	}
	
	public Bid(double maximumBid, Item item, Account account) {
		this(new Date(), maximumBid, true, item, account);
	}

	public Date getTimeBid() {
		return timeBid;
	}

	public void setTimeBid(Date timeBid) {
		this.timeBid = timeBid;
	}

	public double getMaximumBid() {
		return maximumBid;
	}

	public void setMaximumBid(double maximumBid) {
		this.maximumBid = maximumBid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
