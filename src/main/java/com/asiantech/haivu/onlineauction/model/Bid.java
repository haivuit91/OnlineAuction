package com.asiantech.haivu.onlineauction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author haivu_000
 *
 */
@Entity
@Table(name = "bid")
public class Bid {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 1000)
	private long id;

	@Column(name = "time_bid")
	private Date timeBid;

	@Column(name = "maximum_bid")
	@NotNull(message = "The maximum bid is required and can't be empty")
	private double maximumBid;
	
	@Column(name = "count")
	private int count;

	@Column(name = "status")
	private boolean status;

	@ManyToOne
	private Item item;

	@ManyToOne
	private Account account;

	public Bid() {
		super();
	}

	public Bid(long id, Date timeBid, double maximumBid, int count, boolean status, Item item, Account account) {
		super();
		this.id = id;
		this.timeBid = timeBid;
		this.maximumBid = maximumBid;
		this.count = count;
		this.status = status;
		this.item = item;
		this.account = account;
	}
	
	/**
	 * @param id
	 * @param maximumBid
	 * @param item
	 * @param account
	 * 
	 * Create and update Bid
	 */
	public Bid(long id, double maximumBid, int count, Item item, Account account) {
		this(id, new Date(), maximumBid, count, true, item, account);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
