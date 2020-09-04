package view;

import javafx.scene.layout.*;

public class StockManagerRootPane extends VBox {

	private TopMenu menu;
	private Tabs tabs;
	
	/**
	 * the root pane for the user interface, containing all other elements
	 */
	public StockManagerRootPane() {
		menu = new TopMenu();
		tabs = new Tabs();
		
		VBox.setVgrow(tabs, Priority.ALWAYS);
		this.getChildren().addAll(menu, tabs);
	}
	
	/**
	 * @return returns the tabs class
	 */
	public Tabs getTabs() {
		return tabs;
	}
	
	/**
	 * @return returns the menu class
	 */
	public TopMenu getMenu() {
		return menu;
	}
	
}
