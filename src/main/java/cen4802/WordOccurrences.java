package cen4802;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains methods for reading text from a webpage, counting the number of times a word is written, and sorting the list of words from greatest number of
 * occurrences to least.
 * Usage: readText > convertArrayListToHashMap > sortHashMap > convertHashMapToString
 * Originally created for CEN 3024, and modified for CEN 4802, Assignment 8, Logging.
 * 
 * @author Stephen Sturges Jr
 * @version 10/26/2022
 */
public class WordOccurrences {
    
	/** Constant for logging in this class. */
	private static final Logger LOGGER = Assignment8Logger.LOGGER;
	
    /**
     * Loops through the ArrayList, counts the number of occurrences of each word, and outputs a HashMap containing each word and its number of occurrences.
     * @param inputText An ArrayList of Strings. Each string is a line of text from the readText method. 
     * @return A HashMap containing (word, number of occurrences) pairs.
     */
	public static HashMap<String, Integer> convertArrayListToHashMap(ArrayList<String> inputText) {
    	// Count the number of occurrences of a word in the ArrayList of text from the URL and store the word and its number of occurrences in a HashMap.
        HashMap<String, Integer> results = new HashMap<>();
        for (int i = 0; i < inputText.size(); i++) {
            int wordCount = 0;
            String word = inputText.get(i);
            for (int j = 0; j < inputText.size(); j++) {
                if (inputText.get(j).equalsIgnoreCase(word)) {
                    wordCount++;
                    inputText.remove(j);
                    // Account for the change in ArrayList size after removing element. 
                    if(i > 0) {
                    	i -= 1;
                    } else {
                    	i = 0;
                    } // End of if-else statement.
                    if(j > 0) {
                    	j -= 1;
                    } else {
                    	j = 0;
                    } // End of if-else statement.
                } // End of if statement.
            } // End of for loop.
            results.put(word, wordCount);
            wordCount = 0;
        } // End of for loop.
        return results;
    } // End of convertArrayListToHashMap() method.
    
	/**
     * Loops through the ArrayList, counts the number of occurrences of each word, inserts word and count into a database, and outputs a HashMap containing each word and its number of occurrences.
     * @param inputText An ArrayList of Strings. Each string is a line of text from the readText method. 
     * @return A HashMap containing (word, number of occurrences) pairs.
     */
	public static HashMap<String, Integer> convertArrayListToHashMapAndStoreInDatabase(ArrayList<String> inputText) {
    	// Count the number of occurrences of a word in the ArrayList of text from the URL and store the word and its number of occurrences in a HashMap.
        HashMap<String, Integer> results = new HashMap<>();
        for (int i = 0; i < inputText.size(); i++) {
            int wordCount = 0;
            String word = inputText.get(i);
            for (int j = 0; j < inputText.size(); j++) {
                if (inputText.get(j).equalsIgnoreCase(word)) {
                    wordCount++;
                    inputText.remove(j);
                    // Account for the change in ArrayList size after removing element. 
                    if(i > 0) {
                    	i -= 1;
                    } else {
                    	i = 0;
                    } // End of if-else statement.
                    if(j > 0) {
                    	j -= 1;
                    } else {
                    	j = 0;
                    } // End of if-else statement.
                } // End of if statement.
            } // End of for loop.
            results.put(word, wordCount);
            System.out.println("Rows Affected: " + WordOccurrencesJDBC.insertValues(WordOccurrencesJDBC.establishConnection(), word, wordCount)); // Insert values into database.
            wordCount = 0;
        } // End of for loop.
        return results;
    } // End of convertArrayListToHashMap() method.
	
    /**
     * Converts a sorted HashMap into Strings ending in a new line character for display.
     * @param inputText A HashMap containing a word and its number of occurrences.
     * @return A string in the format: "i. word, number_of_occurrences"
     */
    public static String convertHashMapToString(HashMap<String, Integer> inputText) {
    	String result = ""; // Used for output to TestClass GUI.
        Set<Entry<String, Integer>> entrySetSortedByValue = inputText.entrySet();
        int i = 0;
        for(Entry<String, Integer> mapping : entrySetSortedByValue) {
            i++;
            result += (i + ". " + mapping.getKey() + ", " + mapping.getValue() + "\n");
            // Stop the list at 20 entries.
            if (i == 20) {
                break;
            } // End of if statement.
        } // End of for loop.
        return result;
    } // End of convertHashMapToString method.
    
    /**
     * Reads the text from a webpage given a URL, the first line, and the last line after the relevant text.
     * @param inputURL The URL of the webpage you wish to extract text from.
     * @param startText The first line of the text you want to extract.
     * @param endText The line after the last line you want to extract.
     * @return An ArrayList of lower case strings, each a line of text from the webpage.
     */
    public static ArrayList<String> readText(String inputURL, String startText, String endText) {
    	ArrayList<String> result = new ArrayList<String>();
    	URL url = null;
		try {
			url = new URL(inputURL);
		} catch (MalformedURLException e1) {
			LOGGER.log(Level.SEVERE, "Invalid URL.", e1);
		} // End of try-catch block.
    	try {
            BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            // Define loop control variables and create ArrayList for analysis.
            Boolean readInput = true;
            Boolean printLines = false;
            Boolean emptyLine = false;
            String inputLine;
            
            /**
             * Replaces all HTML tags with nothing and replace the special character ’ with '.
             * If the string with all replacements is null, or the readInput variable is false, then exit the loop.
             */
            while ((inputLine = input.readLine().replaceAll("<[^>]*>", "").replaceAll("’", "")) != null && readInput) {
                // Controls the start of the relevant text to analyze.
                if (inputLine.equalsIgnoreCase(startText)) {
                    printLines = true;
                } // End of if statement.
                
                // Removes empty lines from being included in the relevant text ArrayList.
                if (inputLine.isEmpty()) {
                    emptyLine = true;
                } else {
                    emptyLine = false;
                } // End of if-else statement.
                
                // Controls the end of the relevant text to analyze.
                if (inputLine.equalsIgnoreCase(endText)) {
                    printLines = false;
                    readInput = false;
                } // End of if statement.
                
                // Splits the String lines into string words and places them into the textToAnalyze array.
                if (printLines == true && emptyLine == false) {
                    String outputTextArray[] = inputLine.split("&mdash|[^'a-z[A-Z]]|\'s");
                    for (int i = 0; i < outputTextArray.length; i++) {
                        if (!outputTextArray[i].isEmpty()) {
                            result.add(outputTextArray[i].trim().toLowerCase());
                        } // End of if statement.
                    } // End of for loop.
                } // End of if statement.
            } // End of while loop.
    	} catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Read text error.", e);
        } // End of try-catch statement.
    	return result;
    } // End of readText method.
    
    /**
     * Sorts the values stored in a HashMap according to the integer value, greatest to least.
     * @param inputText HashMap with String keys and Integer values containing a word and its number of occurrences.
     * @return A sorted HashMap containing words and their number of occurrences from highest number of occurrences to lowest.
     */
    public static HashMap<String, Integer> sortHashMap(HashMap<String, Integer> inputText) {
        // Custom comparator to order the values (occurrences) from greatest to least.
        Comparator<Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                Integer v1 = e1.getValue();
                Integer v2 = e2.getValue();
                String k1 = e1.getKey();
                String k2 = e2.getKey();
                // If the number of occurrences are equal, sort the words in alphabetical order.
                if (v2 == v1) {
                    return k1.compareTo(k2);
                } else {
                    return v2.compareTo(v1);
                } // End of if-else statement.
            } // End of compare method.
        };
        
        // Place all the entries from the results HashMap into a Set.
        Set<Entry<String, Integer>> resultsSet = inputText.entrySet();

        // Create a new ArrayList from the Set.
        List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(resultsSet);

        // Sort the ArrayList using the custom comparator.
        Collections.sort(listOfEntries, valueComparator);

        // Create a new LinkedHashMap to store the values from the sorted ArrayList.
        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>(listOfEntries.size());

        // Place the key and value pairs into the new LinkedHashMap.
        for (Entry<String,Integer> entry : listOfEntries) {
        	result.put(entry.getKey(), entry.getValue());
        } // End of for loop.
        return result;
    } // End of sortHashMap method.

    /**
     * Loops through the ArrayList, counts the number of occurrences of each word, and stores each word and its number of occurrences in a database.
     * @param inputText An ArrayList of Strings. Each string is a line of text from the readText method. 
     * @return A HashMap containing (word, number of occurrences) pairs.
     */
	public static void storeWordsInDatabase(ArrayList<String> inputText) {
		Connection connection = WordOccurrencesJDBC.establishConnection();
		for (int i = 0; i < inputText.size(); i++) {
            int wordCount = 0;
            String word = inputText.get(i);
            for (int j = 0; j < inputText.size(); j++) {
                if (inputText.get(j).equalsIgnoreCase(word)) {
                    wordCount++;
                    inputText.remove(j);
                    // Account for the change in ArrayList size after removing element. 
                    if(i > 0) {
                    	i -= 1;
                    } else {
                    	i = 0;
                    } // End of if-else statement.
                    if(j > 0) {
                    	j -= 1;
                    } else {
                    	j = 0;
                    } // End of if-else statement.
                } // End of if statement.
            } // End of for loop.
            WordOccurrencesJDBC.insertValues(connection, word, wordCount);
            wordCount = 0;
        } // End of for loop.
    } // End of convertArrayListToHashMap() method.
    
} // End of WordOccurrences class.