package io.github.crashgamescrmc.StockMarket.market;

public class SMAI extends SMUser {

	public SMAI(double stop_loss, double stop_gain, double balance) {
		this.balance = balance;
		this.setStop_gain(stop_gain);
		this.setStop_loss(stop_loss);
	}

	private double stop_loss = 0.90;
	private double stop_gain = 1.03;
	private double start_buy_low = 0.9;
	private double start_buy_high = 1.05;
	private double observing_time = 60;

	private double place_buy_limit = 1.03; // place order limit 3% above the
											// choosen price

	private double balance;

	@Override
	public boolean has(double money) {
		return balance >= money;
	}

	@Override
	public void withdraw(double money) {
		balance -= money;
	}

	@Override
	public void deposit(double money) {
		balance += money;
	}

	public double getStop_gain() {
		return stop_gain;
	}

	public void setStop_gain(double stop_gain) {
		this.stop_gain = stop_gain;
	}

	public double getStop_loss() {
		return stop_loss;
	}

	public void setStop_loss(double stop_loss) {
		this.stop_loss = stop_loss;
	}

	public double getStart_buy_low() {
		return start_buy_low;
	}

	public void setStart_buy_low(double start_buy_low) {
		this.start_buy_low = start_buy_low;
	}

	public double getStart_buy_high() {
		return start_buy_high;
	}

	public void setStart_buy_high(double start_buy_high) {
		this.start_buy_high = start_buy_high;
	}

	public void play(StockMarket market) {
		// buying

		for (ShareStack shareStack : market.getShares().values()) {
			double price = shareStack.getPrice();
		}
	}

	public double getObserving_time() {
		return observing_time;
	}

	public void setObserving_time(double observing_time) {
		this.observing_time = observing_time;
	}

}
