package view;

import javafx.scene.control.Tab;

public class InventoryTab extends Tab {

	private StockPane stockpane;
	
	/**
	 * tab which contains stockpane, the inventory management side of the program
	 */
	public InventoryTab() {
		
		this.setId("inventorytab");
		this.setText("Inventory");
		
		stockpane = new StockPane();
		this.setContent(stockpane);
		
		this.setStyle("-fx-font: 20px sans-serif, normal, bold;");
	}

	/**
	 * @return returns the stockpane object
	 */
	public StockPane getStockPane() {
		return stockpane;
	}
	
}

