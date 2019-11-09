package io.github.crashgamescrmc.StockMarket.market;

public class ShareStack {

	public ShareStack(Share type, int amount, double price_when_bought) {
		this.type = type;
		this.amount = amount;
		this.price_when_bought = price_when_bought;
	}

	public ShareStack(Share type, int amount) {
		this.type = type;
		this.amount = amount;
	}

	private int amount;
	private double price_when_bought;
	private Share type;

	public double getPrice() {
		return type.getPrice();
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

	public void merge(ShareStack shareStack) {
		if (type == shareStack.type) {
			price_when_bought = (price_when_bought * amount + shareStack.price_when_bought * shareStack.amount)
					/ (amount + shareStack.amount);
			amount += shareStack.amount;
		} else {
			throw new ShareStackMergeException("You cannot merge to share stacks of different share types! ("
					+ type.getName() + " & " + shareStack.type.getName() + ")");
		}
	}

	/**
	 * The return value tells you whether the stack is empty after the removal.
	 * 
	 * @param amount
	 * @return
	 */
	public boolean remove(int amount) {
		this.amount -= amount;
		return amount < 1;
	}

	public void add(int amount) {
		amount += amount;
	}

	public Share getType() {
		return type;
	}

	public void setType(Share type) {
		this.type = type;
	}

}
