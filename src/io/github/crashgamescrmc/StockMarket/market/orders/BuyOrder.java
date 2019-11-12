package io.github.crashgamescrmc.StockMarket.market.orders;

import io.github.crashgamescrmc.StockMarket.market.NotEnoughMoneyException;
import io.github.crashgamescrmc.StockMarket.market.Share;
import io.github.crashgamescrmc.StockMarket.market.ShareStack;
import io.github.crashgamescrmc.StockMarket.market.StockMarket;

public abstract class BuyOrder extends Order {

	public BuyOrder(Share share, int amount) {
		super(share, amount);
	}

	@Override
	public Order execute(double price, int amount, StockMarket market) {
		ShareStack share = market.getMarket_shares().get(getShare().getName());
		int real_amount = getExecutionAmount(price, amount);

		if (user.has(price * amount)) {
			share.remove(real_amount);
			user.giveShares(getShare(), real_amount);
		} else {
			throw new NotEnoughMoneyException();
		}

		return getNextOrder(price, amount, market);
	}

}
