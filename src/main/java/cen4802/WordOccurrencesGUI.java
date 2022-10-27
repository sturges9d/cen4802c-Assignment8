package cen4802;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class contains the main method and controls the GUI functionality for the WordOccurrences class via JavaFX.
 * Originally created for CEN 3024, and modified for CEN 4802, Assignment 8, Logging.
 * 
 * @author Stephen Sturges Jr
 * @version 10/26/2022
 */
public class WordOccurrencesGUI extends Application {
	
	/** Constant for logging in this class. */
	private static final Logger LOGGER = Assignment8Logger.LOGGER;
	
	/** Variable that gets primaryStage from the start method parameter. */
	private Stage window;
	/** Variable for the splash/welcome screen. */
	private Scene scene1;
	/** Variable for the results screen. */
	private Scene scene2;
	
	// Constants for button and window sizes.
	/** Constant that controls the width of all buttons. */
	private final int buttonWidth = 50;
	/** Constant that controls the height of all buttons. */
	private final int buttonHeight = 20;
	
	/** Constant that controls the window width. */
	private final int windowWidth = 250;
	/** Constant that controls the window height. */
	private final int windowHeight = 300;
	
	/** Constant for the top inside offset. */
	private final int insetsTop = 10;
	/** Constant for the right inside offset. */
	private final int insetsRight = 10;
	/** Constant for the bottom inside offset. */
	private final int insetsBottom = 10;
	/** Constant for the left inside offset. */
	private final int insetsLeft = 10;
	
	// Webpage variables.
	/** The URL of the webpage to be analyzed. */
	String textURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	/** The first line of the text to be analyzed. */
	String startText = "The Raven"; 
	/** The complete line of text after the final line that is to be counted. */
	String endText = "*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***"; 
	
	/**
	 * This is the main method.
	 * 
	 * @param args Default arguments for main.
	 */
	public static void main(String[] args) {
		Assignment8Logger.setUpLogger();

		LOGGER.log(Level.FINE, "Program Start.");
		launch(args);
		// For demonstrating logging errors.
		Connection connection = WordOccurrencesJDBC.establishConnection();
		WordOccurrencesJDBC.getValuesError(connection);
		WordOccurrencesJDBC.insertValuesError1(connection, "Log Error Test", 1);
		WordOccurrencesJDBC.insertValuesError2(connection, "Log Error Test", 1);
		LOGGER.log(Level.FINE, "Program End.");
	} // End of main method.
	
	/**
	 * Calls the ConfirmBox class. Used to confirm the user's choice to close the program.
	 */
	private void closeProgram() {
		LOGGER.log(Level.FINER, "User requested to Exit.");
		Boolean answer = ConfirmBox.display("Confirmation", "Are you sure you want to exit?");
		if(answer) {
			window.close();
		} // End of if statement.
	} // End of closeProgram method.
	
	/**
	 * Overrides the start method from the Application class.
	 * 
	 * @param primaryStage The main window for the program. The window variable gets this parameter.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Word Occurances");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		// First scene (Splash screen).
		Label label1 = new Label("Welcome to my Word Occurances program!");
		Button nextButton = new Button("Next");
		nextButton.setPrefSize(buttonWidth, buttonHeight);
		nextButton.setOnAction(e -> {
			LOGGER.log(Level.FINEST, "Next button clicked.");
			window.setScene(scene2);
		});
		
		VBox centerMenu = new VBox();
		centerMenu.getChildren().addAll(label1);
		centerMenu.setPadding(new Insets(insetsTop, insetsRight, insetsBottom, insetsLeft));
		centerMenu.setAlignment(Pos.CENTER);
		
		VBox bottomMenu1 = new VBox();
		bottomMenu1.getChildren().addAll(nextButton);
		bottomMenu1.setPadding(new Insets(insetsTop, insetsRight, insetsBottom, insetsLeft));
		bottomMenu1.setAlignment(Pos.BOTTOM_RIGHT);
		
		BorderPane borderPane1 = new BorderPane();
		borderPane1.setCenter(label1);
		borderPane1.setBottom(bottomMenu1);
		
		// Second scene (Results screen).
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(insetsTop, insetsRight, insetsBottom, insetsLeft));
		grid.setVgap(8);;
		grid.setHgap(10);
		
		Label listTitle = new Label("Top 20 most used words in \"" + startText + "\"");
		GridPane.setConstraints(listTitle, 0, 0);
		
		ArrayList<String> poemText = WordOccurrences.readText(textURL, startText, endText);
		WordOccurrences.storeWordsInDatabase(poemText); 
		String listResultString = WordOccurrencesJDBC.getValues(WordOccurrencesJDBC.establishConnection());
		
		ListView<String> listResult = new ListView<String>();
		listResult.getItems().addAll(listResultString);

		GridPane.setConstraints(listResult, 0, 1);
		
		grid.getChildren().addAll(listTitle, listResult);
		
		Button backButton = new Button("Back");
		backButton.setPrefSize(buttonWidth, buttonHeight);
		backButton.setOnAction(e -> {
			LOGGER.log(Level.FINEST, "Back button clicked.");
			window.setScene(scene1);
		});
		
		Button exitButton = new Button("Exit");
		exitButton.setPrefSize(buttonWidth, buttonHeight);
		exitButton.setOnAction(e -> closeProgram());
		
		HBox bottomMenu2 = new HBox();
		bottomMenu2.setPadding(new Insets(insetsTop, insetsRight, insetsBottom, insetsLeft));
		bottomMenu2.setSpacing(windowWidth - ((2 * buttonWidth) + (insetsRight + insetsLeft)));
		bottomMenu2.getChildren().addAll(backButton, exitButton);
		
		BorderPane borderPane2 = new BorderPane();
		borderPane2.setCenter(grid);
		borderPane2.setBottom(bottomMenu2);
		
		scene1 = new Scene(borderPane1, windowWidth, windowHeight);
		scene2 = new Scene(borderPane2, windowWidth, windowHeight);
		
		window.setScene(scene1);
		window.show();
		LOGGER.log(Level.FINER, "Scene " + window.getTitle() + " displayed.");
	} // End of start method.
    
} // End of WordOccurrencesGUI class.
