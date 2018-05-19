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
	
	public void generateInitialStock() {
		for (Item item : Stock.getStockProperties().values()) {
			
			Item oldItemSaved = null;
			for(Item oldItem: inventory.keySet()) {
				if(oldItem.getName().equals(item.getName())){
					oldItemSaved = oldItem;
					break;
				}
			}
			
			if(oldItemSaved != null) {
				inventory.put(item, inventory.get(oldItemSaved));
				inventory.remove(oldItemSaved);
			}else {				
				inventory.put(item, 0);
			}
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
		ArrayList<ArrayList<String>> salesLog = CSVReading.readCSV(filePath);
		for (ArrayList<String> sale : salesLog) {
			String itemName = sale.get(0);
			int saleAmount = Integer.parseInt(sale.get(1));
			Item item = Stock.getItem(itemName); 
			inventory.replace(item, inventory.get(item) - saleAmount); // remove items from inventory
			capital += item.getSellPrice() * saleAmount; // update capital by profit
		}
	}
	
	/**
	 * Loads in a manifest file to simulate delivery of items. Capital decreases and inventory increases.
	 * @param manifest 
	 */
	public void importManifest(Manifest manifest) {
		for (Truck truck : manifest.getManifestCollection()) {
			for (Map.Entry<Item, Integer> itemCargo : truck.getCargo().entrySet()) {
				addItemsToInventory(itemCargo.getKey(), itemCargo.getValue());
			}
		}
		capital -= manifest.calculateCostOfManifest(); 
	}
	
	/**
	 * Adds items to current inventory.
	 * @param item - Item to be added. 
	 * @param quantity - The quantity of the item to be added. 
	 */
	
	public void addItemsToInventory(Item item, int quantity) {
		inventory.replace(item, inventory.get(item) + quantity);
	}
	
	/**
	 * Reorders item from stock.
	 * @return Stock
	 * @author Lachlan Kuhr
	 */
	
	public Stock getReorderStock() {
		Stock reorderStock = new Stock();
		for(Item item : inventory.keySet()) {
			if(item.getReorderPoint() >= inventory.get(item)) {
				reorderStock.put(item, item.getReorderAmount());
			}
		}
		return reorderStock;
	}
	
	/** 
	 * Method to destroy store instance for fresh store. 
	 */
	
	public static void destoryStore() {
		store = null;
	}
	
}
