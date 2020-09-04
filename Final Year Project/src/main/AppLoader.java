package main;

import controller.StockManagerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.StockManagerRootPane;

public class AppLoader extends Application {

	private StockManagerRootPane view;
	private StockManagerController controller;

	@Override
	public void init() {
		view = new StockManagerRootPane();
		view.getStylesheets().add(this.getClass().getResource("/main/StockManager.css").toExternalForm());
		view.setId("mainview");
		controller = new StockManagerController(view);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setMinWidth(1145);
		stage.setMaxWidth(1145);
		stage.setMinHeight(700);
		stage.setMaxHeight(1080);
		stage.setTitle("Stock Management and Invoicing");
		stage.setScene(new Scene(view));
		stage.show();
		
		stage.setHeight(900);
		
		controller.setStage(stage);
		
		controller.ownerCheck();
	}

	/**
	 * standard java application starter
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * returns the controller object
	 * @return returns the controller object
	 */
	public StockManagerController getController() {
		return controller;
	}

	/**
	 * sets the controller object
	 * @param the controller object to be set
	 */
	public void setController(StockManagerController controller) {
		this.controller = controller;
	}
	

}