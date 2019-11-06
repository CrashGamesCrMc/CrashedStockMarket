package io.github.crashgamescrmc.StockMarket.market;

public class StockMarketUser {

	private String name;

	private StockMarketShareStack[] shares;
	private StockMarketOrder[] orders;

	public StockMarketShareStack[] getShares() {
		return shares;
	}

	public void setShares(StockMarketShareStack[] shares) {
		this.shares = shares;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StockMarketOrder[] getOrders() {
		return orders;
	}

	public void setOrders(StockMarketOrder[] orders) {
		this.orders = orders;
	}

}
