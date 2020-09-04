package model;

public class StockItem implements Comparable<StockItem>{

	private String name;
	private String desc;
	private int quantity;
	private double price;
	
	public StockItem() {
		name = "";
		desc = "";
		quantity = 0;
	}
	
	/**
	 * full stockitem constructor
	 * @param name name of the stock item
	 * @param desc description for the stock item
	 * @param quantity quantity of stock in inventory
	 * @param price price of the stock item
	 */
	public StockItem(String name, String desc, int quantity, double price) {
		this.name = name;
		this.desc = desc;
		this.quantity = quantity;
		this.price = price;
	}
	
	/**
	 * sets the name variable
	 * @param name name string to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * sets the description variable
	 * @param desc description string to be set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * sets the quantity variable
	 * @param quantity integer to be set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * sets the price variable
	 * @param price double to be set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return returns the name variable
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return returns the desc variable
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * @return returns the quantity variable
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * @return returns the price variable
	 */
	public double getPrice() {
		return price;
	}
	
	@Override
	public int compareTo(StockItem other) {
		return this.getName().compareTo(other.getName());
	}
	
	@Override
	public String toString() {
		String s = String.format("%-27s %6d %9.2f", name, quantity, price);
		return (s);
	}
	
	/**
	 * @return returns the traditional toString format
	 */
	public String actualtoString() {
		return ("[Name=" + name + " Desc=" + desc + "Quantity=" + quantity + "Price=" + price + "]");
	}
	
}
