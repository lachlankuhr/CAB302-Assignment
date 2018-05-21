package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import stock.Item;

public class CSVReading {
	
	public static ArrayList<ArrayList<String>> readCSV(String filePath) throws IOException {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			String row;
			
			while ((row = bufferedReader.readLine()) != null) {
				String[] rowArray = row.split(",");
				data.add(new ArrayList<>(Arrays.asList(rowArray)));
			}
			bufferedReader.close();

		
		return data;
	}

}
