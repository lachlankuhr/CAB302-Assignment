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
	private static HashMap<String, Item> propertiesLookUp = new HashMap<String, Item>();
 
	/**
	 * A collection of items. Can be used for representing store inventory,
	 * stock orders, sales logs, and truck cargo.
	 */	
	
	public static void loadInItemProperties(String filePath) {
		properties = CSVReading.readItemProperties(filePath);
		propertiesLookUp = generatePropertiesLookUp();
	}
	
	public static HashMap<String, Item> generatePropertiesLookUp() {
		for (Item item : properties) {
			propertiesLookUp.put(item.getName(), item);
		}
		return propertiesLookUp;
	}
	
	public static ArrayList<Item> getStockProperties(){
		return properties;
	}
	
	public int getStockQuantity() {
		int count = 0;
		
		for(Item item : this.keySet()) {
			count += this.get(item);
		}
		
		return count;
	}
	
	public double calculateCostOfCargo() {
		double cost = 0.0;
		
		for(Item item : this.keySet()) {
			cost += this.get(item) * item.getManufacturingCost();
		}
		
		return cost; 
	}
	
	public static Item getItem(String itemName) {
		return propertiesLookUp.get(itemName);
	}
	
}




