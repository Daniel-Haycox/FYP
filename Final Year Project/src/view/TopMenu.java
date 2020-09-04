package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class TopMenu extends MenuBar {

	private MenuItem details, instructions, exit;
	
	/**
	 * the menu bar at the top of the UI
	 */
	public TopMenu() {
		
		this.setId("menubar");
		Menu menu = new Menu("File");
		menu.setId("filemenu");
		
		details = new MenuItem("Your Details");
		exit = new MenuItem("Exit");
		exit.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));
		
		menu.getItems().addAll(details, new SeparatorMenuItem(), exit);
		this.getMenus().add(menu);
		
		menu = new Menu("Help");
		
		instructions = new MenuItem("User Guide");
		instructions.setAccelerator(KeyCombination.keyCombination("SHORTCUT+I"));
		
		menu.getItems().add(instructions);
		this.getMenus().add(menu);
		
		this.setStyle("-fx-font: 15px sans-serif, normal, bold;");
		
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler passed by the controller
	 */
	public void addDetailsHandler(EventHandler<ActionEvent> e) {
		details.setOnAction(e);
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler passed by the controller
	 */
	public void addExitHandler(EventHandler<ActionEvent> e) {
		exit.setOnAction(e);
	}
	
	/**
	 * adds the eventhandler passed by the controller
	 * @param e the eventhandler passed by the controller
	 */
	public void addInstructionsHandler(EventHandler<ActionEvent> e) {
		instructions.setOnAction(e);
	}
	
	/**
	 * fires the details menu option. this causes the alert in the controller class to fire.
	 */
	public void activateDetails() {
		details.fire();
	}
}
