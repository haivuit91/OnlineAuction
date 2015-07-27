package com.asiantech.haivu.onlineauction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "item_title", length = 100)
	@Max(value = 1000)
	@NotNull(message = "The item title is required and can't be empty")
	private String itemTitle;

	@Column(name = "item_description", length = 4000)
	private String itemDescription;

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
	
	@Column(name = "bid_counts")
	private int bidCounts;

	@Column(name = "bid_status")
	private boolean bidStatus;

	@ManyToOne
	private Account account;

	@ManyToOne
	private CategorySub categorySub;

	public Item() {
		super();
	}

	public Item(long id, String itemTitle, String itemDescription,
			String itemThumbnail, double minimumBid, double bidIncremenent,
			double currentBid, Date bidStartDate, Date bidEndDate, int bidCounts,
			boolean bidStatus, Account account, CategorySub categorySub) {
		super();
		this.id = id;
		this.itemTitle = itemTitle;
		this.itemDescription = itemDescription;
		this.itemThumbnail = itemThumbnail;
		this.minimumBid = minimumBid;
		this.bidIncremenent = bidIncremenent;
		this.currentBid = currentBid;
		this.bidStartDate = bidStartDate;
		this.bidEndDate = bidEndDate;
		this.bidCounts = bidCounts;
		this.bidStatus = bidStatus;
		this.account = account;
		this.categorySub = categorySub;
	}

	/**
	 * @param itemTitle
	 * @param itemDescription
	 * @param itemThumbnail
	 * @param minimumBid
	 * @param bidIncremenent
	 * @param bidStartDate
	 * @param bidEndDate
	 * @param account
	 * 
	 * Create new item
	 */
	public Item(String itemTitle, String itemDescription, String itemThumbnail,
			double minimumBid, double bidIncremenent, Date bidStartDate,
			Date bidEndDate, boolean bidStatus, Account account, CategorySub categorySub) {
		this(0, itemTitle, itemDescription, itemThumbnail, minimumBid,
				bidIncremenent, minimumBid, bidStartDate, bidEndDate, 0, bidStatus,
				account, categorySub);
	}

	/**
	 * @param item
	 * @param currentBid
	 * 
	 * Update current bid Item
	 */
	public Item(Item item, double currentBid) {
		this(item.getId(), item.itemTitle, item.getItemDescription(), item
				.getItemThumbnail(), item.getMinimumBid(), item
				.getBidIncremenent(), currentBid, item.getBidStartDate(), item
				.getBidEndDate(), item.getBidCounts(), item.isBidStatus(), item.account,
				item.categorySub);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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

	public int getBidCounts() {
		return bidCounts;
	}

	public void setBidCounts(int bidCounts) {
		this.bidCounts = bidCounts;
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
