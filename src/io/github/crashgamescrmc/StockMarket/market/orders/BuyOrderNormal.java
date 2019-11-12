package io.github.crashgamescrmc.StockMarket.market.orders;

import io.github.crashgamescrmc.StockMarket.market.Share;
import io.github.crashgamescrmc.StockMarket.market.StockMarket;

public class BuyOrderNormal extends BuyOrder {

	private int limit;

	public BuyOrderNormal(Share share, int amount, int limit) {
		super(share, amount);
		this.limit = limit;
	}

	@Override
	public Order getNextOrder(double price, int amount, StockMarket market) {
		return getMaxAmount() == amount ? null : new BuyOrderNormal(getShare(), getMaxAmount() - amount, limit);
	}

	@Override
	public int getExecutionAmount(double price, int amount) {
		return getMaxAmount() <= amount ? getMaxAmount() : amount;
	}

	@Override
	public double getPriceLimit() {
		return limit;
	}

}
