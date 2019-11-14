package io.github.crashgamescrmc.StockMarket.data.types;

import java.util.UUID;

import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBCompareable;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

public class DBUUID implements DBvalue {

	public DBUUID(UUID value) {
		this.value = value;
	}

	private UUID value;

	@Override
	public void Parse(String text) {
		value = UUID.fromString(text);
	}

	@Override
	public String Save() {
		return value.toString();
	}

	@Override
	public boolean Equals(DBCompareable value2) {
		if (value2 instanceof DBUUID) {
			return ((DBUUID) value2).value.equals(value);
		} else if (value2 instanceof DBString) {
			return ((DBString) value2).getValue().equals(value.toString());
		} else {
			return false;
		}
	}

	@Override
	public String Display() {
		return value.toString();
	}

	public UUID getValue() {
		return value;
	}

	public void setValue(UUID value) {
		this.value = value;
	}

}
