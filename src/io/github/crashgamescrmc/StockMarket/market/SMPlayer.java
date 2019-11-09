package io.github.crashgamescrmc.StockMarket.market;

import org.bukkit.OfflinePlayer;

import io.github.crashgamescrmc.StockMarket.StockMarketPlugin;

public class SMPlayer extends SMUser {

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
