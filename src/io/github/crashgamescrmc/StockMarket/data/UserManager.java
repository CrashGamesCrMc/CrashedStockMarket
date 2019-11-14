package io.github.crashgamescrmc.StockMarket.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.ColumnOrigin;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DataBaseQuery;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DefaultDataBaseQuery;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryJoinCondition;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryResult;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.SearchedValue;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.crashgamescrmc.StockMarket.market.SMPlayer;
import io.github.crashgamescrmc.StockMarket.market.ShareStack;
import io.github.crashgamescrmc.StockMarket.market.orders.Order;

public class UserManager {

	private DataBaseHandler dbh;
	private DataBaseQuery query;

	public UserManager(DataBaseHandler dbh) {
		this.dbh = dbh;
		query = new DefaultDataBaseQuery(dbh);
	}

	public void addUser(SMPlayer user) {
		query.Insert("CrashedStockMarket", "users",
				new SearchedValue[] { new SearchedValue("ID", new DBString(user.getPlayer().getUniqueId().toString())),
						new SearchedValue("name", new DBString(user.getPlayer().getName())) });
	}

	public SMPlayer getUser(OfflinePlayer player) {
		// currently not really needed, but maybe later

		QueryResult user_info = query.Run("CrashedStockMarket", "users", new String[] { "ID", "name" },
				new SearchedValue[] { new SearchedValue("ID", new DBString(player.getUniqueId().toString())) });

		List<Order> orders = new ArrayList<Order>();
		Map<String, ShareStack> shares = new HashMap<String, ShareStack>();

		Table result_shares = query.InnerJoin("CrashedStockMarket", "users", "userShares",
				new String[] { "share", "amount" }, new ColumnOrigin[] {}, new QueryJoinCondition[] {});

		SMPlayer out = new SMPlayer(player, shares, orders);
	}

}
