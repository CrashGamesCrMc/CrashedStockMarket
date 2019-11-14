package io.github.crashgamescrmc.StockMarket.market;

import io.github.crashgamescrmc.StockMarket.StockMarketPlugin;
import io.github.crashgamescrmc.StockMarket.market.orders.Order;

public class StockMarket extends SMUser {

	public StockMarket(OrderBook orderBook) {
		setOrderBook(orderBook);
	}

	private OrderBook orderBook;

	public OrderBook getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(OrderBook orderBook) {
		this.orderBook = orderBook;
	}

	public void update() {
		double price;
		int amount;

		for (ShareStack share : getShares().values()) {
			amount = share.getAmount();
			price = orderBook.getPriceForMaximumProfit(share.getType(), amount);

			for (Order order : orderBook.getOrdersForShare(share.getType())) {
				order.execute(price, amount, this);
				amount -= order.getExecutionAmount(price, amount);
			}

			share.setAmount(amount);

		}
	}

	@Override
	public boolean has(double money) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void withdraw(double money) {
		StockMarketPlugin.economy
				.withdrawPlayer(StockMarketPlugin.plugin.getConfig().getString(StockMarketPlugin.cBANK_NAME), money);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deposit(double money) {
		StockMarketPlugin.economy
				.depositPlayer(StockMarketPlugin.plugin.getConfig().getString(StockMarketPlugin.cBANK_NAME), money);
	}

}
