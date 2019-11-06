package io.github.crashgamescrmc.StockMarket.market;

public interface StockMarketOrder {

	public StockMarketUser getUser();

	public int getExecutionAmount(double price, int amount);

	public int getMaxAmount();

	public StockMarketOrder execute(double price, int amount);

	public StockMarketShareStack getShare();

}
