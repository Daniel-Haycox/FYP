package controller;

import java.util.ArrayList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import model.Customer;
import model.Owner;
import model.StockItem;
import view.StockManagerRootPane;

public class StockManagerController {
	
	private StockManagerRootPane view;
	private SQLHandler db;
	private PDFHandler pdf;
	private Stage stage;
	
	/**
	 * empty constructor
	 */
	public StockManagerController() {
		this.view = null;
		this.db = null;
		this.pdf = null;
		this.stage = null;
	}
	
	/**
	 * full constructor
	 * @param view the view pane passed for MVC interaction
	 */
	public StockManagerController(StockManagerRootPane view) {
		this.view = view;
		this.db = new SQLHandler("InvoiceDB");
		this.pdf = new PDFHandler();

		db.createTable("stock");
		db.createTable("invoice");
		db.createCustomersTable();
		db.createOwnerTable();
		db.createCounterTable();
		db.startInvoiceCounter();
		
		this.attachEventHandlers();
		
		stockUpdater();
		view.getTabs().getInvoiceTab().getInvoicePane().populateCustomers(populateCustomers());
		view.getTabs().getInvoiceTab().getInvoicePane().totalCounter();
		
	}
	
	/**
	 * attaches all event handlers to components in the view
	 */
	private void attachEventHandlers() {
		view.getTabs().getInventoryTab().getStockPane().addSubmitHandler(new SubmitHandler());
		view.getTabs().getInventoryTab().getStockPane().addDeleteHandler(new DeleteHandler());
		view.getTabs().getInventoryTab().getStockPane().addAddHandler(new AddHandler());
		view.getTabs().getInventoryTab().getStockPane().addRemoveHandler(new RemoveHandler());
		view.getTabs().getInvoiceTab().getInvoicePane().addUpHandler(new InvoiceUpHandler());
		view.getTabs().getInvoiceTab().getInvoicePane().addDownHandler(new InvoiceDownHandler());
		view.getTabs().getInvoiceTab().getInvoicePane().addAddCustomerHandler(new AddCustomerHandler());
		view.getTabs().getInvoiceTab().getInvoicePane().addDeleteCustomerHandler(new DeleteCustomerHandler());
		view.getTabs().getInvoiceTab().getInvoicePane().addGenerateInvoiceHandler(new GenerateInvoiceHandler());
		view.getMenu().addDetailsHandler(new DetailsHandler());
		view.getMenu().addInstructionsHandler(new InstructionsHandler());
	}
	
	/**
	 * recieves the stage object so it can be used for the exithandler
	 * @param s the programs stage object
	 */
	public void setStage(Stage s) {
		this.stage = s;
		view.getMenu().addExitHandler(new ExitHandler());
	}
	
	/**
	 * checks if an owner exists, if not activates the details alert
	 */
	public void ownerCheck() {
		ArrayList<Map<String,Object>> tempres = db.query("owner");
		if (tempres.size() < 1)
			view.getMenu().activateDetails();
		else if(tempres.get(0).get("name").toString().length() < 1 || 
		   tempres.get(0).get("street").toString().length() < 1 || 
		   tempres.get(0).get("city").toString().length() < 1 || 
		   tempres.get(0).get("postcode").toString().length() < 1 || 
		   tempres.get(0).get("phone").toString().length() < 1 || 
		   tempres.get(0).get("days").toString().length() < 1){
			view.getMenu().activateDetails();
		}
	}
	
	/**
	 * populates the customers combobox with existing customers
	 * @return returns an array of customer objects from db query to populate the combobox with
	 */
	public Customer[] populateCustomers() {
		ArrayList<Map<String,Object>> results = db.query("customers");
		Customer[] customers = new Customer[results.size()];
		for (int i = 0; i < results.size(); i++) {
			Customer c = new Customer((String) results.get(i).get("name"), (String) results.get(i).get("street"), (String) results.get(i).get("city"), (String) results.get(i).get("postcode"));
			customers[i] = c;
		}
		return customers;
	}
	
	/**
	 * populates the stock listview
	 * @param table the correct table to query in the db
	 * @return returns a list of stockitem objects from db query to populate the stocklist
	 */
	public StockItem[] populateStock(String table) {
		ArrayList<Map<String,Object>> results = db.query(table);
		StockItem[] stockitems = new StockItem[results.size()];
		for(int i = 0; i < results.size(); i++) {
			StockItem item = new StockItem((String) results.get(i).get("name"), (String) results.get(i).get("description"), (int) results.get(i).get("quantity"), (double) results.get(i).get("price"));		
			stockitems[i] = item;
		}
		return stockitems;
	}
	
	/**
	 * updates the inventory and invoicing stocklists
	 */
	public void stockUpdater() {
		view.getTabs().getInventoryTab().getStockPane().populateStock(populateStock("stock"));
		view.getTabs().getInvoiceTab().getInvoicePane().populateStock(populateStock("stock"));
		view.getTabs().getInvoiceTab().getInvoicePane().populateInvoice(populateStock("invoice"));
	}
	
	/**
	 * eventhandler for the add button
	 * @author Daniel Haycox
	 *
	 */
	private class AddHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				StockItem temp = view.getTabs().getInventoryTab().getStockPane().getSelectedItem();
				temp.setQuantity(temp.getQuantity() + view.getTabs().getInventoryTab().getStockPane().getCounterValue());
				db.updateProduct("stock", temp.getName(), temp.getQuantity());
				stockUpdater();
				view.getTabs().getInventoryTab().getStockPane().setSelectedItem(temp);
				
			} catch (Exception z) {
				System.out.println(z.getMessage());
			}
		}
	}
	
	/**
	 * eventhandler for the remove button
	 * @author Daniel Haycox
	 *
	 */
	private class RemoveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				StockItem temp = view.getTabs().getInventoryTab().getStockPane().getSelectedItem();
				if (temp.getQuantity() >= view.getTabs().getInventoryTab().getStockPane().getCounterValue()) {
					temp.setQuantity(temp.getQuantity() - view.getTabs().getInventoryTab().getStockPane().getCounterValue());
					db.updateProduct("stock", temp.getName(), temp.getQuantity());
					stockUpdater();
					view.getTabs().getInventoryTab().getStockPane().setSelectedItem(temp);
					
				}
			} catch (Exception z) {
				System.out.println(z.getMessage());
			}
		}
	}
	
	/**
	 * eventhandler for the delete button
	 * @author Daniel Haycox
	 *
	 */
	private class DeleteHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				db.deleteProduct("stock", view.getTabs().getInventoryTab().getStockPane().getSelectedItem().getName());
				stockUpdater();
			} catch (Exception z) {
				System.out.println(z.getMessage());
			}
		}
	}
	
	/**
	 * eventhandler for the submit button
	 * @author Daniel Haycox
	 *
	 */
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			StockItem i = new StockItem();
			if (view.getTabs().getInventoryTab().getStockPane().validator()) {
				view.getTabs().getInventoryTab().getStockPane().submitItem(i);
				db.insertProduct("stock", i.getName(), i.getDesc(), i.getQuantity(), i.getPrice());
				stockUpdater();
			}
		}
	}
	
	//INVOICE EVENT HANDLING
	
	/**
	 * eventhandler to move items up from invoice to inventory
	 * @author Daniel Haycox
	 *
	 */
	private class InvoiceUpHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				if (view.getTabs().getInvoiceTab().getInvoicePane().focusChecker() == 1) {
					StockItem temp = view.getTabs().getInvoiceTab().getInvoicePane().getSelectedItem();
					for (StockItem s : view.getTabs().getInvoiceTab().getInvoicePane().getInvoiceList()) {
						if (s.getName().equals(temp.getName())) {
							if (s.getQuantity() <= view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock", s.getName(), temp.getQuantity() + s.getQuantity());
								db.deleteProduct("invoice", s.getName());
							} else if (s.getQuantity() > view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock",  s.getName(), temp.getQuantity() + view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
								db.updateProduct("invoice", s.getName(), s.getQuantity() - view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
							}
						}
					}
					
				} else if (view.getTabs().getInvoiceTab().getInvoicePane().focusChecker() == -1) {
					StockItem temp = view.getTabs().getInvoiceTab().getInvoicePane().getSelectedInvoiceItem();
					for (StockItem s : view.getTabs().getInvoiceTab().getInvoicePane().getStockList()) {
						if (s.getName().equals(temp.getName())) {
							if (temp.getQuantity() <= view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock", s.getName(), s.getQuantity() + temp.getQuantity());
								db.deleteProduct("invoice", s.getName());
							} else if (temp.getQuantity() > view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock",  s.getName(), s.getQuantity() + view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
								db.updateProduct("invoice", s.getName(), temp.getQuantity() - view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
							}
						}
					}
				}
				stockUpdater();
				view.getTabs().getInvoiceTab().getInvoicePane().totalCounter();
			} catch (Exception z) {
				System.out.println(z.getMessage());
			}
		}
	}
	
	/**
	 * eventhandler to move things down from inventory to invoice
	 * @author Daniel Haycox
	 *
	 */
	private class InvoiceDownHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				if (view.getTabs().getInvoiceTab().getInvoicePane().focusChecker() == 1) {
					StockItem temp = view.getTabs().getInvoiceTab().getInvoicePane().getSelectedItem();
					boolean completed = false;
					for (StockItem s : view.getTabs().getInvoiceTab().getInvoicePane().getInvoiceList()) {
						if (s.getName().equals(temp.getName())) {
							if (temp.getQuantity() <= view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock", s.getName(), 0);
								db.updateProduct("invoice", s.getName(), s.getQuantity() + temp.getQuantity());
								completed = true;
							} else if (temp.getQuantity() > view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock",  s.getName(), temp.getQuantity() - view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
								db.updateProduct("invoice", s.getName(), s.getQuantity() + view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
								completed = true;
							}
						}
					}
					if (!completed) {
						if (temp.getQuantity() <= view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
							db.updateProduct("stock", temp.getName(), 0);
							db.insertProduct("invoice", temp.getName(), temp.getDesc(), temp.getQuantity(), temp.getPrice());
						} else if (temp.getQuantity() > view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
							db.updateProduct("stock",  temp.getName(), temp.getQuantity() - view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
							db.insertProduct("invoice", temp.getName(), temp.getDesc(), view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue(), temp.getPrice());
						}
					}
					
				} else if (view.getTabs().getInvoiceTab().getInvoicePane().focusChecker() == -1) {
					StockItem temp = view.getTabs().getInvoiceTab().getInvoicePane().getSelectedInvoiceItem();
					for (StockItem s : view.getTabs().getInvoiceTab().getInvoicePane().getStockList()) {
						if (s.getName().equals(temp.getName())) {
							if (s.getQuantity() <= view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock", s.getName(), 0);
								db.updateProduct("invoice", s.getName(), temp.getQuantity() + s.getQuantity());
							} else if (s.getQuantity() > view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue()) {
								db.updateProduct("stock",  s.getName(), s.getQuantity() - view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
								db.updateProduct("invoice", s.getName(), temp.getQuantity() + view.getTabs().getInvoiceTab().getInvoicePane().getCounterValue());
							}
						}
					}
				}
				stockUpdater();
				view.getTabs().getInvoiceTab().getInvoicePane().totalCounter();
			} catch (Exception z) {
				System.out.println(z.getMessage());
			}
		}
	}
	
	/**
	 * eventhandler to add new customers to the database
	 * @author Daniel Haycox
	 *
	 */
	private class AddCustomerHandler implements EventHandler<ActionEvent> {
		public void handle (ActionEvent e) {
			Customer temp = new Customer();
			view.getTabs().getInvoiceTab().getInvoicePane().retrieveCustomer(temp);
			db.insertCustomer(temp.getName(), temp.getStreet(), temp.getCity(), temp.getPostcode());
			view.getTabs().getInvoiceTab().getInvoicePane().populateCustomers(populateCustomers());
		}
	}
	
	/**
	 * eventhandler to delete customers from the database
	 * @author Daniel Haycox
	 *
	 */
	private class DeleteCustomerHandler implements EventHandler<ActionEvent> {
		public void handle (ActionEvent e) {
			Customer temp = new Customer();
			view.getTabs().getInvoiceTab().getInvoicePane().retrieveCustomer(temp);
			db.deleteProduct("customers", temp.getName());
			view.getTabs().getInvoiceTab().getInvoicePane().populateCustomers(populateCustomers());
			view.getTabs().getInvoiceTab().getInvoicePane().clearCustomer();
		}
	}
	
	/**
	 * eventhandler to generate the pdf invoice
	 * @author Daniel Haycox
	 *
	 */
	private class GenerateInvoiceHandler implements EventHandler<ActionEvent> {
		public void handle (ActionEvent e) {
			ArrayList<Map<String,Object>> results = db.query("owner");
			Owner owner;
			if (results.size() > 0)
				owner = new Owner((String) results.get(0).get("name"), (String) results.get(0).get("street"), (String) results.get(0).get("city"), (String) results.get(0).get("postcode"), (String) results.get(0).get("phone"), (String) results.get(0).get("days"));
			else
				owner = new Owner();
			
			ArrayList<Map<String,Object>> results2 = db.query("counter");
			int invoicecount = (Integer) results2.get(0).get("count");
			
			Customer temp = new Customer();
			view.getTabs().getInvoiceTab().getInvoicePane().retrieveCustomer(temp);

			if (temp.getName().length() <= 1) {
				Alert a = new Alert(AlertType.WARNING);
				a.setHeaderText("No Customer Selected.");
				a.show();
			} else {
				try {
					pdf.generate(owner, temp, view.getTabs().getInvoiceTab().getInvoicePane().getInvoiceList(), invoicecount);
					for (StockItem i : view.getTabs().getInvoiceTab().getInvoicePane().getInvoiceList()) {
						db.deleteProduct("invoice", i.getName());
					};
					stockUpdater();
					view.getTabs().getInvoiceTab().getInvoicePane().totalCounter();
					db.updateInvoice(invoicecount + 1);
				} catch (Exception x) {}
			}
		}
	}
	
	/**
	 * eventhandler to gather the owner details
	 * @author Daniel Haycox
	 *
	 */
	private class DetailsHandler implements EventHandler<ActionEvent> {
		public void handle (ActionEvent e) {
			ArrayList<Map<String,Object>> results = db.query("owner");
			Owner owner;
			if (results.size() > 0)
				owner = new Owner((String) results.get(0).get("name"), (String) results.get(0).get("street"), (String) results.get(0).get("city"), (String) results.get(0).get("postcode"), (String) results.get(0).get("phone"), (String) results.get(0).get("days"));
			else
				owner = new Owner();
			Alert a = new Alert(AlertType.INFORMATION);
			a.setTitle("Your Information");
			a.setHeaderText("Please update your information.");

			TextArea ownername = new TextArea();
			ownername.setPromptText("Company Name");
			ownername.setWrapText(true);
			TextArea ownerstreet = new TextArea();
			ownerstreet.setPromptText("House Number and Street Name");
			ownerstreet.setWrapText(true);
			TextArea ownercity = new TextArea();
			ownercity.setPromptText("Town or City");
			ownercity.setWrapText(true);
			TextArea ownerpostcode = new TextArea();
			ownerpostcode.setPromptText("Postcode");
			ownerpostcode.setWrapText(true);
			TextArea ownerphone = new TextArea();
			ownerphone.setPromptText("Phone Number");
			ownerphone.setWrapText(true);
			TextArea invoicedays = new TextArea();
			invoicedays.setPromptText("How many days customers have to pay for invoices");
			invoicedays.setWrapText(true);
			VBox ownerdetails = new VBox();
			ownerdetails.getChildren().addAll(ownername, ownerstreet, ownercity, ownerpostcode, ownerphone, invoicedays);
			
			if (owner.getName().length() > 1 && owner.getStreet().length() > 1 && owner.getCity().length() > 1 && owner.getPostcode().length() > 1) {
				ownername.setText(owner.getName());
				ownerstreet.setText(owner.getStreet());
				ownercity.setText(owner.getCity());
				ownerpostcode.setText(owner.getPostcode());
				ownerphone.setText(owner.getPhone());
				invoicedays.setText(owner.getDays());
			}
			
			ownername.setMaxSize(530, 38);
			ownername.setMinSize(530, 38);
			ownername.setStyle("-fx-font: 19px sans-serif, normal, bold;");
			ownerstreet.setMaxSize(530, 38);
			ownerstreet.setMinSize(530, 38);
			ownerstreet.setStyle("-fx-font: 19px sans-serif, normal, bold;");
			ownercity.setMaxSize(530, 38);
			ownercity.setMinSize(530, 38);
			ownercity.setStyle("-fx-font: 19px sans-serif, normal, bold;");
			ownerpostcode.setMaxSize(530, 38);
			ownerpostcode.setMinSize(530, 38);
			ownerpostcode.setStyle("-fx-font: 19px sans-serif, normal, bold;");
			ownerphone.setMaxSize(530, 38);
			ownerphone.setMinSize(530, 38);
			ownerphone.setStyle("-fx-font: 19px sans-serif, normal, bold;");
			invoicedays.setMaxSize(530, 38);
			invoicedays.setMinSize(530, 38);
			invoicedays.setStyle("-fx-font: 19px sans-serif, normal, bold;");
			ownerdetails.setSpacing(10);
			
			a.getDialogPane().setContent(ownerdetails);
			
			a.showAndWait();
			
			owner.setName(ownername.getText());
			owner.setStreet(ownerstreet.getText());
			owner.setCity(ownercity.getText());
			owner.setPostcode(ownerpostcode.getText());
			owner.setPhone(ownerphone.getText());
			owner.setDays(invoicedays.getText());
			
			db.clearOwner();
			db.insertOwner(owner.getName(), owner.getStreet(), owner.getCity(), owner.getPostcode(), owner.getPhone(), owner.getDays());
			
			System.out.println(db.query("owner"));
			
			
		}
	}
	
	/**
	 * eventhandler the exit the program from the filemenu or hotkey
	 * @author Daniel Haycox
	 *
	 */
	private class ExitHandler implements EventHandler<ActionEvent> {
		public void handle (ActionEvent e) {
			stage.close();
		}
	}
	
	/**
	 * eventhandler to open the instructions tab
	 * @author Daniel Haycox
	 *
	 */
	private class InstructionsHandler implements EventHandler<ActionEvent> {
		public void handle (ActionEvent e) {
			view.getTabs().createHelpTab();
		}
	}
	
}
