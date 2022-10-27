package cen4802;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created with guidance from TheNewBoston JavaFX tutorial, this class creates a confirmation box for a user to confirm their action.
 * Originally created for CEN 3024, and modified for CEN 4802, Assignment 8, Logging.
 * 
 * @author Stephen Sturges Jr
 * @version 10/26/2022
 */
public class ConfirmBox {

	/** Constant for logging in this class. */
	private static final Logger LOGGER = Assignment8Logger.LOGGER;
	
	/** Variable that holds the boolean result. */
	private static boolean answer;
	
	// Constants for button and window sizes.
	/** Constant that controls the width of all buttons. */
	private static final int buttonWidth = 50;
	/** Constant that controls the height of all buttons. */
	private static final int buttonHeight = 20;
	
	/** Constant that controls the window width. */
	private static final int windowWidth = 250;
	/** Constant that controls the window height. */
	private static final int windowHeight = 100;
	
	/** Constant for the top inside offset. */
	private static final int insetsTop = 10;
	/** Constant for the right inside offset. */
	private static final int insetsRight = 10;
	/** Constant for the bottom inside offset. */
	private static final int insetsBottom = 10;
	/** Constant for the left inside offset. */
	private static final int insetsLeft = 10;
	
	/**
	 * Displays a confirmation box with window title and displaying the input message to the user with yes and no buttons.
	 * @param title The title of the confirmation box window.
	 * @param message The message to be displayed to the user.
	 * @return boolean, true ("Yes" button clicked) or false ("No" button clicked).
	 */
	public static boolean display(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		
		// Create buttons.
		Button yesButton = new Button("Yes");
		yesButton.setPrefSize(buttonWidth, buttonHeight);
		
		Button noButton  = new Button("No");
		noButton.setPrefSize(buttonWidth, buttonHeight);
		
		// Set button behavior.
		yesButton.setOnAction(e -> {
			LOGGER.log(Level.FINEST, "Yes Button clicked.");
			answer = true;
			window.close();
		});
		
		noButton.setOnAction(e -> {
			LOGGER.log(Level.FINEST, "No Button clicked.");
			answer = false;
			window.close();
		});
		
		// Set layout.
		HBox bottomMenu = new HBox();
		bottomMenu.setPadding(new Insets(insetsTop, insetsRight, insetsBottom, insetsLeft));
		bottomMenu.getChildren().addAll(noButton, yesButton);
		bottomMenu.setSpacing(windowWidth - ((2 * buttonWidth) + (insetsRight + insetsLeft)));
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(label);
		borderPane.setBottom(bottomMenu);
		
		// Add layout to scene.
		Scene scene = new Scene(borderPane, windowWidth, windowHeight);
		
		// Display window.
		LOGGER.log(Level.FINER, "Confirmation box displayed.");
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	} // End of display method.

} // End of ConfirmBox class.