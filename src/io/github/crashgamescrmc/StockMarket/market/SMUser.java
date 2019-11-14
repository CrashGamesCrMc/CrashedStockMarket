package io.github.crashgamescrmc.StockMarket.market;

import java.util.List;
import java.util.Map;

import io.github.crashgamescrmc.StockMarket.market.orders.Order;

public abstract class SMUser {

	public SMUser(String name, Map<String, ShareStack> shares, List<Order> orders) {
		this.name = name;
		this.shares = shares;
		this.orders = orders;
	}

	private String name;

	private Map<String, ShareStack> shares;
	private List<Order> orders;

	public abstract boolean has(double money);

	public abstract void withdraw(double money);

	public abstract void deposit(double money);

	public void giveShares(ShareStack shareStack) {
		// TODO DB update

		ShareStack stack = shares.get(shareStack.getType().getName());
		if (stack == null) {
			shares.put(shareStack.getType().getName(), shareStack);
		} else {
			stack.merge(shareStack);
		}
	}

	public void giveShares(Share share, int amount) {
		// TODO DB update

		shares.get(share.getName()).add(amount);
	}

	public void removeShares(Share share, int amount) {
		// TODO DB update

		shares.get(share.getName()).remove(amount);
	}

	public boolean hasShares(Share share) {
		return shares.get(share.getName()) == null;
	}

	public boolean hasShares(Share share, int amount) {
		return shares.get(share.getName()) == null ? false
				: shares.get(shares.get(share.getName())).getAmount() >= amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Map<String, ShareStack> getShares() {
		return shares;
	}

	public void setShares(Map<String, ShareStack> shares) {
		this.shares = shares;
	}

}
