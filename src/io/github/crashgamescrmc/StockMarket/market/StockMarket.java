package io.github.crashgamescrmc.StockMarket.market;

import java.util.HashMap;
import java.util.Map;

public class StockMarket {

	private Map<String, ShareStack> market_shares = new HashMap<String, ShareStack>();
	private OrderBook orderBook;

	public Map<String, ShareStack> getMarket_shares() {
		return market_shares;
	}

	public void setMarket_shares(Map<String, ShareStack> market_shares) {
		this.market_shares = market_shares;
	}

	public OrderBook getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(OrderBook orderBook) {
		this.orderBook = orderBook;
	}

	public void update() {
		double price;
		int amount;
		for (ShareStack share : market_shares.values()) {
			amount = share.getAmount();
			price = orderBook.getPriceForMaximumProfit(share.getType(), amount);

			for (Order order : orderBook.getOrdersForShare(share.getType())) {
				order.execute(price, amount, this);
				amount -= order.getExecutionAmount(price, amount);
			}

			share.setAmount(amount);

		}
	}

}
