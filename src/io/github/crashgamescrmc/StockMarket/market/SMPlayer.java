package io.github.crashgamescrmc.StockMarket.market;

import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;

import io.github.crashgamescrmc.StockMarket.StockMarketPlugin;
import io.github.crashgamescrmc.StockMarket.market.orders.Order;

public class SMPlayer extends SMUser {

	public SMPlayer(OfflinePlayer player, Map<String, ShareStack> shares, List<Order> orders) {
		super(player.getName(), shares, orders);
		this.player = player;
	}

	private OfflinePlayer player;

	@Override
	public boolean has(double money) {
		return StockMarketPlugin.economy.has(player, money);
	}

	@Override
	public void withdraw(double money) {
		StockMarketPlugin.economy.withdrawPlayer(player, money);
	}

	@Override
	public void deposit(double money) {
		StockMarketPlugin.economy.depositPlayer(player, money);
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public void setPlayer(OfflinePlayer player) {
		this.player = player;
	}

}
