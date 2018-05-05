package stock;

import java.util.ArrayList;
import java.util.HashMap;

import csv.CSVReading;

public class Stock extends HashMap<Item, Integer>{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5985815780948161216L;
	private static ArrayList<Item> properties; 

	/**
	 * A collection of items. Can be used for representing store inventory,
	 * stock orders, sales logs, and truck cargo.
	 */	
	
	public static void loadInItemProperties(String file) {
		properties = CSVReading.readItemProperties(file);
	}
	
	public static ArrayList<Item> getStockProperties(){
		return properties;
	}
}




