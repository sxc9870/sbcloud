package com.sbcloud.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "k_line")
public class KLinePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int tid;
	@Column(name = "code")
	private String code;
	@Column(name = "date")
	private Date date;
	@Column(name = "trade_money", length = 20)
	private String tradeMoney;
	
	@Column(name = "swing")
	private BigDecimal swing;
	@Column(name = "diff_rate")
	private BigDecimal diffRate;
	@Column(name = "market")
	private String market;
	@Column(name = "turnover")
	private BigDecimal turnover;
	@Column(name = "min_price")
	private BigDecimal minPrice;
	@Column(name = "close_price")
	private BigDecimal closePrice;
	@Column(name = "trade_num")
	private BigDecimal tradeNum;
	@Column(name = "diff_money")
	private BigDecimal diffMoney;
	@Column(name = "open_price")
	private BigDecimal openPrice;
	@Column(name = "max_price")
	private BigDecimal maxPrice;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getSwing() {
		return swing;
	}

	public void setSwing(BigDecimal swing) {
		this.swing = swing;
	}

	public BigDecimal getDiffRate() {
		return diffRate;
	}

	public void setDiffRate(BigDecimal diffRate) {
		this.diffRate = diffRate;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public BigDecimal getTurnover() {
		return turnover;
	}

	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(BigDecimal tradeNum) {
		this.tradeNum = tradeNum;
	}

	public BigDecimal getDiffMoney() {
		return diffMoney;
	}

	public void setDiffMoney(BigDecimal diffMoney) {
		this.diffMoney = diffMoney;
	}

	public BigDecimal getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

}
