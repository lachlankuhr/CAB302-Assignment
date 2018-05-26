package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import stock.Item;

/**
 * Provides reading of CSV functionality. 
 * Only static implementation.
 * @author Lachlan Kuhr
 */

public class CSVReading {
	
	/**
	 * Static method to read data from a csv. 
	 * @param filePath The directory from which the file is to be loaded from.
	 * @return The data in the csv file.
	 * @throws IOException When the file is unable to be read. 
	 */
	
	public static ArrayList<ArrayList<String>> readCSV(String filePath) throws IOException {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); // instantiate data object

			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath)); // for reading in the csv data
			String row;
			
			while ((row = bufferedReader.readLine()) != null) { // to read in each line of the csv
				String[] rowArray = row.split(",");
				data.add(new ArrayList<>(Arrays.asList(rowArray))); 
			}
			bufferedReader.close(); 

		return data;
	}

}
