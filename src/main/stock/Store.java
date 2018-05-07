package stock;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import csv.CSVReading;
import delivery.Manifest;
import delivery.Truck;

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
			inventory.put(item, 100);
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
		return NumberFormat.getCurrencyInstance().format(capital);
	}
	
	/**
	 * Updates store data based on weekly sales log. Capital increases and inventory decreases.
	 * @param filePath - File path to file storing information about weekly sales
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
	
	/**
	 * Loads in a manifest file to simulate delivery of items. Capital decreases and inventory increases.
	 * @param manifest 
	 */
	public void importManifest(Manifest manifest) {
		boolean refrigTruck;
		int cargoQuantity;
		Double lowestTemp = null;
		double cost = 0;
		for (Truck truck : manifest.getManifestCollection()) {
			lowestTemp = null;
			refrigTruck = false;
			cargoQuantity = 0;
			for (Map.Entry<Item, Integer> itemCargo : truck.getCargo().entrySet()) {
				if (itemCargo.getKey().getTemperature() == null) {
					addItemsToInventory(itemCargo.getKey(), itemCargo.getValue());
					cargoQuantity += itemCargo.getValue();
				} else {
					refrigTruck = true;
					if (itemCargo.getKey().getTemperature() < lowestTemp || lowestTemp == null) {
						lowestTemp = itemCargo.getKey().getTemperature(); 
					}
					addItemsToInventory(itemCargo.getKey(), itemCargo.getValue());
				}
			}
			if (refrigTruck) {
				cost += 900 + 200 * Math.pow(0.7, lowestTemp / 5);
			} else {
				cost += 750 + 0.25 * cargoQuantity;
			}
		}
		
		capital -= cost; 
		
	}
	
	/**
	 * Adds items to current inventory.
	 * @param item - Item to be added. 
	 * @param quantity - The quantity of the item to be added. 
	 */
	
	public void addItemsToInventory(Item item, int quantity) {
		inventory.replace(item, inventory.get(item) + quantity);
	}
	
}
