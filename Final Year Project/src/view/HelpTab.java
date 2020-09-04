package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HelpTab extends Tab{

	/**
	 * tab class containing the help/instructions information
	 */
	public HelpTab() {
		this.setId("helptab");
		this.setText("User Guide");
		
		VBox content = new VBox();
		this.setContent(content);
		
		TextArea userguide = new TextArea();
		content.getChildren().add(userguide);
		
		userguide.setText("Instructions\n" + 
						  "----------------\n" +
				"Upon first start up, you should have been asked to enter the details of your business. If you need to adjust these at any point, it can be done via file -> Your Details.\n\n\n" +
				"Inventory Tab\n" + 
				"-------------------\n" +
				"To insert a new item, enter its details in the bottom right section of the tab. Once you have entered an item, it can be viewed in the Inventory List in the top left.\n" +
				"Clicking an item will load that items description into the description box in the top right.\n\n" +
				"You can adjust the quantity of an item you have in stock at any time. Adjust the value in the bottom right by typing or using the + & - buttons,\n" + 
				"then press add or remove to increase or decrease the selected items quantity by that amount.\n\n" +
				"If you ever need to delete an item, select it and then press the Delete Item button below the description box.\n\n\n" + 
				"Invoice Tab\n" + 
				"----------------\n" + 
				"To add an item to the customers basket, select the item and the amount, then use down arrow to move that quantity into the basket.\n" + 
				"You can also remove items from the customers basket the same way, by selecting the item you want to remove and pressing the up arrow.\n\n" +
				"Once the items in the basket is correct, either select a customer drop the dropdown menu, or add a new customer by entering their details into the top right,\nand selecting Add Customer.\n\n" + 
				"If the basket and the customers details are all correct, press Generate Invoice to create an invoice for the customer of the items they have purchased.\n" +
				"The invoice will be generated into an Invoices folder in the same location that this software is stored.\n\n\n\n\n" + 
				"----------------------------------------------------------------------------------------------------------------------------------------------------\n\n" + 
				"Created by Daniel Haycox.     Database Management provided by SQLite.     PDF Generation provided by iText.\n\n" + 
				"----------------------------------------------------------------------------------------------------------------------------------------------------");
	
		
		VBox.setVgrow(userguide, Priority.ALWAYS);
		
		
		this.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		userguide.setStyle("-fx-font: 15px sans-serif, normal, bold;");
		
	}
	
}
