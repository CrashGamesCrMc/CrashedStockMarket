package io.github.crashgamescrmc.StockMarket.market;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Share {

	private double price;
	private Company company;
	private CustomQueue<Double> last_prices = new CustomQueue<Double>();

	public String getName() {
		return company.getName();
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double[] getLastPrices(int count) {
		double[] out = new double[count];

		Iterator<Double> it = last_prices.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i == count) {
				return out;
			}
			out[i] = it.next();
			i++;
		}

		double avg = getAvgPrice(i);
		for (int j = 0; j < count - i; j++) {
			out[i + j] = avg;
		}
		return out;
	}

	public List<Double> getLastPricesAsList(int count) {
		List<Double> out = new ArrayList<Double>(count);

		Iterator<Double> it = last_prices.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i == count) {
				return out;
			}
			out.add(it.next());
			i++;
		}

		double avg = getAvgPrice(i);
		for (int j = 0; j < count - i; j++) {
			out.add(avg);
		}
		return out;
	}

	public double getAvgPrice(int count) {
		int i;
		double total = 0;
		Iterator<Double> it = last_prices.iterator();
		for (i = 0; i < count; i++) {
			if (!it.hasNext()) {
				break;
			}
			total += it.next();
		}
		return total / i;
	}

	public double getAvgGaus(int count, int cutoff) {
		List<Double> prices = getLastPricesAsList(count);
		prices.sort(new DoubleComparator());

		int c1 = count / 3;
		if (c1 == 0) {
			c1 = 1;
		}
		int c2 = 2 / 3 * count;

		double total = 0;
		int runs = 0;
		for (int i = c1 - 1; i < c2; i++) {
			total += prices.get(i);
			runs++;
		}
		return total / runs;
	}

	public double getAmplitude(int duration, int count, int gausCutoff) {
		if (last_prices.size() == 1) {
			return new double[] { price };
		} else if (duration > last_prices.size()) {
			return getAmplitude(last_prices.size(), count);
		} else if (count * 2 > last_prices.size()) {
			return getAmplitude(duration, last_prices.size() / 2);
		} else {
			List<Double> prices = getLastPricesAsList(duration);
			prices.sort(new DoubleComparator());
			double baseline = getAvgGaus(count, gausCutoff);
			double total_high = 0;
			double total_low = 0;
			for (int i = 0; i < Math.min(prices.size(), count / 2); i++) {
				total_high += prices.get(prices.size() - 1 - i);
				total_low += prices.get(i);
			}
		}
	}

}
