package delivery;

import stock.Item;
import stock.Stock;

public class OrdinaryTruck extends Truck {
	
	/**
	 * The Ordinary Truck concrete class for Truck. 
	 */
	
	// Fields 
	private static final int maxCargo = 1000;
	
	/** 
	 * Constructor for Ordinary Truck. 
	 */
	
	public OrdinaryTruck(Stock truckStock) throws DeliveryException{
		super(truckStock);
	}
	
	/**
	 * Methods calculates the cost of delivery for a Ordinary Truck. 
	 * Accurate to two decimal places 
	 */

	@Override
	public double calculateCostOfDelivery() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public int getCargoQuantity() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void addCargo(int quantity, Item item) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Stock getCargo() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
