package io.github.crashgamescrmc.StockMarket.market;

public class StockMarketBuyOrderCheapest extends StockMarketBuyOrder {

	@Override
	public int getExecutionAmount(double price, int amount) {
		if (amount > getAmount()) {
			return amount;
		} else {
			return getAmount();
		}
	}

	@Override
	public StockMarketOrder getNextOrder(double price, int amount, StockMarket market) {
		return null;
	}

}
