package io.github.crashgamescrmc.StockMarket.market;

public abstract class SellOrder extends Order {

	public SellOrder(Share share, int amount) {
		super(share, amount);
	}

	@Override
	public Order execute(double price, int amount, StockMarket market) {
		ShareStack share = market.getMarket_shares().get(getShare().getName());
		int real_amount = getExecutionAmount(price, amount);

		if (user.hasShares(share.getType(), real_amount)) {
			share.add(real_amount);
			user.removeShares(getShare(), real_amount);
		} else {
			throw new NotEnoughMoneyException();
		}

		return getNextOrder(price, amount, market);
	}

}
