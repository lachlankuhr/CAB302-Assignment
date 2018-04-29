package delivery;

import java.util.AbstractCollection;

import stock.Item;

public abstract class Truck {
	
	/**
	 * An abstract class for the two truck types.
	 */
	
	// Fields 
	private AbstractCollection<Item> items;  // items held in cargo
	
	/** 
	 * Constructor for Truck. 
	 */
	
	public Truck() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/** 
	 * Calculates the cost of delivery for the truck. 
	 */
	
	public abstract void calculateCostOfDelivery(int cargoQuantity, Double temperature);
	
	/** 
	 * Gets the quantity of cargo in the truck.
	 * @return - The quantity of cargo in the truck. 
	 */
	
	public abstract int getCargoQuantity();
	
	/**
	 * Adds items to the truck. 
	 * @param quantity - The quantity of the item to add. 
	 * @param item - The item to add. 
	 */
	
	public abstract void addCargo(int quantity, Item item); 
	
	
	
	
}
