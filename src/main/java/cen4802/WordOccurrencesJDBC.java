package cen4802;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class adds database connectivity for the WordOccurrences program.
 * Originally created for CEN 3024, and modified for CEN 4802, Assignment 8, Logging.
 * 
 * @author Stephen Sturges Jr
 * @version 10/26/2022
 */
public class WordOccurrencesJDBC {
	
	/** Constant for logging in this class. */
	private static final Logger LOGGER = Assignment8Logger.LOGGER;
	
	/**
	 * Establishes a connection to a hard coded MySQL server.
	 * 
	 * @return Returns an established connection.
	 */
	public static Connection establishConnection() {
		Connection connection;
		String serverURL = "jdbc:mysql://localhost/word_occurrences";
		String username = "wordOccurrencesUser";
		String password = "wordOccurrencesUserPW";
		try {
			connection = DriverManager.getConnection(serverURL, username, password);
			LOGGER.log(Level.INFO, "Database connection established.");
			return connection;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Database connection failed.", e);
		}
		return null;
	} // End of establishConnection method.
	
	/**
	 * Retrieves values from the database.
	 * 
	 * @param connection	JDBC connection.
	 * @return				String, results query from database.
	 */
	public static String getValues(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM word ORDER BY count DESC LIMIT 20";
			ResultSet queryResult = statement.executeQuery(sql);
			String result = "";
			int i = 0;
			while (queryResult.next()){
				i++;
				result += i + ". " + queryResult.getString("word") + ", " + queryResult.getInt("count") + "\n";
			} // End of while loop.
			LOGGER.log(Level.INFO, "Database result query returned.");
			return result;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL results query error.", e);
		} // End of try-catch block.
		return null;
	} // End of getValues method.
	
	/**
	 * Retrieves values from the database. Intentionally produces an error for Assignment 8 logging.
	 * 
	 * @param connection	JDBC connection.
	 * @return				String, results query from database.
	 */
	public static String getValuesError(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM word ORDER BY count DESC LIMIT 20error";
			ResultSet queryResult = statement.executeQuery(sql);
			String result = "";
			int i = 0;
			while (queryResult.next()){
				i++;
				result += i + ". " + queryResult.getString("word") + ", " + queryResult.getInt("count") + "\n";
			} // End of while loop.
			LOGGER.log(Level.INFO, "Database result query returned.");
			return result;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL results query error.", e);
		} // End of try-catch block.
		return null;
	} // End of getValues method.
	
	/**
	 * Inserts word and number of occurrence values into the database where the connection is established.
	 * 
	 * @param connection	The connection established, by the establishConnection method, to the database.
	 * @param word			String, word to be entered into the database.
	 * @param count			int, number of occurrences of the associated word.
	 * @return 				int, number of rows affected by the insert statement.
	 */
	public static int insertValues(Connection connection, String word, int count) {
		int rowsAffected = 0;
		try {
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO word VALUES ('" + word + "'," + count + ")";
			rowsAffected = statement.executeUpdate(sql);
			LOGGER.log(Level.INFO, rowsAffected + " rows affected.");
			return rowsAffected;
		} catch (SQLIntegrityConstraintViolationException dv) {
			LOGGER.log(Level.WARNING, "Word, " + word + ", is already in the database.");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL insert query error.", e);
		} // End of try-catch block.
		return rowsAffected;
	} // End of insertValues statement.
	
	/**
	 * Inserts word and number of occurrence values into the database where the connection is established.  Intentionally produces an error for Assignment 8 logging.
	 * 
	 * @param connection	The connection established, by the establishConnection method, to the database.
	 * @param word			String, word to be entered into the database.
	 * @param count			int, number of occurrences of the associated word.
	 * @return 				int, number of rows affected by the insert statement.
	 */
	public static int insertValuesError1(Connection connection, String word, int count) {
		int rowsAffected = 0;
		try {
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO word VALUES (''" + word + "'," + count + ")";
			rowsAffected = statement.executeUpdate(sql);
			LOGGER.log(Level.INFO, rowsAffected + " rows affected.");
			return rowsAffected;
		} catch (SQLIntegrityConstraintViolationException dv) {
			LOGGER.log(Level.WARNING, "Word, " + word + ", is already in the database.");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL insert query error.", e);
		} // End of try-catch block.
		return rowsAffected;
	} // End of insertValues statement.
	
	/**
	 * Inserts word and number of occurrence values into the database where the connection is established.  Intentionally produces an error for Assignment 8 logging.
	 * 
	 * @param connection	The connection established, by the establishConnection method, to the database.
	 * @param word			String, word to be entered into the database.
	 * @param count			int, number of occurrences of the associated word.
	 * @return 				int, number of rows affected by the insert statement.
	 */
	public static int insertValuesError2(Connection connection, String word, int count) {
		int rowsAffected = 0;
		try {
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO word VALUES ('" + word + "'," + count + "," + "'error')";
			rowsAffected = statement.executeUpdate(sql);
			LOGGER.log(Level.INFO, rowsAffected + " rows affected.");
			return rowsAffected;
		} catch (SQLIntegrityConstraintViolationException dv) {
			LOGGER.log(Level.WARNING, "Word, " + word + ", is already in the database.");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL insert query error.", e);
		} // End of try-catch block.
		return rowsAffected;
	} // End of insertValues statement.
	
} // End of WordOccurrencedJDBC class.
