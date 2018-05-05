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
	
	public RefrigeratedTruck(Stock truckStock) throws DeliveryException {
		super(truckStock);
	}
	
	/**
	 * Methods calculates the cost of delivery for a Refrigerated Truck. 
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
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
