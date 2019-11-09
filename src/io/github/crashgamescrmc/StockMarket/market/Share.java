package io.github.crashgamescrmc.StockMarket.market;

public class Share {

	private double price;
	private Company company;

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

}
