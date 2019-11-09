package io.github.crashgamescrmc.StockMarket.market;

public class Company {

	private String name;
	private double dividend;
	private int shareCount;

	private double sharePrice;

	private int revenue;
	private double trading_cost;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDividend() {
		return dividend;
	}

	public void setDividend(double dividend) {
		this.dividend = dividend;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public double getTrading_cost() {
		return trading_cost;
	}

	public void setTrading_cost(double trading_cost) {
		this.trading_cost = trading_cost;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

}
