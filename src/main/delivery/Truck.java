package delivery;

import stock.Item;
import stock.Stock;

public abstract class Truck {
	
	/**
	 * An abstract class to base OrdinaryTruck and RefrigeratedTruck from.
	 * @author Atrey Gajjar
	 */
	
	// Stock object storing items truck is carrying
	private Stock truckStock;
	private int maxCargo;
	private String manifestIdentification;
	
	public Truck(int maxCargo, String manifestIdentification) {
		truckStock = new Stock();
		this.maxCargo = maxCargo;
		this.manifestIdentification = manifestIdentification;
	}
	
	/** 
	 * Constructor for Truck. Sets up a new stock object to associate with the truck.
	 * @author Atrey Gajjar
	 */
	
	public Truck(Stock truckStock) {
		this.truckStock = truckStock;
	}
	
	/** 
	 * Calculates the cost of delivery for the truck. 
	 * Accurate to two decimal places. 
	 * @return The cost of delivery of a truck
	 * @author Atrey Gajjar
	 * @throws DeliveryException 
	 */
	
	public abstract double calculateCostOfDelivery();
		
	/**
	 * Adds items to the truck. 
	 * @param quantity - The quantity of the item to add. 
	 * @param item - The item to add. 
	 * @throws DeliveryException 
	 * @author Atrey Gajjar
	 */
	
	public void addCargo(int quantity, Item item) throws DeliveryException{
		if (this.getCargo().containsKey(item)) {
			int currentQuantity = this.getCargo().get(item);
			
			if(currentQuantity + quantity > maxCargo) {
				throw new DeliveryException("Adding too much cargo to truck");
			}
			
			this.getCargo().replace(item, currentQuantity + quantity);
		} else {
			this.getCargo().put(item, quantity);
		}
	}
	
	/**
	 * Gets the cargo on the truck. 
	 * @return The cargo on the truck.
	 * @author Atrey Gajjar 
	 */
	public Stock getCargo() {
		return truckStock;
	}
	
	/**
	 * Gets the maximum quantity of items the truck can hold.
	 * @return The maximum quantity of cargo the type of truck can hold.
	 * @author Atrey Gajjar
	 */
	public int getMaxCargo() {
		return maxCargo;
	}
	
	/** 
	 * Gets the quantity of cargo in the truck.
	 * @return The quantity of cargo in the truck.
	 * @author Atrey Gajjar 
	 */
	public int getCargoQuantity() {
		return getCargo().getStockQuantity();
	}
	
	/**
	 * Get the string tag used to represent the type of truck. Used to read and write the manifest
	 * @return String representing the type of truck
	 * @author Atrey Gajjar
	 */
	public String getManifestIdentification() {
		return manifestIdentification;
	}
	
	/**
	 * Tests whether the truck contains a cold item. Used in testing exceptions
	 * @return True if a truck holds a temperature controlled item, false otherwise
	 */
	public boolean coldItemCheck() {
		Stock stock = getCargo();
		boolean coldItem = false;
		
		for(Item item: stock.keySet()) {
			if(item.getTemperature() != null) {
				coldItem = true;
			}
		}
		
		return coldItem;
	}
}
