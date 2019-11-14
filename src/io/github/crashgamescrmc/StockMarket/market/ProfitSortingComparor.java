package io.github.crashgamescrmc.StockMarket.market;

import java.util.Comparator;

import io.github.crashgamescrmc.StockMarket.market.orders.BuyOrder;
import io.github.crashgamescrmc.StockMarket.market.orders.Order;
import io.github.crashgamescrmc.StockMarket.market.orders.SellOrder;

public class ProfitSortingComparor implements Comparator<Order> {

	@Override
	public int compare(Order arg0, Order arg1) {
		if (arg0 instanceof BuyOrder && arg1 instanceof SellOrder) {
			return 1;
		}
		return -1;
	}

}
