package io.github.crashgamescrmc.StockMarket.market;

import java.util.Comparator;

public class DoubleComparator implements Comparator<Double> {

	@Override
	public int compare(Double o1, Double o2) {
		if (o1 < o2) {
			return -1;
		} else if (o1 == o2) {
			return 0;
		}
		return 1;
	}

}
