package delivery;

import java.util.ArrayList;

import stock.Item;
import stock.Stock;

public abstract class Truck {
	
	/**
	 * An abstract class for the two truck types.
	 */
	
	// Fields 
	private Stock truckStock;
	
	public Truck() {
		truckStock = new Stock();
	}
	
	/** 
	 * Constructor for Truck. 
	 */
	
	public Truck(Stock truckStock) {
		this.truckStock = truckStock;
	}
	
	/** 
	 * Calculates the cost of delivery for the truck. 
	 * Accurate to two decimal places. 
	 */
	
	public abstract double calculateCostOfDelivery();
		
	/**
	 * Adds items to the truck. 
	 * @param quantity - The quantity of the item to add. 
	 * @param item - The item to add. 
	 */
	
	public abstract void addCargo(int quantity, Item item); 
	
	/**
	 * Gets the cargo on the truck. 
	 * @return The cargo on the truck. 
	 */
	public Stock getCargo() {
		return truckStock;
	}
	
	/** 
	 * Gets the quantity of cargo in the truck.
	 * @return - The quantity of cargo in the truck. 
	 */
	public int getCargoQuantity() {
		return getCargo().getStockQuantity();
	}
	
	public abstract String getManifestIdentification();
	
}
