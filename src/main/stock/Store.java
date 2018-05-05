package stock;

import java.util.ArrayList;

import csv.CSVReading;

public class Store {
	
	/*
	public static void main(String[] args) {
		Store store = Store.generateStoreInstance();
		store.loadInItemProperties("C:\\Temp\\CAB302\\item_properties.csv");
		for (Item item : properties) {
			System.out.println(item.getName());
		}
	}
	*/
	
	/**
	 * An object for representing the store itself.
	 */
	
	// Fields
	private double capital;
	private Stock<Item> inventory; 
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
	
	
	public void generateIntialStock() {
		
		for (Item item : Stock.getStockProperties()) {
			inventory.put(item, 0);
		}

	}
	
	public ArrayList<Item> getProperties() {
		return properties; 
	}

	public Stock getStock() {
		return inventory;
	}
	
}
