package view;

import javafx.scene.control.Tab;

public class InvoiceTab extends Tab {
	
	private InvoicePane invoicepane;

	/**
	 * the tab that contains the invoicing section of the application
	 */
	public InvoiceTab() {
		
		this.setId("invoicetab");
		this.setText("Invoice Generator");
		
		invoicepane = new InvoicePane();
		this.setContent(invoicepane);
		
		this.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		
	}
	
	/**
	 * returns the invoicepane object
	 * @return
	 */
	public InvoicePane getInvoicePane() {
		return invoicepane;
	}
	
}
