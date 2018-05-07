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
	
	private static ArrayList<Item> itemList = new ArrayList<Item>(); 
	private static ArrayList<ArrayList<String>> salesLog = new ArrayList<ArrayList<String>>();
	
	
	public static ArrayList<Item> readItemProperties(String filePath)  {
		try {
			FileReader reader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String row;
			
			while ((row = bufferedReader.readLine()) != null) {
					String[] rowArray = row.split(",");
					
					Double temperature;
					
					if (rowArray.length == 5) {
						temperature = null;
					} else {
						temperature = Double.valueOf(rowArray[5]);
					}
					
					itemList.add(new Item(rowArray[0], Double.parseDouble(rowArray[1]), 
							Double.parseDouble(rowArray[2]), 
							Integer.parseInt(rowArray[3]),
							Integer.parseInt(rowArray[4]),
							temperature));
			}
			bufferedReader.close();
		} catch (Exception e) {
			
		}
		return itemList;
	}
	
	public static ArrayList<ArrayList<String>> readSalesLog(String file) {
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String row;
			
			while ((row = bufferedReader.readLine()) != null) {
					String[] rowArray = row.split(",");
					
					salesLog.add(new ArrayList<>(Arrays.asList(rowArray)));
			}
			bufferedReader.close();
		} catch (Exception e) {
			
		}
		return salesLog;
	}

	public static ArrayList<ArrayList<String>> readManifest(String filePath) {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			String row;
			
			while ((row = bufferedReader.readLine()) != null) {
				String[] rowArray = row.split(",");
				data.add(new ArrayList<>(Arrays.asList(rowArray)));
			}
			bufferedReader.close();
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
	
		
		return data;
	}

}
