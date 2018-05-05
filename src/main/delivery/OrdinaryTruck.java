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
	
	public OrdinaryTruck() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Methods calculates the cost of delivery for a Ordinary Truck. 
	 * The temperature will null. 
	 */

	@Override
	public void calculateCostOfDelivery(int cargoQuantity, Double temperature) {
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
		// TODO Auto-generated method stub
		return null;
	}

}
