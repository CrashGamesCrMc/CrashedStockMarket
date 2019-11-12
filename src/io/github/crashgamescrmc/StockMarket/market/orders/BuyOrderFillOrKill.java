package io.github.crashgamescrmc.StockMarket.market.orders;

import io.github.crashgamescrmc.StockMarket.market.Share;
import io.github.crashgamescrmc.StockMarket.market.StockMarket;

public class BuyOrderFillOrKill extends BuyOrder {

	public BuyOrderFillOrKill(Share share, int amount) {
		super(share, amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Order getNextOrder(double price, int amount, StockMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getExecutionAmount(double price, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPriceLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

}
