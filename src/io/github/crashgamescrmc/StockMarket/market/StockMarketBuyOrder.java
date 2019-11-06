package io.github.crashgamescrmc.StockMarket.market;

public abstract class StockMarketBuyOrder implements StockMarketOrder {

	private StockMarketUser user;
	private int amount;

	@Override
	public StockMarketUser getUser() {
		return user;
	}

	public abstract int getExecutionAmount(double price, int amount);

	@Override
	public int getMaxAmount() {
		return amount;
	}

	public abstract StockMarketOrder getNextOrder(double price, int amount, StockMarket market);

	public StockMarketOrder execute(double price, int amount, StockMarket market) {
		StockMarketShareStack share = market.getShares().get(getShare().getParent().getName());
		share.setAmount(share.getAmount() - amount);
		

		return getNextOrder(price, amount, market);
	}

	protected int getAmount() {
		return amount;
	}

}
