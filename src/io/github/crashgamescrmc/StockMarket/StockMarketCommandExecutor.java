package io.github.crashgamescrmc.StockMarket;

import java.util.Random;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class StockMarketCommandExecutor implements CommandExecutor {

	public StockMarketPlugin plugin;

	public StockMarketCommandExecutor(StockMarketPlugin plugin) {
		plugin.getCommand("sm").setExecutor(this);
		this.plugin = plugin;
	}

	public static String PermissionDenied(String[] permissions) {
		String output = prefix + "§4You do not have permission to run this command.\n" + prefix
				+ "Permissions that grant access to this command:\n";
		for (String permission : permissions) {
			output += prefix + " - " + permission + "\n";
		}
		return output;
	}

	public static boolean hasPermission(CommandSender sender, String[] permissions) {
		if (sender instanceof Server) {
			return true;
		}
		if (sender.isOp()) {
			return true;
		}
		for (String permission : permissions) {
			if (sender.hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	public static final String prefix = "§f[§aStock Market§f]: §a";

	public static final String error_only_players = prefix + "§4Only players can run this command!";
	public static final String error_not_enough_arguments = prefix + "§4Not enough aruments to execute command!";
	public static final String error_invalid_share = prefix + "§4Invalid share ID!";
	public static final String error_invalid_good = prefix + "§4Invalid type of good!";
	public static final String error_not_enough_shares = prefix + "§4Not enough shares!";
	public static final String error_subcommand_not_found = prefix + "§4Subcommand not found!";

	public static final String[] permission_config = new String[] { "StockMarket.config", "StockMarket.*" };
	public static final String[] permission_buy = new String[] { "StockMarket.buy", "StockMarket.member",
			"StockMarket.*" };
	public static final String[] permission_buy_share = new String[] { "StockMarket.buy.share", "StockMarket.member",
			"StockMarket.*" };
	public static final String[] permission_list = new String[] { "StockMarket.list", "StockMarket.member",
			"StockMarket.*" };
	public static final String[] permission_add_share = new String[] { "StockMarket.add.*", "StockMarket.add.share",
			"StockMarket.*" };
	public static final String[] permission_help = new String[] { "StockMarket.help", "StockMarket.member",
			"StockMarket.*" };
	public static final String[] permission_add = new String[] { "StockMarket.add", "StockMarket.*" };
	public static final String[] permission_show_self = new String[] { "StockMarket.show.self", "StockMarket.show.*",
			"StockMarket.member", "Stockmarket.*", };
	public static final String[] permission_show_others = new String[] { "StockMarket.show.others",
			"StockMarket.show.*", "StockMarket.member", "StockMarket.*" };
	public static final String[] permission_sell = new String[] { "StockMarket.sell", "StockMarket.member",
			"StockMarket.*" };
	public static final String[] permission_sell_share = new String[] { "StockMarket.sell.share", "StockMarket.sell.*",
			"StockMarket.member", "StockMarket.*" };
	public static final String[] permission_config_help = new String[] { "StockMarket.config.*",
			"StockMarket.config.help", "StockMarket.*" };
	public static final String[] permission_config_share_ID = new String[] { "StockMarket.*", "StockMarket.config.*",
			"StockMarket.config.share.*", "StockMarket.config.share.ID" };
	public static final String[] permission_config_share_name = new String[] { "StockMarket.*", "StockMarket.config.*",
			"StockMarket.config.share.*", "StockMarket.config.share.name" };
	public static final String[] permission_config_share_base = new String[] { "StockMarket.*", "StockMarket.config.*",
			"StockMarket.config.share.*", "StockMarket.config.share.base" };
	public static final String[] permission_config_share_current_base = new String[] { "StockMarket.*",
			"StockMarket.config.*", "StockMarket.config.share.*", "StockMarket.config.share.current_base" };
	public static final String[] permission_config_share_change = new String[] { "StockMarket.*",
			"StockMarket.config.*", "StockMarket.config.share.*", "StockMarket.config.share.change" };
	public static final String[] permission_config_share_current_change = new String[] { "StockMarket.*",
			"StockMarket.config.*", "StockMarket.config.share.*", "StockMarket.config.share.current_change" };
	public static final String[] permission_config_share_show = new String[] { "StockMarket.*", "StockMarket.config.*",
			"StockMarket.config.share.*", "StockMarket.config.share.show" };
	public static final String[] permission_reload = new String[] { "StockMarket.*", "StockMarket.reload" };
	public static final String[] permission_version = new String[] { "StockMarket.version", "StockMarket.*" };
	public static final String[] permission_forcemovements = new String[] { "StockMarket.forcemovements",
			"StockMarket.*" };
	public static final String[] permission_thread_stop = new String[] { "StockMarket.*", "StockMarket.thread.*",
			"StockMarket.thread.stop" };
	public static final String[] permission_thread_start = new String[] { "StockMarket.*", "StockMarket.thread.*",
			"StockMarket.thread.start" };
	public static final String[] permission_thread_restart = new String[] { "StockMarket.thread.restart",
			"StockMarket.thread.*", "StockMarket.*" };

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		System.out.print("Processing");

		if (cmd.getName().equalsIgnoreCase("sm")) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("config")) {
					if (args.length == 1) {
						if (!(hasPermission(sender, permission_config))) {
							sender.sendMessage(PermissionDenied(permission_config));
							return true;
						}
						sender.sendMessage(StockMarketPlugin.config.toJSONString());
					} else {
						if (args[1].equalsIgnoreCase("help")) {
							if (!hasPermission(sender, permission_config_help)) {
								sender.sendMessage(PermissionDenied(permission_config_help));
								return true;
							}
							sender.sendMessage(prefix + "Stock Market subcommands of /sm config:\n" + prefix
									+ "(commands setting values can be used as a query when not providing a new value)");
							sender.sendMessage(
									prefix + "/sm config share ID <ID> [<new_ID>] -> redefines the share's ID");
							sender.sendMessage(
									prefix + "/sm config share name <ID> [<new_name>] -> sets the share's name");
							sender.sendMessage(prefix
									+ "/sm config share base <ID> [<new_base>] -> sets the share's base value (the base value after a reset)");
							sender.sendMessage(prefix
									+ "/sm config share current_base <ID> [<new_current_base>] -> sets the share's current base value");
							sender.sendMessage(prefix
									+ "/sm config share change <ID> [<new_change>] -> sets the share's change value (relative to the share's value; change value after a reset)");
							sender.sendMessage(prefix
									+ "/sm config share current_change <ID> [<new_current_change>] -> sets the share's current change value");
							sender.sendMessage(
									prefix + "/sm config share show <ID> -> shows the config value for the share");
						} else if (args[1].equalsIgnoreCase("share")) {
							if (args.length == 2) {
								sender.sendMessage(error_not_enough_arguments);
								return true;
							}

							if (args[2].equalsIgnoreCase("ID")) {
								if (!hasPermission(sender, permission_config_share_ID)) {
									sender.sendMessage(PermissionDenied(permission_config_share_ID));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}
								if (args.length == 4) {
									sender.sendMessage(prefix + "ID: " + args[3]);
									return true;
								}
								JSONObject share = (JSONObject) StockMarketPlugin.getShares().remove(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}
								StockMarketPlugin.getShares().put(args[4], share);
								sender.sendMessage(prefix + "Set ID of " + args[3] + " to " + args[4] + "!");
							} else if (args[2].equalsIgnoreCase("name")) {
								if (!hasPermission(sender, permission_config_share_name)) {
									sender.sendMessage(PermissionDenied(permission_config_share_name));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}

								JSONObject share = (JSONObject) StockMarketPlugin.getShare(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}

								if (args.length == 4) {
									sender.sendMessage(
											prefix + "name of " + args[3] + " is " + (double) share.get("name"));
									return true;
								}
								share.put("name", args[4]);
								sender.sendMessage(prefix + "Set name of " + args[3] + " to " + args[4] + "!");
							} else if (args[2].equalsIgnoreCase("base")) {
								if (!hasPermission(sender, permission_config_share_base)) {
									sender.sendMessage(PermissionDenied(permission_config_share_base));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}

								JSONObject share = (JSONObject) StockMarketPlugin.getShare(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}

								if (args.length == 4) {
									sender.sendMessage(
											prefix + "base of " + args[3] + " is " + (double) share.get("base"));
									return true;
								}
								share.put("base", args[4]);
								sender.sendMessage(prefix + "Set base of " + args[3] + " to " + args[4] + "!");
							} else if (args[2].equalsIgnoreCase("current_base")) {
								if (!hasPermission(sender, permission_config_share_current_base)) {
									sender.sendMessage(PermissionDenied(permission_config_share_current_base));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}

								JSONObject share = (JSONObject) StockMarketPlugin.getShare(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}

								if (args.length == 4) {
									sender.sendMessage(prefix + "current base of " + args[3] + " is "
											+ (String) share.get("current_base"));
									return true;
								}
								share.put("name", args[4]);
								sender.sendMessage(prefix + "Set current base of " + args[3] + " to " + args[4] + "!");
							} else if (args[2].equalsIgnoreCase("change")) {
								if (!hasPermission(sender, permission_config_share_change)) {
									sender.sendMessage(PermissionDenied(permission_config_share_change));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}

								JSONObject share = (JSONObject) StockMarketPlugin.getShare(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}

								if (args.length == 4) {
									sender.sendMessage(
											prefix + "change of " + args[3] + " is " + (double) share.get("change"));
									return true;
								}
								share.put("change", args[4]);
								sender.sendMessage(prefix + "Set change of " + args[3] + " to " + args[4] + "!");
							} else if (args[2].equalsIgnoreCase("current_change")) {
								if (!hasPermission(sender, permission_config_share_current_change)) {
									sender.sendMessage(PermissionDenied(permission_config_share_current_change));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}

								JSONObject share = (JSONObject) StockMarketPlugin.getShare(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}

								if (args.length == 4) {
									sender.sendMessage(prefix + "current change of " + args[3] + " is "
											+ (double) share.get("current_change"));
									return true;
								}
								share.put("current_change", args[4]);
								sender.sendMessage(
										prefix + "Set current change of " + args[3] + " to " + args[4] + "!");
							} else if (args[2].equalsIgnoreCase("show")) {
								if (!hasPermission(sender, permission_config_share_show)) {
									sender.sendMessage(PermissionDenied(permission_config_share_show));
									return true;
								}
								if (args.length == 3) {
									sender.sendMessage(error_not_enough_arguments);
									return true;
								}
								sender.sendMessage(prefix + "share config for " + args[3] + ":");

								JSONObject share = StockMarketPlugin.getShare(args[3]);
								if (share == null) {
									sender.sendMessage(error_invalid_share);
									return true;
								}
								sender.sendMessage("§a" + share.toJSONString());
							} else {
								sender.sendMessage(error_subcommand_not_found);
							}
						} else {
							sender.sendMessage(error_subcommand_not_found);
						}
					}
				} else if (args[0].equalsIgnoreCase("list")) {
					if (!(hasPermission(sender, permission_list))) {
						sender.sendMessage(PermissionDenied(permission_list));
						return true;
					}
					sender.sendMessage(prefix + "========== Shares ==========\n");
					JSONObject shares = StockMarketPlugin.getShares();
					JSONObject share;
					for (int i = 0; i < shares.size(); i++) {
						share = (JSONObject) shares.values().toArray()[i];
						sender.sendMessage(prefix + "§e" + share.get("name") + " (" + shares.keySet().toArray()[i]
								+ ")§f - Price: " + share.get("price"));
					}
					sender.sendMessage(prefix + "========== Currencies ==========\n");
					JSONObject currencies = StockMarketPlugin.getCurrencies();
					JSONObject currency;
					for (int i = 0; i < currencies.size(); i++) {
						currency = (JSONObject) currencies.values().toArray()[i];
						sender.sendMessage(prefix + "§a" + currency.get("name") + " ("
								+ currencies.keySet().toArray()[i] + ")§f - Price: " + currency.get("price"));
					}
				} else if (args[0].equalsIgnoreCase("add")) {
					if (args.length < 2) {
						sender.sendMessage(prefix + "Stock Market subcommands for /sm add:");
						sender.sendMessage(
								prefix + "/sm add share <ID> <name> <base> <change> -> adds a share to the market");
						return true;
					}
					if (args[1].equalsIgnoreCase("share")) {
						if (!(hasPermission(sender, permission_add_share))) {
							sender.sendMessage(PermissionDenied(permission_add_share));
							return true;
						}
						double base = Double.parseDouble(args[4]);
						double change = Double.parseDouble(args[5]);
						StockMarketPlugin.addShare(args[2], args[3].replace("_", " "), base, change, base, change, 0.05,
								base);
					} else {
						sender.sendMessage(error_invalid_good);
					}
				} else if (args[0].equalsIgnoreCase("buy")) {
					if (args.length <= 1) {
						if (!(hasPermission(sender, permission_buy))) {
							sender.sendMessage(PermissionDenied(permission_buy));
							return true;
						}
						sender.sendMessage(prefix + "Stock Market subcommands for /sm buy:");
						sender.sendMessage(prefix + "/sm buy share <ID> [<amount>]");
					} else {
						if (args[1].equalsIgnoreCase("share")) {
							if (!(hasPermission(sender, permission_buy_share))) {
								sender.sendMessage(PermissionDenied(permission_buy_share));
								return true;
							}
							if (!(sender instanceof Player)) {
								sender.sendMessage(error_only_players);
							}
							if (!(args.length >= 3)) {
								sender.sendMessage(error_not_enough_arguments);
								return true;
							}
							JSONObject share = (JSONObject) StockMarketPlugin.getShares().get(args[2]);
							if (share == null) {
								sender.sendMessage(error_invalid_share);
								return true;
							}
							double price = (double) share.get("price");
							int amount = 1;
							if (args.length >= 4) {
								try {
									amount = Integer.parseInt(args[3]);
								} catch (NumberFormatException e) {
									sender.sendMessage(
											prefix + "§4Error while executing command! The given amount was not a number. Exception: "
													+ e.getMessage());
									return true;
								}
							}
							double total_price = price * amount;
							if (!StockMarketPlugin.economy.has(sender.getName(), total_price)) {
								sender.sendMessage(prefix + "§4Not enough money!");
								return true;
							}
							Player player = (Player) sender;
							StockMarketPlugin.economy.withdrawPlayer(player, total_price);
							JSONObject user = StockMarketPlugin.getUser(player.getName());
							JSONObject user_share = (JSONObject) ((JSONObject) ((JSONObject) user.get("market"))
									.get("shares")).get(args[2]);
							user_share.put("amount", (long) user_share.get("amount") + amount);
							user_share.put("buy_price", price);

							sender.sendMessage(prefix + "Bought " + amount + " shares of " + share.get("name")
									+ ", costing " + price + " each.");
						} else {
							sender.sendMessage(error_invalid_good);
						}
					}
				} else if (args[0].equalsIgnoreCase("show")) {
					if (args.length == 1) {
						if (!(hasPermission(sender, permission_show_self))) {
							sender.sendMessage(PermissionDenied(permission_show_self));
							return true;
						}
						if (!(sender instanceof Player)) {
							sender.sendMessage(error_only_players);
							return true;
						}

						Player player = (Player) sender;
						sender.sendMessage(prefix + "Your deposit:");
						JSONObject user = StockMarketPlugin.getUser(player.getName());
						JSONObject user_shares = (JSONObject) ((JSONObject) user.get("market")).get("shares");
						JSONObject user_share;
						String share_name;
						JSONObject share;

						sender.sendMessage(prefix + "---------- shares ----------");
						for (int i = 0; i < user_shares.size(); i++) {
							user_share = (JSONObject) user_shares.values().toArray()[i];
							share_name = (String) user_shares.keySet().toArray()[i];
							share = (JSONObject) StockMarketPlugin.getShares().get(share_name);

							if ((long) user_share.get("amount") > 0) {

								sender.sendMessage(prefix + "" + share_name + " - Buy price: "
										+ (double) user_share.get("buy_price") + " - Price: "
										+ (double) share.get("price") + " - Amount: " + user_share.get("amount")
										+ " - Change: "
										+ (((double) share.get("price") / (double) user_share.get("buy_price") - 1)
												* 100 + "%"));
							}
						}
					} else {
						// copy of above, but shows other people's goods
						if (!(hasPermission(sender, permission_show_others))) {
							sender.sendMessage(PermissionDenied(permission_show_others));
							return true;
						}

						sender.sendMessage(prefix + "" + args[1] + "'s deposit:");

						JSONObject user = StockMarketPlugin.getUser(args[1]);
						if (user == null) {
							sender.sendMessage(prefix + "User does not exist!");
							return true;
						}

						JSONObject user_shares = (JSONObject) ((JSONObject) user.get("market")).get("shares");
						JSONObject user_share;
						String share_name;
						JSONObject share;

						sender.sendMessage(prefix + "---------- shares ----------");
						for (int i = 0; i < user_shares.size(); i++) {
							user_share = (JSONObject) user_shares.values().toArray()[i];
							share_name = (String) user_shares.keySet().toArray()[i];
							share = (JSONObject) StockMarketPlugin.getShares().get(share_name);

							if ((long) user_share.get("amount") > 0) {
								sender.sendMessage(prefix + "" + share_name + " - Buy price: "
										+ (double) user_share.get("buy_price") + " - Price: "
										+ (double) share.get("price") + " - Amount: " + user_share.get("amount"));
							}
						}

					}
				} else if (args[0].equalsIgnoreCase("sell")) {
					if (args.length == 1) {
						if (!(hasPermission(sender, permission_sell))) {
							sender.sendMessage(PermissionDenied(permission_sell));
							return true;
						}
						sender.sendMessage(prefix + "Stock Market subcommands for /sm sell:");
						sender.sendMessage(prefix + "/sm sell share <ID>");
					} else if (args[1].equalsIgnoreCase("share")) {
						if (!(hasPermission(sender, permission_sell))) {
							sender.sendMessage(PermissionDenied(permission_sell));
							return true;
						}

						if (!(sender instanceof Player)) {
							sender.sendMessage(error_only_players);
							return true;
						}

						String share_name;
						long amount;
						if (args.length == 2) {
							sender.sendMessage(error_not_enough_arguments);
							return true;
						} else if (args.length == 3) {
							share_name = args[2];
							amount = 1;
						} else {
							share_name = args[2];
							amount = Integer.parseInt(args[3]);
						}
						Player player = (Player) sender;

						JSONObject share = (JSONObject) StockMarketPlugin.getShares().get(share_name);
						JSONObject user_share = (JSONObject) ((JSONObject) ((JSONObject) (JSONObject) StockMarketPlugin
								.getUser(player.getName()).get("market")).get("shares")).get(share_name);
						double price = (double) share.get("price");

						if (user_share == null) {
							sender.sendMessage(error_invalid_share);
							return true;
						}
						long max_amount = (long) user_share.get("amount");

						if (amount > max_amount) {
							sender.sendMessage(error_not_enough_shares);
							return true;
						}
						user_share.put("amount", max_amount - amount);
						StockMarketPlugin.economy.depositPlayer(player.getName(), amount * price);
						sender.sendMessage(
								prefix + "Sold " + amount + " shares of " + share_name + " for " + price + " each!");
					} else {
						sender.sendMessage(error_invalid_good);
					}
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (!hasPermission(sender, permission_reload)) {
						sender.sendMessage(PermissionDenied(permission_reload));
						return true;
					}
					StockMarketPlugin.parseConfig();
					plugin.stockMarketThread.state = StockMarketThread.STOP;
					plugin.stockMarketThread = new StockMarketThread(plugin, new Random());
					new Thread(plugin.stockMarketThread);

					sender.sendMessage(prefix + "Reloaded Stock Market!");
				} else if (args[0].equalsIgnoreCase("version")) {
					if (!hasPermission(sender, permission_version)) {
						sender.sendMessage(PermissionDenied(permission_version));
						return false;
					}
					sender.sendMessage("Running Stock Market v" + StockMarketPlugin.version + " (build "
							+ StockMarketPlugin.build + ") developed by CrashGamesCrMc (aka Beyblade007SF).");
				} else if (args[0].equalsIgnoreCase("forcemovements")) {
					if (!hasPermission(sender, permission_forcemovements)) {
						sender.sendMessage(PermissionDenied(permission_forcemovements));
						return true;
					}
					JSONObject shares = StockMarketPlugin.getShares();
					for (int i = 0; i < shares.size(); i++) {
						((JSONObject) shares.values().toArray()[i]).put("movement_end", 0L);
						sender.sendMessage(prefix + "§4Reset movement for §e" + shares.keySet().toArray()[i] + "§f!");
					}
					sender.sendMessage(prefix + "Reset movement of all shares!");
				} else if (args[0].equalsIgnoreCase("thread")) {
					if (args[1].equalsIgnoreCase("stop")) {
						if (!hasPermission(sender, permission_thread_stop)) {
							sender.sendMessage(PermissionDenied(permission_thread_stop));
							return true;
						}
						plugin.stockMarketThread.state = StockMarketThread.STOP;
						sender.sendMessage(prefix + "§4Stop signal has been sent!");
					} else if (args[1].equalsIgnoreCase("start")) {
						if (!hasPermission(sender, permission_thread_start)) {
							sender.sendMessage(PermissionDenied(permission_thread_start));
							return true;
						}
						plugin.stockMarketThread = new StockMarketThread(plugin, new Random());
						new Thread(plugin.stockMarketThread).start();
						sender.sendMessage(prefix + "§4Started a new Stock Market thread!");
					} else if (args[1].equalsIgnoreCase("restart")) {
						if (!hasPermission(sender, permission_thread_restart)) {
							sender.sendMessage(PermissionDenied(permission_thread_restart));
							return true;
						}
						plugin.stockMarketThread.state = StockMarketThread.STOP;
						plugin.stockMarketThread = new StockMarketThread(plugin, new Random());
						new Thread(plugin.stockMarketThread).start();
						sender.sendMessage(prefix + "§4Stop signal sent and new thread started!");
					}
				} else {
					sender.sendMessage(error_subcommand_not_found);
				}
			} else {
				// stock market help
				if (!(hasPermission(sender, permission_help))) {
					sender.sendMessage(PermissionDenied(permission_help));
					return true;
				}
				sender.sendMessage(prefix + "Stock Market commands:");
				if (hasPermission(sender, permission_config)) {
					sender.sendMessage(prefix + "/sm config -> returns the current config");
				}
				if (hasPermission(sender, permission_list)) {
					sender.sendMessage(prefix + "/sm list -> returns the shares and currencies on the market");
				}
				if (hasPermission(sender, permission_add)) {
					sender.sendMessage(prefix + "/sm add -> shows help for subcommands");
				}
				if (hasPermission(sender, permission_buy)) {
					sender.sendMessage(prefix + "/sm buy -> shows help for subcommands");
				}
				if (hasPermission(sender, permission_show_self)) {
					sender.sendMessage(prefix + "/sm show -> shows your deposit");
				}
				if (hasPermission(sender, permission_show_others)) {
					sender.sendMessage(prefix + "/sm show <username> -> shows someone's deposit");
				}
				if (hasPermission(sender, permission_sell)) {
					sender.sendMessage(prefix + "/sm sell -> shows help for subcommands");
				}
				if (hasPermission(sender, permission_reload)) {
					sender.sendMessage(prefix + "/sm reload -> reloads the plugins config file");
				}
				if (hasPermission(sender, permission_version)) {
					sender.sendMessage(prefix + "/sm version -> shows the plugin's version");
				}
			}
			return true;
		}
		return false;
	}

}
