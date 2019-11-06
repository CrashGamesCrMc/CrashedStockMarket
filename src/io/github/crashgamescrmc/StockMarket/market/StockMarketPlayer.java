package io.github.crashgamescrmc.StockMarket.market;

import java.util.UUID;

public abstract class StockMarketPlayer extends StockMarketUser {

	public abstract void grant(double amount);

	public abstract void withdraw(double amount);

	private UUID uuid;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
