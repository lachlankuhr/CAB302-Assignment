package delivery;

import stock.Item;
import stock.Stock;

public class RefrigeratedTruck extends Truck {
	
	/**
	 * The Refrigerated Truck concrete class for Truck. 
	 */
	
	// Fields 
	private static final int maxCargo = 800;
	
	/** 
	 * Constructor for Refrigerated Truck. 
	 */
	
	public RefrigeratedTruck() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Methods calculates the cost of delivery for a Refrigerated Truck. 
	 * The temperature will be present in this calculation. 
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
