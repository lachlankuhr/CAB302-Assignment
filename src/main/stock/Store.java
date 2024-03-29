package stock;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import csv.CSVFormatException;
import csv.CSVReading;
import delivery.Manifest;
import delivery.Truck;

/**
 * An object for representing the store itself.
 * Implements the singleton design pattern.  
 * @author Lachlan Kuhr
 *
 */

public class Store {
	
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
		
		Stock newInventory = new Stock();
		
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
				newInventory.put(item, inventory.get(oldItemSaved));
			}else { // initial stock is set to 0 
				newInventory.put(item, 0);
			}
		}
		
		inventory.clear();
		inventory.putAll(newInventory);
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
	 * @throws CSVFormatException When the format of the csv differs to that expected for a sales log.
	 * @throws StockException Thrown when there is either an unknown item, more items have been sold than in stock, or unable to parse values.
	 * @author Lachlan Kuhr
	 */
	public void loadSalesLog(String filePath) throws IOException, StockException, CSVFormatException {
		ArrayList<ArrayList<String>> salesLog = CSVReading.readCSV(filePath); // read in the sales log
		// process the sales log
		
		for (ArrayList<String> sale : salesLog) { 
			// check file format
			if (sale.size() !=2) {
				throw new CSVFormatException("File does not match required format. Check sales log file.");
			}
			// to ensure that both item name and quantity exists
			if (sale.get(0).isEmpty()  || sale.get(1).isEmpty()) {
				throw new StockException("Missing item name or quantity. Check sales log file.");
			}
			String itemName;
			if (Stock.getItem(sale.get(0)) != null) {
				itemName = sale.get(0); // index zero is the name 
			} else { // to ensure there is an object representation of the item name
				throw new StockException("There was an unknown item sold. Check sales log file.");
			}
			int saleAmount;
			try {
				Integer.parseInt(sale.get(1));
			} catch (Exception e) { // 
				throw new StockException("Unable to parse sale quantities. Check sales log file.");
			}
			
			if (Integer.parseInt(sale.get(1)) >= 0) {
				saleAmount = Integer.parseInt(sale.get(1)); // index one is the sales amount
			} else { // the quantity is negative 
				throw new StockException("There was a negative number of items sold. Check sales log file.");
			}
			
			Item item = Stock.getItem(itemName); 
			if (inventory.get(item) < saleAmount) {
				throw new StockException("Sold more quantity than in the store. Check sales log file.");
			} 
			inventory.replace(item, inventory.get(item) - saleAmount); // remove items from inventory
			capital += item.getSellPrice() * saleAmount; // update capital by profit
		}
	}
	
	/**
	 * Loads in a manifest file to simulate delivery of items. Capital decreases and inventory increases.
	 * @param manifest The manifest to be 
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
	 * @param item Item to be added. 
	 * @param quantity The quantity of the item to be added. 
	 */
	public void addItemsToInventory(Item item, int quantity) {
		inventory.replace(item, inventory.get(item) + quantity);
	}
	
	/**
	 * Reorders item from stock given the reorder level and reorder amounts. 
	 * @return Stock The reorder stock. 
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
	 * Used when changing store in same session of GUI (due to the use of the singleton design pattern). 
	 * @author Lachlan Kuhr
	 */
	public static void destoryStore() {
		store = null;
	}
	
}
