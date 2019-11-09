package io.github.crashgamescrmc.StockMarket.market;

public abstract class Order {

	public Order(Share share, int amount) {
		this.amount = amount;
		this.share = share;
	}

	private int amount;
	private Share share;
	protected SMUser user;

	public SMUser getUser() {
		return user;
	}

	public abstract Order getNextOrder(double price, int amount, StockMarket market);

	public abstract int getExecutionAmount(double price, int amount);

	public int getMaxAmount() {
		return amount;
	}

	public abstract Order execute(double price, int amount, StockMarket market);

	public Share getShare() {
		return share;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public abstract double getPriceLimit();

}
