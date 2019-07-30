package io.github.crashgamescrmc.StockMarket;

import java.util.Random;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

public class StockMarketThread implements Runnable {

	StockMarketPlugin plugin;

	public StockMarketThread(StockMarketPlugin plugin, Random random) {
		this.plugin = plugin;
		this.random = random;
	}

	public double GetRandom(double begin, double end) {
		return random.nextDouble() * (end - begin) + begin;
	}

	private Random random;

	public static final int RUNNING = 0;
	public static final int STOP = 1;
	public static final int RESTART = 2;

	public static int state = RUNNING;

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		JSONObject shares = StockMarketPlugin.getShares();
		JSONObject share;
		double movement;
		long movement_duration;
		long movement_end;
		long start;
		long end;
		while (true) {
			if (state == RUNNING) {
			} else if (state == STOP) {
				plugin.getLogger().info("Stopped stock market movement thread!");
				break;
			} else if (state == RESTART) {
				new Thread(new StockMarketThread(plugin, random));
				break;
			}
			start = System.currentTimeMillis();
			for (int i = 0; i < shares.size(); i++) {
				share = (JSONObject) shares.values().toArray()[i];

				movement = (double) share.get("movement");
				movement_duration = (long) share.get("movement_duration");
				movement_end = (long) share.get("movement_end");

				if (movement_end < System.currentTimeMillis()) {

					double price_dif = (double) share.get("price") - (double) share.get("current_base");
					// share has no movement
					movement_duration = (long) (1000 * 60 * GetRandom(StockMarketPlugin.getRandomMovementMinTime(),
							StockMarketPlugin.getRandomMovementMaxTime()));
					movement_end = (long) (System.currentTimeMillis() + movement_duration);

					/*
					 * movement =
					 * GetRandom(StockMarketPlugin.getRandomMovementMin(),
					 * StockMarketPlugin.getRandomMovementMin()) GetRandom( 1 +
					 * price_dif / StockMarketPlugin.getMinBaseDistance()
					 * (double) share.get("current_change"), 1 - price_dif /
					 * StockMarketPlugin.getMaxBaseDistance() (double)
					 * share.get("current_change"));
					 */
					movement = GetRandom(StockMarketPlugin.getRandomMovementMin(),
							StockMarketPlugin.getRandomMovementMax())
							* GetRandom(
									(double) share.get("current_change")
											- price_dif / StockMarketPlugin.getMaxBaseDistance()
													* (double) share.get("current_change"),
									(double) share.get("current_change")
											+ price_dif / StockMarketPlugin.getMinBaseDistance()
													* (double) share.get("current_change"));

					if (movement_duration < 60 * 1000 * 60) {
						movement = movement / ((double) 60 / movement_duration);
					}

					share.put("movement", movement);
					share.put("movement_duration", movement_duration);
					share.put("movement_end", movement_end);

					plugin.getLogger()
							.info("New movement for " + shares.keySet().toArray()[i] + ": " + movement + " for "
									+ (movement_duration / 1000 / 60) + " minutes. (possible: min="
									+ (StockMarketPlugin.getRandomMovementMin() * ((double) share.get("current_change")
											+ price_dif / StockMarketPlugin.getMinBaseDistance()
													* (double) share.get("current_change")))
									+ "; max="
									+ (StockMarketPlugin.getRandomMovementMax() * ((double) share.get("current_change")
											+ price_dif / StockMarketPlugin.getMinBaseDistance()
													* (double) share.get("current_change")))
									+ ")");
				}

				// share.put("price", (double) share.get("price") + (movement /
				// (movement_duration / 1000 / 60)));
				share.put("price", (double) share.get("price")
						+ (movement / (movement_duration / 60000)) * (double) share.get("price"));

				if ((double) share.get("price") < (double) share.get("base") * StockMarketPlugin.getBankruptcy()) {
					Bukkit.broadcastMessage(StockMarketCommandExecutor.prefix + (String) shares.keySet().toArray()[i]
							+ " has gone bankrupt! The shares are now redistributed.");
					share.put("current_base", share.get("base"));
					share.put("current_change", share.get("change"));
					share.put("price", share.get("base"));
				}
				plugin.getLogger().info(
						"Movement: " + movement + " - total movement; now: " + movement / (movement_duration / 60000));
			}
			end = System.currentTimeMillis();

			StockMarketPlugin.saveConfigFile();

			try {
				Thread.sleep(60 * 1000 - (end - start));
				plugin.getLogger().info("[Stock Market][Event Thread]: Waiting " + (60 * 1000 - (end - start)) + "ms!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
