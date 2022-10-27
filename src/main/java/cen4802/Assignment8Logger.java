package cen4802;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.application.Application;

/**
 * Created for CEN 4802, Assignment 8, Logging.
 * 
 * @author Stephen Sturges Jr.
 * @version 10/26/2022
 */
public abstract class Assignment8Logger extends Application {

	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void setUpLogger() {
		LogManager.getLogManager().reset();
		LOGGER.setLevel(Level.ALL);
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);
		LOGGER.addHandler(consoleHandler);
		
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("Assignment8.log", true);
			fileHandler.setLevel(Level.ALL);
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException se) {
			se.printStackTrace();
			LOGGER.log(Level.SEVERE, "File logger not working.", se);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.log(Level.SEVERE, "File logger not working.", ioe);
		}
	}

}
