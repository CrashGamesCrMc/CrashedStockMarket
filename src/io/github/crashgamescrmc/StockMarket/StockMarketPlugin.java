package io.github.crashgamescrmc.StockMarket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.milkbowl.vault.economy.Economy;

public class StockMarketPlugin extends JavaPlugin {

	public static Economy economy;
	public static StockMarketPlugin plugin;
	public static JSONObject config;
	public static final String config_file_path = "stocks.json";

	public static final String version = "0.3.4";
	public static final long build = 2;

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {

		plugin = this;

		if (!setupEconomey()) {
			getLogger().info("Economy could not be set up, shutting down server!");
			Bukkit.shutdown();
		}

		getServer().getPluginManager().registerEvents(new JoinListener(), this);

		if (!new File(config_file_path).exists()) {
			try {
				JSONObject master = new JSONObject();

				JSONObject settings = new JSONObject();
				settings.put("version", version);
				settings.put("build", build);
				settings.put("change_time", 1440);
				settings.put("random_movement_max_time", 48 * 60);
				settings.put("random_movement_min_time", 60);
				settings.put("random_movement_max", 1.0);
				settings.put("random_movement_min", 0.1);
				settings.put("max_base_distance", 10.0); // max value from base
															// price
															// (10*change+base)
				settings.put("min_base_distance", -10.0);
				settings.put("bankruptcy_from_base", 0.5);
				settings.put("round", 4);

				master.put("settings", settings);

				JSONObject market = new JSONObject();

				JSONObject shares = new JSONObject();

				/*
				 * JSONObject Apple = new JSONObject(); Apple.put("name",
				 * "Apple Computer Inc."); Apple.put("base", 165.0); Apple.put("change", 0.03);
				 * Apple.put("current_base", 165.0); Apple.put("current_change", 0.03);
				 * Apple.put("price", Apple.get("base")); Apple.put("event_chance", 0.05);
				 * Apple.put("movement",0.0); Apple.put("movement_end", 0L);
				 * Apple.put("movement_duration", 0L);
				 * 
				 * shares.put("Apple", Apple);
				 */

				market.put("shares", shares);

				// currencies

				JSONObject currencies = new JSONObject();

				JSONObject Euro = new JSONObject();
				Euro.put("name", "Euro");
				Euro.put("base", 0.9);
				Euro.put("change", 0.005);
				Euro.put("current_base", 0.9);
				Euro.put("current_change", 0.005);
				Euro.put("price", Euro.get("base"));
				Euro.put("event_chance", 0.025);

				currencies.put("EUR", Euro);

				market.put("currencies", currencies);

				master.put("market", market);

				JSONObject users = new JSONObject();

				master.put("users", users);

				getLogger().info(master.toJSONString());

				// file logic

				FileWriter fw = new FileWriter(config_file_path);
				fw.write(master.toJSONString());
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// read file

		try {

			FileReader fr = new FileReader(config_file_path);
			JSONParser parser = new JSONParser();

			config = (JSONObject) parser.parse(fr);

			JSONObject settings = getSettings();

			if (Utils.isSmaller(getConfigVersion(), getConfigBuild(), "0.2.0", 1)) {

				getLogger().info("§e§lAdapting config from prior to 0.2.0!");
				// share movement update

				addGenericShareMovements();

				getSettings().put("max_base_distance", 10.0);

				getSettings().put("min_base_distance", -10.0);

				settings.put("random_movement_max_time", 48 * 60);
				settings.put("random_movement_min_time", 3);
				settings.put("random_movement_max", 1.0);
				settings.put("random_movement_min", 0.1);
			}
			if (Utils.isSmaller(getConfigVersion(), getConfigBuild(), "0.3.1", 1)) {
				getLogger().info("§e§lAdapting config from prior to 0.3.1!");
				settings.put("bankruptcy_from_base", 0.5);
			}
			if (Utils.isSmaller(getConfigVersion(), getConfigBuild(), "0.3.4", 1)) {
				getLogger().info("§e§lAdapting config from prior to 0.3.4!");
				settings.put("rounding", 4);
			}

			getSettings().put("version", version);
			getSettings().put("build", build);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new StockMarketCommandExecutor(this);
		new Thread(new StockMarketThread(this, new Random())).start();

		getLogger().info("Enabled StockMarket!");

		saveConfigFile();
	}

	public void onDisable() {
		System.out.println("Saving config data!");
		saveConfigFile();
		System.out.println("Disabled StockMarket!");
	}

	private boolean setupEconomey() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return economy != null;
	}

	@SuppressWarnings("unchecked")
	public static void parseConfig() {
		FileReader fr;
		try {
			fr = new FileReader(config_file_path);
			JSONParser parser = new JSONParser();

			config = (JSONObject) parser.parse(fr);

			JSONObject settings = getSettings();

			plugin.getLogger().info("config: version=" + getConfigVersion() + "; build=" + getConfigBuild());

			if (Utils.isSmaller(getConfigVersion(), getConfigBuild(), "0.2.0", 1)) {

				plugin.getLogger().info("§e§lAdapting config from prior to 0.2.0!");
				// share movement update

				addGenericShareMovements();

				getSettings().put("max_base_distance", 10.0);

				getSettings().put("min_base_distance", -10.0);

				settings.put("random_movement_max_time", 48 * 60);
				settings.put("random_movement_min_time", 3);
				settings.put("random_movement_max", 1.0);
				settings.put("random_movement_min", 0.1);
			}
			if (Utils.isSmaller(getConfigVersion(), getConfigBuild(), "0.3.1", 1)) {
				plugin.getLogger().info("§e§lAdapting config from prior to 0.3.1!");
				settings.put("bankruptcy_from_base", 0.5);
			}

			getSettings().put("version", version);
			getSettings().put("build", build);

			config = (JSONObject) parser.parse(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveConfigFile() {
		FileWriter fw;
		try {
			fw = new FileWriter(config_file_path);
			fw.write(config.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void addUser(String username) {
		JSONObject user = new JSONObject();
		JSONObject market = new JSONObject();
		JSONObject shares = new JSONObject();
		JSONObject external_shares = getShares();
		JSONObject user_share_data;

		for (int i = 0; i < external_shares.size(); i++) {
			user_share_data = new JSONObject();
			user_share_data.put("buy_price", -1.0);
			user_share_data.put("amount", 0);
			shares.put(external_shares.keySet().toArray()[i], user_share_data);
		}
		market.put("shares", shares);
		user.put("market", market);
		((JSONObject) config.get("users")).put(username, user);
	}

	public static int GetChangeTime() {
		return (int) ((JSONObject) config.get("settings")).get("change_time");
	}

	public static JSONObject getShares() {
		return (JSONObject) ((JSONObject) config.get("market")).get("shares");
	}

	public static JSONObject getCurrencies() {
		return (JSONObject) ((JSONObject) config.get("market")).get("currencies");
	}

	public static JSONObject getUser(String username) {
		return (JSONObject) ((JSONObject) config.get("users")).get(username);
	}

	public static JSONObject getUsers() {
		return (JSONObject) config.get("users");
	}

	public static JSONObject getSettings() {
		return (JSONObject) config.get("settings");
	}

	public static int getShareIndex(String share) {
		Object[] shares = getShares().keySet().toArray();
		for (int i = 0; i < shares.length; i++) {
			if (share.equals(shares[i])) {
				return i;
			}
		}
		return -1;
	}

	public static long getRandomMovementMaxTime() {
		return (long) getSettings().get("random_movement_max_time");
	}

	public static long getRandomMovementMinTime() {
		return (long) getSettings().get("random_movement_min_time");
	}

	public static double getRandomMovementMin() {
		return (double) getSettings().get("random_movement_min");
	}

	public static double getRandomMovementMax() {
		return (double) getSettings().get("random_movement_max");
	}

	public static String getConfigVersion() {
		return (String) getSettings().get("version");
	}

	public static long getConfigBuild() {
		return (long) getSettings().get("build");
	}

	public static double getMinBaseDistance() {
		return (double) getSettings().get("min_base_distance");
	}

	public static double getMaxBaseDistance() {
		return (double) getSettings().get("max_base_distance");
	}

	public static double getBankruptcy() {
		return (double) getSettings().get("bankruptcy_from_base");
	}

	@SuppressWarnings("unchecked")
	public static void addShare(String ID, String name, double base, double change, double current_base,
			double current_change, double event_chance, double price) {
		JSONObject share = new JSONObject();
		share.put("name", name);
		share.put("base", base);
		share.put("change", change);
		share.put("current_base", current_base);
		share.put("current_change", current_change);
		share.put("event_chance", event_chance);
		share.put("price", price);

		getShares().put(ID, share);
		JSONObject users = getUsers();
		for (int i = 0; i < users.size(); i++) {
			addShareToUser(ID, (String) users.keySet().toArray()[i]);
		}
		addGenericShareMovement(ID);
	}

	@SuppressWarnings("unchecked")
	public static void addShareToUser(String ID, String username) {
		JSONObject share = new JSONObject();
		share.put("buy_price", -1.0);
		share.put("amount", 0L);
		((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) config.get("users")).get(username)).get("market"))
				.get("shares")).put(ID, share);
	}

	@SuppressWarnings("unchecked")
	public static void addGenericShareMovement(String share) {
		JSONObject _share = getShare(share);
		_share.put("movement", 0.0);
		_share.put("movement_end", 0L);
		_share.put("movement_duration", 0L);
	}

	public static void addGenericShareMovements() {
		String share_name;
		for (Object share : getShares().keySet().toArray()) {
			share_name = (String) share;
			addGenericShareMovement(share_name);
		}
	}

	public static JSONObject getShare(String share) {
		return (JSONObject) getShares().get(share);
	}

}
