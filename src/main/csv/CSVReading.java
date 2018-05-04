package csv;

import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import stock.Item;

public class CSVReading {
	
	private static ArrayList<Item> itemList = new ArrayList<Item>(); 
	
	public static ArrayList<Item> readItemProperties(String file)  {
		try {
			FileReader reader = new FileReader(file);
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


}
