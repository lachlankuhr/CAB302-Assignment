package stock;

import java.io.IOException;
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
	 * @author Lachlan Kuhr
	 */
	
	// Fields
	private double capital;
	private Stock inventory; 
	private String name; 
	private static Store store; 
	
	/**
	 * Implementing the singleton design pattern.
	 * @param capital The capital of the store initially (always $100,000 as specified by the singleton design pattern).
	 * @param name The name of the store. 
	 * @author Lachlan Kuhr
	 */
	
	private Store(double capital, String name) {
		this.capital = capital; 
		this.name = name;
		inventory = new Stock();
	}
	
	/**
	 * Implementing the singleton design pattern.
	 * If the store object already exists, return that object. 
	 * @return The initial store object. 
	 * @author Lachlan Kuhr
	 */
	
	public static Store generateStoreInstance() {
      if(store == null) {
          store = new Store(100000, "SuperMart");
       }
       return store; //If the store object already exists, return that object. 
	}
	
	/**
	 * Generates stock based on initial item properties file.
	 * @author Lachlan Kuhr
	 */
	
	public void generateInitialStock() {
		for (Item item : Stock.getStockProperties().values()) { 
			
			Item oldItemSaved = null; 
			// search the inventory for match of item
			for(Item oldItem: inventory.keySet()) {
				if(oldItem.getName().equals(item.getName())){
					oldItemSaved = oldItem;
					break;
				}
			}
			
			if(oldItemSaved != null) { // if item already exists in inventory, put value in
				inventory.put(item, inventory.get(oldItemSaved));
				inventory.remove(oldItemSaved);
			}else { // initial stock is set to 0 
				inventory.put(item, 0);
			}
		}
	}
	
	/**
	 * Gets the inventory of the store.
	 * @return The inventory of the store.
	 * @author Lachlan Kuhr
	 */
	
	public Stock getStock() {
		return inventory;
	}
	
	/**
	 * Gets the amount of capital.
	 * @return The numerical value of store capital.
	 * @author Lachlan Kuhr
	 */
	
	public double getCapital() {
		return capital;
	}
	
	/**
	 * Gets the name of the store.
	 * @return The name of the store.
	 * @author Lachlan Kuhr
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Gets a printable version of the store's capital amount.
	 * @return String representation of store capital.
	 * @author Lachlan Kuhr
	 */
	
	public String getFormattedCapital() {
		return NumberFormat.getCurrencyInstance().format(capital);
	}
	
	/**
	 * Updates store data based on weekly sales log. Capital increases and inventory decreases.
	 * @param filePath - File path to file storing information about weekly sales.
	 * @throws IOException Exception thrown upon file reading.
	 * @author Lachlan Kuhr
	 */
	
	public void loadSalesLog(String filePath) throws IOException {
		ArrayList<ArrayList<String>> salesLog = CSVReading.readCSV(filePath); // read in the sales log
		// process the sales log
		for (ArrayList<String> sale : salesLog) { 
			String itemName = sale.get(0); // index zero is the name 
			int saleAmount = Integer.parseInt(sale.get(1)); // index one is the sales amount
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
		for (Truck truck : manifest.getManifestCollection()) { // for each truck
			for (Map.Entry<Item, Integer> itemCargo : truck.getCargo().entrySet()) {  // for each item 
				addItemsToInventory(itemCargo.getKey(), itemCargo.getValue()); // add the items to the inventory
			}
		}
		capital -= manifest.calculateCostOfManifest(); // decrease the captial by the cost of the manifest
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
		// identify which items need to be reorder
		for(Item item : inventory.keySet()) { 
			if(item.getReorderPoint() >= inventory.get(item)) {
				reorderStock.put(item, item.getReorderAmount());
			}
		}
		// return items that 
		return reorderStock;
	}
	
	/** 
	 * Method to destroy store instance for fresh store. 
	 * @author Lachlan Kuhr
	 */
	
	public static void destoryStore() {
		store = null;
	}
	
}
