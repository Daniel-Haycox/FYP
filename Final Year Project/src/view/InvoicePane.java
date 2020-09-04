package view;

import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Customer;
import model.StockItem;

public class InvoicePane extends VBox{

	private HBox leftright;
	private VBox left;
	private Text columnheaders;
	private HBox buttons;
	private VBox valign;
	private HBox halign;
	private Button plus;
	private VBox counterbox;
	private TextArea counter;
	private int countervalue;
	private Button minus;
	private HBox updown;
	private Button up;
	private Image upimage;
	private Button down;
	private Image downimage;
	private ObservableList<StockItem> stocklist;
	private ListView<StockItem> stock;
	private Text columnheaders2;
	private ObservableList<StockItem> invoicestocklist;
	private ListView<StockItem>invoicestock;	
	private int focusval;
	
	private VBox right;
	private VBox righttop;
	private VBox rightbottom;
	private Text customerheader;
	private ObservableList<Customer> customerlist;
	private ComboBox<Customer> customers;
	private TextArea customername;
	private TextArea customerstreet;
	private TextArea customercity;
	private HBox postcodeadddelete;
	private TextArea customerpostcode;
	private VBox adddelete;
	private Button addcustomer;
	private Button deletecustomer;
	private HBox pricealign;
	private VBox pricegenerateinvoice;
	private Text totalheader;
	private TextArea totalprice;
	private Button generateinvoice;
	
	/**
	 * the pane for the invoicing side of the program
	 */
	public InvoicePane() {
		
		this.setId("invoicepane");
		
		leftright = new HBox();
		leftright.setId("leftright");
		this.getChildren().add(leftright);
		
		left = new VBox();
		leftright.getChildren().add(left);
		
		columnheaders = new Text(" Name                                                    Quantity       Price");
		left.getChildren().add(columnheaders);
		
		stocklist = FXCollections.observableArrayList();
		stock = new ListView<StockItem>();
		stock.setId("stockview");
		
		left.getChildren().add(stock);
		
		buttons = new HBox();
		buttons.setId("buttonbox");
		left.getChildren().add(buttons);
		
		valign = new VBox();
		buttons.getChildren().add(valign);
		halign = new HBox();
		valign.getChildren().add(halign);
		plus = new Button("+");
		counterbox = new VBox();
		counter = new TextArea();
		counter.setId("counter");
		counter.setWrapText(true);
		counterbox.getChildren().add(counter);
		countervalue = 1;
		counter.setText(countervalue + "");
		minus = new Button("-");
		
		halign.getChildren().addAll(plus, counterbox, minus);
		
		updown = new HBox();
		
		up = new Button("");
		upimage = new Image(getClass().getResourceAsStream("../up.png"));
		up.setGraphic(new ImageView(upimage));
		down = new Button("");
		downimage = new Image(getClass().getResourceAsStream("../down.png"));
		down.setGraphic(new ImageView(downimage));
		updown.getChildren().addAll(up, down);
		updown.setId("addremove");
		buttons.getChildren().add(updown);
		
		columnheaders2 = new Text(" Name                                                    Quantity       Price");
		left.getChildren().add(columnheaders2);
		
		invoicestocklist = FXCollections.observableArrayList();
		invoicestock = new ListView<StockItem>();
		left.getChildren().add(invoicestock);
			
		right = new VBox();
		right.setId("right");
		righttop = new VBox();
		rightbottom = new VBox();
		leftright.getChildren().add(right);
		right.getChildren().addAll(righttop, rightbottom);
		
		customerheader = new Text("Customer Details");
		customerlist = FXCollections.observableArrayList();
		customers = new ComboBox<Customer>();
		customername = new TextArea();
		customername.setPromptText("Full Name");
		customername.setWrapText(true);
		customerstreet = new TextArea();
		customerstreet.setPromptText("House Number and Street Name");
		customerstreet.setWrapText(true);
		customercity = new TextArea();
		customercity.setPromptText("Town or City");
		customercity.setWrapText(true);
		
		righttop.getChildren().addAll(customerheader, customers, customername, customerstreet, customercity);
		
		postcodeadddelete = new HBox();
		customerpostcode = new TextArea();
		customerpostcode.setPromptText("Postcode");
		customerpostcode.setWrapText(true);
		adddelete = new VBox();
		adddelete.setId("adddelete");
		addcustomer = new Button("Add Customer");
		deletecustomer = new Button("Delete Customer");
		adddelete.getChildren().addAll(addcustomer, deletecustomer);
		postcodeadddelete.getChildren().addAll(customerpostcode, adddelete);		
		righttop.getChildren().add(postcodeadddelete);
		
		pricealign = new HBox();
		pricegenerateinvoice = new VBox();
		totalheader = new Text("Total");
		totalprice = new TextArea();
		totalprice.setId("totalprice");
		totalprice.setMouseTransparent(true);
		totalprice.setFocusTraversable(false);
		totalprice.setWrapText(true);
		generateinvoice = new Button("Generate Invoice");
		pricegenerateinvoice.getChildren().addAll(totalheader, totalprice, generateinvoice);
		pricealign.getChildren().add(pricegenerateinvoice);
		rightbottom.getChildren().add(pricealign);
		
		
		VBox.setVgrow(stock, Priority.SOMETIMES);
		VBox.setVgrow(invoicestock, Priority.SOMETIMES);
		HBox.setHgrow(counter, Priority.ALWAYS);
		
		attachHandlers();
		addListeners();
		this.setStyle();
		
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler class passed by the controller
	 */
	public void addUpHandler(EventHandler<ActionEvent> e) {
		up.setOnAction(e);
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler class passed by the controller
	 */
	public void addDownHandler(EventHandler<ActionEvent> e) {
		down.setOnAction(e);
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler class passed by the controller
	 */
	public void addAddCustomerHandler(EventHandler<ActionEvent> e) {
		addcustomer.setOnAction(e);
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler class passed by the controller
	 */
	public void addDeleteCustomerHandler(EventHandler<ActionEvent> e) {
		deletecustomer.setOnAction(e);
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler class passed by the controller
	 */
	public void addGenerateInvoiceHandler(EventHandler<ActionEvent> e) {
		generateinvoice.setOnAction(e);
	}
	
	/**
	 * tracks the total of all items the customer is buying
	 */
	public void totalCounter() {
		double total = 0;
		for (StockItem s : invoicestocklist) {
			total += s.getQuantity() * s.getPrice();
		}
		totalprice.setText(String.format("£%.2f", total));
	}
	
	/**
	 * clears the current customer in the combobox
	 */
	public void clearCustomer() {
		customers.getSelectionModel().select(-1);
		customername.clear();
		customerstreet.clear();
		customercity.clear();
		customerpostcode.clear();
	}
	
	/**
	 * populates the visible list of items in inventory
	 * @param stockitems a list of all items in inventory
	 */
	public void populateStock(StockItem[] stockitems) {
		stocklist.clear();
		for (StockItem x : stockitems) {
			stocklist.add(x);
		}	
		stock.setItems(stocklist);
		this.updateStock();
	}
	
	/**
	 * populates the visible list of items the customer is buying
	 * @param invoiceitems a list of all items the customer is buying
	 */
	public void populateInvoice(StockItem[] invoiceitems) {
		invoicestocklist.clear();
		for (StockItem x : invoiceitems) {
			invoicestocklist.add(x);
		}
		invoicestock.setItems(invoicestocklist);
		this.updateStock();
	}
	
	/**
	 * populates the customer combobox with a list of all customers
	 * @param carray an array of customers to be put into the combobox
	 */
	public void populateCustomers(Customer[] carray) {
		customerlist.clear();
		for (Customer c : carray) {
			customerlist.add(c);
		}
		Collections.sort(customerlist);
		customers.setItems(customerlist);
	}
	
	/**
	 * sorts and updates both stocklists
	 */
	public void updateStock() {
		Collections.sort(stocklist);
		stock.setItems(stocklist);
		Collections.sort(invoicestocklist);
		invoicestock.setItems(invoicestocklist);
	}
	
	/**
	 * sets the currently selected item in the stocklist
	 * @param s the item to set as selected
	 */
	public void setSelectedItem(StockItem s) {
    	stock.getSelectionModel().selectFirst();
    	while(stock.getSelectionModel().getSelectedItem().getName().compareTo(s.getName()) != 0) {
    		stock.getSelectionModel().selectNext();
    	}
	}
	
	/**
	 * sets the currently selected item in the invoice stocklist
	 * @param s the item to set as selected
	 */
	public void setSelectedInvoiceItem(StockItem s) {
    	invoicestock.getSelectionModel().selectFirst();
    	while(invoicestock.getSelectionModel().getSelectedItem().getName().compareTo(s.getName()) != 0) {
    		invoicestock.getSelectionModel().selectNext();
    	}
	}
	
	
	/**
	 * gets the currently selected item in the inventory stocklist
	 * @return
	 */
	public StockItem getSelectedItem() {
		try {
			return stock.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * gets the currently selected item in the customers stocklist
	 * @return
	 */
	public StockItem getSelectedInvoiceItem() {
		try {
			return invoicestock.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * returns the full list of stock
	 * @return
	 */
	public ObservableList<StockItem> getStockList() {
		return this.stocklist;
	}
	
	/**
	 * returns the full list of items the customer is buying
	 * @return
	 */
	public ObservableList<StockItem> getInvoiceList() {
		return this.invoicestocklist;
	}
	
	/**
	 * updates the variable for the running total
	 * @return
	 */
	public boolean updateCounterValue() {
		try {
			countervalue = Integer.parseInt(counter.getText());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * returns the value for the running total
	 * @return
	 */
	public int getCounterValue() {
		return countervalue;
	}
	
	/**
	 * returns a value for which of the two stocklists is currently the active window. this is used to ensure stock is always transferred correctly from one to the other.
	 * @return
	 */
	public int focusChecker() {
		return focusval;
	}
	
	/**
	 * attaches event handlers
	 */
	public void attachHandlers() {
		plus.setOnAction(new plusHandler());
		minus.setOnAction(new minusHandler());
		customers.setOnAction(new customerHandler());
	}
	
	/**
	 * fills the customer object with values from the currently selected customer
	 * @param c
	 */
	public void retrieveCustomer(Customer c) {
		c.setName(customername.getText());
		c.setStreet(customerstreet.getText());
		c.setCity(customercity.getText());
		c.setPostcode(customerpostcode.getText());
	}
	
	/**
	 * adds listeners to both the inventory stocklist and the invoicing stocklist to allow tracking of the last one clicked and ensure proper functionality of the up/down arrows
	 */
	public void addListeners() {
		counter.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldvalue, Boolean newvalue) {
				try {
					if (newvalue == false) {
						try {
							int temp = Integer.parseInt(counter.getText());
							countervalue = temp;
						} catch (Exception e) {
							counter.setText(countervalue + "");
						}
					}
				} catch (Exception e) {}
			}
		});
		
		stock.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldvalue, Boolean newvalue) {
				try {
					if (newvalue == true) {
						focusval = 1;
						System.out.println(focusval);
					}
				} catch (Exception e) {}
			}
		});
		
		invoicestock.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldvalue, Boolean newvalue) {
				try {
					if (newvalue == true) {
						focusval = -1;
						System.out.println(focusval);
					}
				} catch (Exception e) {}
			}
		});
	}
	
	private class plusHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			countervalue++;
			counter.setText(countervalue + "");
		}
	}
	
	private class minusHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (countervalue > 0) {
				countervalue--;
				counter.setText(countervalue + "");
			}
		}
	}
	
	private class customerHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			try {
				Customer temp = customers.getSelectionModel().getSelectedItem();
				customername.setText(temp.getName());
				customerstreet.setText(temp.getStreet());
				customercity.setText(temp.getCity());
				customerpostcode.setText(temp.getPostcode());
			} catch (Exception z) {
				System.out.println(z.getMessage());
			}
		}	
	}
	
	/**
	 * sets styling details for many objects in the invoice pane
	 */
	public void setStyle() {
		
		VBox.setVgrow(leftright, Priority.ALWAYS);
		leftright.setSpacing(10);
		leftright.setPadding(new Insets(0,0,0,0));
		left.setMinWidth(550);
		right.setMinWidth(550);
		columnheaders.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		buttons.setSpacing(10);
		plus.setStyle("-fx-font: 40px sans-serif, normal, bold;");
		minus.setStyle("-fx-font: 50px sans-serif, normal, bold;");
		counter.setStyle("-fx-font: 28px sans-serif, normal, bold;");
		counter.setMaxSize(100, 52);
		counter.setMinSize(100, 52);
		counterbox.setAlignment(Pos.CENTER);
		counterbox.minWidth(200);
		valign.setAlignment(Pos.CENTER);
		halign.setAlignment(Pos.CENTER);
		halign.setSpacing(30);
		halign.setMinWidth(200);
		plus.setMinSize(90, 90);
		minus.setMinSize(90, 90);
		up.setMinSize(45, 90);
		down.setMinSize(45, 90);
		up.setMaxSize(45, 90);
		down.setMaxSize(45, 90);
		updown.setSpacing(10);
		left.setSpacing(10);
		right.setSpacing(10);
		righttop.setSpacing(10);
		this.setSpacing(10);
		postcodeadddelete.setSpacing(10);
		adddelete.setSpacing(10);
		columnheaders2.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		customerheader.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		customers.setMaxSize(530, 38);
		customername.setMaxSize(530, 38);
		customerstreet.setMaxSize(530, 38);
		customercity.setMaxSize(530, 38);
		customerpostcode.setMaxSize(260, 38);
		customers.setMinSize(530, 38);
		customername.setMinSize(530, 38);
		customerstreet.setMinSize(530, 38);
		customercity.setMinSize(530, 38);
		customerpostcode.setMinSize(300, 38);
		customername.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		customerstreet.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		customercity.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		customerpostcode.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		addcustomer.setMinSize(220, 38);
		addcustomer.setMaxSize(220, 38);
		addcustomer.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		deletecustomer.setMinSize(220, 38);
		deletecustomer.setMaxSize(220, 38);
		deletecustomer.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		generateinvoice.setMinSize(200, 38);
		generateinvoice.setMaxSize(200, 38);
		generateinvoice.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		pricegenerateinvoice.setSpacing(10);
		pricegenerateinvoice.setAlignment(Pos.CENTER);
		pricealign.setAlignment(Pos.CENTER);
		VBox.setVgrow(pricealign, Priority.ALWAYS);
		VBox.setVgrow(rightbottom, Priority.ALWAYS);
		totalheader.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		totalprice.setMaxSize(200, 55);
		totalprice.setMinSize(200, 55);
		totalprice.setStyle("-fx-font: 30px sans-serif, normal, bold;");
		
	}
}
