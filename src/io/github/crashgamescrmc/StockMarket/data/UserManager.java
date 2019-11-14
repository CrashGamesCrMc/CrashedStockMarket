package io.github.crashgamescrmc.StockMarket.data;

import java.util.ArrayList;
import java.util.List;

import io.github.SebastianDanielFrenz.SimpleDBMT.Table;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;
import io.github.crashgamescrmc.StockMarket.data.types.DBUUID;
import io.github.crashgamescrmc.StockMarket.market.SMPlayer;
import io.github.crashgamescrmc.StockMarket.market.SMUser;

public class UserManager extends Table {

	public UserManager() {
		super(new UserManagerValueManager());

	}

	private List<SMUser> users = new ArrayList<SMUser>();

	public void convertUserData() {
		setValues(new ArrayList<ArrayList<DBvalue>>());

		int row_size = 0;
		ArrayList<DBvalue> row;
		for (SMUser user : users) {
			row = new ArrayList<DBvalue>(row_size);
			if (user instanceof SMPlayer) {
				row.add(new DBUUID(((SMPlayer) user).getPlayer().getUniqueId()));
			} else {
				row.add(new DBString("SYS_" + user.getName()));
			}
		}
	}

}
