package io.github.crashgamescrmc.StockMarket.market;

import java.util.HashMap;
import java.util.Map;

public class StockMarket {

	private Map<String, StockMarketShareStack> shares = new HashMap<String, StockMarketShareStack>();

	public Map<String, StockMarketShareStack> getShares() {
		return shares;
	}

	public void setShares(Map<String, StockMarketShareStack> shares) {
		this.shares = shares;
	}

}
