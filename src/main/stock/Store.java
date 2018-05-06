package stock;

import java.text.NumberFormat;
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
	 * @param filePath - File path to file storing manifest
	 * @throws StockException 
	 */
	// Potentially some of the worst logic I've ever come up with.
	// Verbose commenting for this reason. 
	public void importManifest(String filePath) throws StockException {
		ArrayList<Item> itemProperties = Stock.getStockProperties();
		double cost = 0;
		int cargoQuantity = 0; 
		Double lowestTemp = null;
		String truckType = "";
		Item currentItem = null;
		ArrayList<ArrayList<String>> manifest = CSVReading.readManifest(filePath);
		for (ArrayList<String> manifestRow : manifest) {
			
			// If there's only one attribute in the row, it's the
			// truck type
			if (manifestRow.size() == 1) {
				truckType = manifestRow.get(0); // get the truck type
				continue; // we can continue after this
			}
			
			// Find the current item being read in the items properties list
			// Should always assign 
			for (Item item : itemProperties) {
				if (manifestRow.get(0) == item.getName()) {
					currentItem = item; // set currentItem to the item identified
				} else {
					throw new StockException("Couldn't locate item from manifesting being loaded in.");
				}
			}
			
			// Process logic for each truck type
			if (truckType == ">Ordinary") {
				cargoQuantity += Integer.parseInt(manifestRow.get(1)); // ordinary truck cost is only 
				// dependent on cargo quantity 
				addItemsToInventory(currentItem, Integer.parseInt(manifestRow.get(1))); // add items to inventory
			} else if (truckType == ">>Refrigerated") {
				// Set temperature to first occurrence of a temperature
				// This is done so that further comparisons can be made to detect lower temperatures. 
				if (lowestTemp == null && currentItem.getTemperature() != null) {
					lowestTemp = currentItem.getTemperature();
				}
				// Test if it's a lower temperature 
				// Should never be entered immediately after the previous if statement
				if (currentItem.getTemperature() < lowestTemp) {
					lowestTemp = currentItem.getTemperature(); 
				}
				addItemsToInventory(currentItem, Integer.parseInt(manifestRow.get(1))); // add items to inventory
			}
				
			}
		
		// Process the capital reduction logic 
		if (truckType == ">Ordinary") {
			capital -= 750 + 0.25 * cargoQuantity;
		} else if (truckType == ">>Refrigerated") {
			capital -= 900 + 200 * Math.pow(0.7, cargoQuantity / 5);
		}
		
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
