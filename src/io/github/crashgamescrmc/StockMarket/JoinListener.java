package io.github.crashgamescrmc.StockMarket;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (StockMarketPlugin.getUser(event.getPlayer().getName()) == null) {
			StockMarketPlugin.addUser(event.getPlayer().getName());
		}
	}

}
