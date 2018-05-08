package stock;

import java.util.ArrayList;
import java.util.HashMap;
import csv.CSVReading;


public class Stock extends HashMap<Item, Integer>{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5985815780948161216L;
	private static ArrayList<Item> properties = new ArrayList<Item>();
	private static HashMap<String, Item> propertiesLookUp = new HashMap<String, Item>();
 
	/**
	 * A collection of items. Can be used for representing store inventory,
	 * stock orders, sales logs, and truck cargo.
	 */	
	
	public static void loadInItemProperties(String filePath) {
		ArrayList<ArrayList<String>> data = CSVReading.readCSV(filePath);
		Double temperature; 
		for (ArrayList<String> row : data) {
			if (row.size() == 5) {
				temperature = null;
			} else {
				temperature = Double.valueOf(row.get(5));
			}
			properties.add(new Item(row.get(0), Double.parseDouble(row.get(1)), 
					Double.parseDouble(row.get(2)), 
					Integer.parseInt(row.get(3)),
					Integer.parseInt(row.get(4)),
					temperature));
		}
		
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




