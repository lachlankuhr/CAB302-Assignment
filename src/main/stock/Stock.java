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
	
	public static void loadInItemProperties(String filePath) {
		properties = CSVReading.readItemProperties(filePath);
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
}




