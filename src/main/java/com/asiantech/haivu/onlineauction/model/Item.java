package com.asiantech.haivu.onlineauction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "item")
public class Item extends Model {

	@Column(name = "item_title", length = 100)
	@NotNull(message = "The item title is required and can't be empty")
	private String itemTitle;

	@Column(name = "item_desciption", length = 4000)
	private String itemDesciption;

	@Column(name = "item_thumbnail_name", length = 255)
	private String itemThumbnail;

	@Column(name = "minimum_bid")
	@NotNull(message = "The minimum bid is required and can't be empty")
	private double minimumBid;

	@Column(name = "bid_incremenent")
	@NotNull(message = "The bid incremenent is required and can't be empty")
	private double bidIncremenent;

	@Column(name = "current_bid")
	private double currentBid;

	@Column(name = "bid_start_date")
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date bidStartDate;

	@Column(name = "bid_end_date")
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date bidEndDate;

	@Column(name = "bid_status")
	private boolean bidStatus;

	@ManyToOne
	private Account account;

	@ManyToOne
	private CategorySub categorySub;

	public Item() {
		super();
	}

	public Item(String itemTitle, String itemDesciption, String itemThumbnail,
			double minimumBid, double bidIncremenent, double currentBid,
			Date bidStartDate, Date bidEndDate, boolean bidStatus,
			Account account, CategorySub categorySub) {
		super();
		this.itemTitle = itemTitle;
		this.itemDesciption = itemDesciption;
		this.itemThumbnail = itemThumbnail;
		this.minimumBid = minimumBid;
		this.bidIncremenent = bidIncremenent;
		this.currentBid = currentBid;
		this.bidStartDate = bidStartDate;
		this.bidEndDate = bidEndDate;
		this.bidStatus = bidStatus;
		this.account = account;
		this.categorySub = categorySub;
	}

	/**
	 * @param itemTitle
	 * @param itemDesciption
	 * @param itemThumbnail
	 * @param minimumBid
	 * @param bidIncremenent
	 * @param bidStartDate
	 * @param bidEndDate
	 * @param account
	 *            Create new item
	 */
	public Item(String itemTitle, String itemDesciption, String itemThumbnail,
			double minimumBid, double bidIncremenent, Date bidStartDate,
			Date bidEndDate, Account account, CategorySub categorySub) {
		this(itemTitle, itemDesciption, itemThumbnail, minimumBid,
				bidIncremenent, minimumBid, bidStartDate, bidEndDate, true,
				account, categorySub);
	}

	/**
	 * @param currentBid
	 *            Update Item
	 */
	public Item(double currentBid) {
		this.currentBid = currentBid;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDesciption() {
		return itemDesciption;
	}

	public void setItemDesciption(String itemDesciption) {
		this.itemDesciption = itemDesciption;
	}

	public String getItemThumbnail() {
		return itemThumbnail;
	}

	public void setItemThumbnail(String itemThumbnail) {
		this.itemThumbnail = itemThumbnail;
	}

	public double getMinimumBid() {
		return minimumBid;
	}

	public void setMinimumBid(double minimumBid) {
		this.minimumBid = minimumBid;
	}

	public double getBidIncremenent() {
		return bidIncremenent;
	}

	public void setBidIncremenent(double bidIncremenent) {
		this.bidIncremenent = bidIncremenent;
	}

	public double getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(double currentBid) {
		this.currentBid = currentBid;
	}

	public Date getBidStartDate() {
		return bidStartDate;
	}

	public void setBidStartDate(Date bidStartDate) {
		this.bidStartDate = bidStartDate;
	}

	public Date getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(Date bidEndDate) {
		this.bidEndDate = bidEndDate;
	}

	public boolean isBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(boolean bidStatus) {
		this.bidStatus = bidStatus;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CategorySub getCategorySub() {
		return categorySub;
	}

	public void setCategorySub(CategorySub categorySub) {
		this.categorySub = categorySub;
	}

}
