package io.github.crashgamescrmc.StockMarket.market;

public class StockMarketShareStack {

	private StockMarketCompany parent;
	private int amount;
	private double price_when_bought;

	public StockMarketCompany getParent() {
		return parent;
	}

	public void setParent(StockMarketCompany parent) {
		this.parent = parent;
	}

	public double getPrice() {
		return parent.getSharePrice();
	}

	public double getPrice_when_bought() {
		return price_when_bought;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Gets the revenue in %.
	 * 
	 * @return
	 */
	public double getRevenue() {
		return getPrice() / price_when_bought * 100;
	}

}
