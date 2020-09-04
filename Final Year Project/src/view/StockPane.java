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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.StockItem;

public class StockPane extends VBox {
	
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
	private VBox addremove;
	private Button add;
	private Button remove;
	private ObservableList<StockItem> stocklist;
	private ListView<StockItem> stock;
	
	private VBox right;
	private VBox righttop;
	private VBox rightbottom;
	private Text descheader;
	private TextArea descbox;
	private HBox deletealign;
	private Button deleteitem;
	private Text newitemheader;
	private HBox nameqntprice;
	private TextArea name;
	private VBox qntprice;
	private TextArea qnt;
	private TextArea price;
	private HBox descsub;
	private TextArea desc;
	private Button submit;
	
	
	/**
	 * the pane for the stock/inventory management half of the application
	 */
	public StockPane() {
		
		this.setId("stockpane");
		
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
		
		addremove = new VBox();
		
		add = new Button("Add");
		remove = new Button("Remove");
		addremove.getChildren().addAll(add, remove);
		addremove.setId("addremove");
		buttons.getChildren().add(addremove);
		
		right = new VBox();
		right.setId("right");
		righttop = new VBox();
		rightbottom = new VBox();
		right.getChildren().addAll(righttop, rightbottom);
		leftright.getChildren().add(right);
		
		descheader = new Text(" Description");
		righttop.getChildren().add(descheader);
		
		descbox = new TextArea();
		descbox.setId("descbox");
		descbox.setWrapText(true);
		descbox.setMouseTransparent(true);
		descbox.setFocusTraversable(false);
		righttop.getChildren().add(descbox);
		
		deletealign = new HBox();
		righttop.getChildren().add(deletealign);
		
		deleteitem = new Button("Delete Item");
		deletealign.getChildren().add(deleteitem);
		
		newitemheader = new Text("Insert New Item");
		rightbottom.getChildren().add(newitemheader);
		
		nameqntprice = new HBox();
		rightbottom.getChildren().add(nameqntprice);
		
		name = new TextArea();
		name.setWrapText(true);
		name.setPromptText("Product Name");
		
		qntprice = new VBox();
		qntprice.setId("qntprice");
		qnt = new TextArea();
		qnt.setWrapText(true);
		qnt.setPromptText("Quantity");
		price = new TextArea();
		price.setWrapText(true);
		price.setPromptText("Price");
		nameqntprice.getChildren().addAll(name, qntprice);
		qntprice.getChildren().addAll(qnt, price);
		
		descsub = new HBox();
		rightbottom.getChildren().add(descsub);
		
		desc = new TextArea();
		desc.setPromptText("Product Description");
		desc.setWrapText(true);
		submit = new Button("Submit");
		descsub.getChildren().addAll(desc, submit);
		
		VBox.setVgrow(stock, Priority.ALWAYS);
		HBox.setHgrow(counter, Priority.ALWAYS);
		
		attachHandlers();
		addListeners();
		this.setStyle();
	}
	
	
	/**
	 * attaches event handlers for the plus and minus buttons
	 */
	public void attachHandlers() {
		plus.setOnAction(new plusHandler());
		minus.setOnAction(new minusHandler());
	}
	
	public void setWidth(double width) {
		left.prefWidth(width);
		right.prefWidth(width);
		stock.prefWidth(width);
	}
	
	/**
	 * populates the list of all items in the inventory
	 * @param stockitems the list of items to populate the visible list with
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
	 * forces a sort and update of the stocklist
	 */
	public void updateStock() {
		Collections.sort(stocklist);
		stock.setItems(stocklist);
	}
	
	/**
	 * populates the stockitem object with the new values if the validator passes
	 * @param s stockitem object to be populated
	 */
	public void submitItem(StockItem s) {
		if (validator()) {
			s.setName(name.getText());
			s.setQuantity(Integer.parseInt(qnt.getText()));	
			s.setPrice(Double.parseDouble(price.getText()));
			s.setDesc(desc.getText());
		}
		else
			System.out.println("test failed");
	}
	
	/**
	 * validator class to check if all values of the new stock item are acceptable and gives visual feedback to the user if not
	 * @return returns true if all fields are acceptable, false otherwise
	 */
	public boolean validator() {
		if (qnt.getText().matches("^[0-9]+$") && price.getText().matches("^[0-9.]+$") && name.getText() != null && qnt.getText() != null && desc.getText() != null) {
			name.setStyle("-fx-border-color:transparent; -fx-font: 19px sans-serif, normal, bold;");
			qnt.setStyle("-fx-border-color:transparent; -fx-font: 19px sans-serif, normal, bold;");
			price.setStyle("-fx-border-color:transparent; -fx-font: 19px sans-serif, normal, bold;");
			desc.setStyle("-fx-border-color:transparent; -fx-font: 19px sans-serif, normal, bold;");
			return true;
		} else {
			name.setStyle("-fx-border-color:red; -fx-font: 19px sans-serif, normal, bold;");
			qnt.setStyle("-fx-border-color:red; -fx-font: 19px sans-serif, normal, bold;");
			price.setStyle("-fx-border-color:red; -fx-font: 19px sans-serif, normal, bold;");
			desc.setStyle("-fx-border-color:red; -fx-font: 19px sans-serif, normal, bold;");
			return false;
		}
	}
	
	/**
	 * sets the currently selected item in the stocklist
	 * @param s the stockitem to be selected
	 */
	public void setSelectedItem(StockItem s) {
    	stock.getSelectionModel().selectFirst();
    	while(stock.getSelectionModel().getSelectedItem().getName().compareTo(s.getName()) != 0) {
    		stock.getSelectionModel().selectNext();
    	}
	}
	
	/**
	 * gets the currently selected item in the stocklist
	 * @return the stock item that is selected
	 */
	public StockItem getSelectedItem() {
		try {
			return stock.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @return returns the current counter value
	 */
	public int getCounterValue() {
		return countervalue;
	}
	
	/**
	 * adds the event handler passed by the controller
	 * @param e the event handler class passed by the controller
	 */
	public void addDeleteHandler(EventHandler<ActionEvent> e) {
		deleteitem.setOnAction(e);
	}
	
	/**
	 * adds the event handler passed by the controller
	 * @param e the event handler class passed by the controller
	 */
	public void addSubmitHandler(EventHandler<ActionEvent> e) {
		submit.setOnAction(e);
	}
	
	/**
	 * adds the event handler passed by the controller
	 * @param e the event handler class passed by the controller
	 */
	public void addAddHandler(EventHandler<ActionEvent> e) {
		add.setOnAction(e);
	}
	
	/**
	 * adds the event handler passed by the controller
	 * @param e the event handler class passed by the controller
	 */
	public void addRemoveHandler(EventHandler<ActionEvent> e) {
		remove.setOnAction(e);
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
	
	/**
	 * adds listeners to ensure the counter correctly updates
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
		
		stock.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockItem>() {
			@Override
			public void changed(ObservableValue<? extends StockItem> observable, StockItem oldvalue, StockItem newvalue) {
				//descbox.setText(stock.getSelectionModel().getSelectedItem().getDesc());
				//descbox.setText(stock.getSelectionModel().getSelectedItem().getDesc());
				try {
					descbox.setText(newvalue.getDesc());
				} catch (Exception e) {}
			}
		});
	}
	
	/**
	 * sets styles for many elements in the stock pane
	 */
	public void setStyle() {
		this.setStyle("-fx-background-color: #fff5ba;");
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
		add.setMinSize(100, 40);
		add.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		remove.setMinSize(100, 40);
		remove.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		addremove.setSpacing(10);
		left.setSpacing(10);
		right.setSpacing(10);
		submit.setMinSize(100, 100);	
		descheader.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		descbox.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		deletealign.setAlignment(Pos.CENTER);
		deleteitem.setMinSize(100, 40);
		deleteitem.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		newitemheader.setStyle("-fx-font: 20px sans-serif, normal, bold;");
		this.setSpacing(10);
		nameqntprice.setSpacing(10);
		qntprice.setSpacing(10);
		descsub.setSpacing(10);
		name.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		qnt.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		price.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		name.setMinSize(400, 38);
		qnt.setMinSize(100, 38);
		price.setMinSize(100, 38);
		name.setMaxSize(450, 38);
		qnt.setMaxSize(100, 38);
		price.setMaxSize(100, 38);
		submit.setMinSize(100, 120);
		desc.setMinSize(420, 120);
		desc.setMaxSize(420, 120);
		desc.setStyle("-fx-font: 19px sans-serif, normal, bold;");
		VBox.setVgrow(descbox, Priority.ALWAYS);
		VBox.setVgrow(righttop, Priority.ALWAYS);
		righttop.setSpacing(10);
		rightbottom.setSpacing(10);
		submit.setStyle("-fx-font: 16px sans-serif, normal, bold;");
		
	}
	
}
