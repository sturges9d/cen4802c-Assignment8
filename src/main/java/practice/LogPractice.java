package practice;

import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogPractice {

	private final static Logger MYLOGGER = Logger.getLogger(LogPractice.class.getName());
	
	public static void main(String[] args) {
		setUpLogger();
		
		MYLOGGER.info("This is a test of MYLOGGER");
	}
	
	public static void setUpLogger() {
		LogManager.getLogManager().reset();
		MYLOGGER.setLevel(Level.ALL);
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.INFO);
		MYLOGGER.addHandler(consoleHandler);
		
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("myPracticeLog.log", true);
			// Directly below was an attempt to format the output of the file.
//			fileHandler.setFormatter(new SimpleFormatter() {
//				private static final String format = "%p %t %c - %m%n";
//				
//				@Override
//				public synchronized String format(LogRecord lr) {
//					return String.format(format,
//									new Date(lr.getMillis()),
//									lr.getLevel().getLocalizedName(),
//									lr.getMessage()
//					);
//				}
//			});
			fileHandler.setLevel(Level.FINE);
			MYLOGGER.addHandler(fileHandler);
		} catch (SecurityException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		
	}

}
