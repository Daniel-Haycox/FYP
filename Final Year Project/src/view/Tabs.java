package view;

import javafx.scene.control.TabPane;

public class Tabs extends TabPane{

	private InventoryTab inventory;
	private InvoiceTab invoice;
	private HelpTab help;
	/**
	 * the tabpane class containing both tabs
	 */
	public Tabs () {
		
		this.setId("tabpane");
		
		inventory = new InventoryTab();
		inventory.setId("inventorytab");
		invoice = new InvoiceTab();
		invoice.setId("invoicetab");
		
		inventory.setClosable(false);
		invoice.setClosable(false);
		
		this.getTabs().addAll(inventory, invoice);
		
		setStyle();
	}
	
	
	/**
	 * @return returns the inventory tab
	 */
	public InventoryTab getInventoryTab() {
		return inventory;
	}
	
	/**
	 * @return returns the invoice tab
	 */
	public InvoiceTab getInvoiceTab() {
		return invoice;
	}
	
	/**
	 * creates the help/instructions tab
	 */
	public void createHelpTab() {
		help = new HelpTab();
		help.setClosable(true);
		this.getTabs().add(help);
	}
	
	/**
	 * sets style for elements in the tab pane
	 */
	public void setStyle() {
	}
	
}
