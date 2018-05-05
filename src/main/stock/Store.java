package stock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import csv.CSVReading;

public class Store {
	
	/**
	 * An object for representing the store itself.
	 */
	
	// Fields
	private double capital;
	private Stock inventory; 
	private String name; 
	private static Store store; 
	
	/**
	 * Implementing the singleton design pattern. 
	 * @param capital
	 * @param name
	 */
	
	protected Store(double capital, String name) {
		this.capital = capital; 
		this.name = name;
		inventory = new Stock();
	}
	
	/**
	 * Implementing the singleton design pattern. 
	 * @return The initial store object. 
	 */
	
	public static Store generateStoreInstance() {
      if(store == null) {
          store = new Store(100000, "SuperMart");
       }
       return store;
	}
	
	/**
	 * Generates stock based on initial item properties file. 
	 * 
	 */
	
	public void generateIntialStock() {
		for (Item item : Stock.getStockProperties()) {
			inventory.put(item, 0);
		}
	}
	
	/**
	 * Gets the inventory of the store
	 * @return The inventory of the store
	 */
	
	public Stock getStock() {
		return inventory;
	}
	
	/**
	 * Gets the amount of capital
	 * @return The numerical value of store capital
	 */
	public double getCapital() {
		return capital;
	}
	
	/**
	 * Gets the name of the store
	 * @return The name of the store
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets a printable version of the store's capital amount
	 * @return String representation of store capital
	 */
	public String getFormattedCapital() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Updates store data based on weekly sales log. Capital increases and inventory decreases.
	 * @param filePath - Absolute file path to file storing information about weekly sales
	 */
	public void loadSalesLog(String filePath) {
		HashMap<String, Integer> salesLog = CSVReading.readSalesLog(filePath);
		for (Map.Entry<String, Integer> sale : salesLog.entrySet()) {
			for (Map.Entry<Item, Integer> item : inventory.entrySet()) {
				if (item.getKey().getName() == sale.getKey()) {
					inventory.replace(item.getKey(), item.getValue() - sale.getValue()); // remove items from inventory
					capital += (item.getKey().getSellPrice() - item.getKey().getManufacturingCost()) * sale.getValue(); // update capital by profit
				}
			}
		}
	}
}
