package stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import csv.CSVReading;


public class Stock extends LinkedHashMap<Item, Integer>{

	// Fields 
	private static final long serialVersionUID = 5985815780948161216L;
	private static LinkedHashMap<String, Item> properties = new LinkedHashMap<String, Item>();
 
	/**
	 * A collection of items. Can be used for representing store inventory,
	 * stock orders, sales logs, and truck cargo.
	 * @throws IOException 
	 * @author Lachlan Kuhr
	 */	
	
	/**
	 * Loads in the item properties initially. 
	 * @param filePath The file path the item properties are to be generated from. 
	 * @throws IOException 
	 * @author Lachlan Kuhr
	 */
	
	public static void loadInItemProperties(String filePath) throws IOException {
		properties = new LinkedHashMap<String, Item>();
		ArrayList<ArrayList<String>> data = CSVReading.readCSV(filePath); // return CSV
		Double temperature; 
		for (ArrayList<String> row : data) {
			if (row.size() == 5) { // then we know temperature doesn't exist
				temperature = null;
			} else {
				temperature = Double.valueOf(row.get(5)); // set the temperature
			}
			properties.put(row.get(0), new Item(row.get(0), Double.parseDouble(row.get(1)), 
					Double.parseDouble(row.get(2)), 
					Integer.parseInt(row.get(3)),
					Integer.parseInt(row.get(4)),
					temperature));
					// put(Name, Quantity, Manufacturing Cost, Sell Price, Reorder Point, Reorder Amount, Temperature)
		}	
	}
	
	/**
	 * Getter for properties field. 
	 * @return Properties field. 
	 * @author Lachlan Kuhr
	 */
	
	public static LinkedHashMap<String, Item> getStockProperties(){
		return properties;
	}
	
	/**
	 * Calculates the total number of items in the stock. 
	 * @return The number of items in the stock. 
	 * @author Lachlan Kuhr
	 */
	
	public int getStockQuantity() {
		int count = 0;
		
		for(Item item : this.keySet()) {
			count += this.get(item);
		}
		
		return count;
	}
	
	/**
	 * Calculates the total cost of the items if they were bought. 
	 * @return The total cost of items. 
	 * @author Lachlan Kuhr
	 */
	
	public double calculateCostOfCargo() {
		double cost = 0.0;
		
		for(Item item : this.keySet()) {
			cost += this.get(item) * item.getManufacturingCost();
		}
		
		return cost; 
	}
	
	/**
	 * Getter method to return the item specified by itemName. 
	 * @param itemName
	 * @return Item corresponding to itemName string. 
	 * @author Lachlan Kuhr
	 */
	
	public static Item getItem(String itemName) {
		return properties.get(itemName);
	}

	/**
	 * Provides the item in the stock that needs the lowest temperature to be maintained.
	 * If no item needs temperature control, returns null.
	 * @return The coldest item in the stock
	 * @author Lachlan Kuhr
	 */
	public Item findColdestItem() {
		Double lowestTemp = null;
		Item coldestItem = null;
		for(Item item: this.keySet()) {	
			Double itemTemp = item.getTemperature();
			
			// set the coldest item to be the first item with a temperature
			if(coldestItem == null) {
				coldestItem = item;
				lowestTemp = itemTemp;
				continue;
			}
			
			// check further items if they are colder
			if(lowestTemp == null) {
				lowestTemp = itemTemp;
				coldestItem = item;
			}else if(itemTemp != null && itemTemp < lowestTemp) {
				lowestTemp = itemTemp;
				coldestItem = item;
			}
		}
		return coldestItem;
	}
	
}