package io.github.crashgamescrmc.StockMarket.market;

public class BuyOrderCheapest extends BuyOrder {

	public BuyOrderCheapest(Share share, int amount) {
		super(share, amount);
	}

	@Override
	public int getExecutionAmount(double price, int amount) {
		if (amount > getMaxAmount()) {
			return amount;
		} else {
			return getMaxAmount();
		}
	}

	@Override
	public Order getNextOrder(double price, int amount, StockMarket market) {
		return amount != getMaxAmount() ? new BuyOrderCheapest(getShare(), getMaxAmount() - amount) : null;
	}

	@Override
	public double getPriceLimit() {
		return Double.NaN;
	}

}
