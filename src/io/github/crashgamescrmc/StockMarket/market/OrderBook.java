package io.github.crashgamescrmc.StockMarket.market;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.crashgamescrmc.StockMarket.market.orders.Order;
import io.github.crashgamescrmc.StockMarket.market.orders.SellOrder;

public class OrderBook {

	public OrderBook(Iterable<Share> shares) {
		orders = new HashMap<String, List<Order>>();

		for (Share share : shares) {
			orders.put(share.getName(), new ArrayList<Order>());
		}
	}

	private Map<String, List<Order>> orders;

	private Comparator<Order> profitSortingComparor = new ProfitSortingComparor();

	public void addOrder(Order order) {
		orders.get(order.getShare().getName()).add(order);
	}

	public List<Order> getOrdersForShare(Share share) {
		return orders.get(share.getName());
	}

	public List<Order> getOrdersForShare(String share) {
		return orders.get(share);
	}

	public double getPriceForMaximumProfit(Share share, int amount_in_stock) {
		List<Order> list = orders.get(share.getName());
		list.sort(profitSortingComparor);

		int transactions;

		// TODO better efficiency

		double max_profit_price = share.getPrice();
		double max_profit = 0;

		double tmp_price;
		int exec_amount;

		for (Order order_for_price : list) {
			transactions = 0;
			tmp_price = order_for_price.getPriceLimit();
			if (tmp_price == Double.NaN) {
				continue;
			}
			for (Order order : list) {
				exec_amount = order.getExecutionAmount(tmp_price, amount_in_stock);

				if (order instanceof SellOrder) {
					amount_in_stock += exec_amount;
				} else {
					amount_in_stock -= exec_amount;
				}

				transactions += exec_amount;
			}
			if (tmp_price * transactions > max_profit) {
				max_profit_price = tmp_price;
			}
		}

		return max_profit_price;

	}

}
