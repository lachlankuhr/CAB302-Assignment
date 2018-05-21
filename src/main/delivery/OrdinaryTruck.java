package delivery;

import stock.Item;
import stock.Stock;

public class OrdinaryTruck extends Truck {
	
	/**
	 * The Ordinary Truck concrete class for Truck. 
	 */
	
	// Fields 
	private static final int MAX_CARGO = 1000;
	
	public OrdinaryTruck() {
		super(MAX_CARGO);
	}
	
	/**
	 * Methods calculates the cost of delivery for a Ordinary Truck. 
	 * Accurate to two decimal places 
	 */

	@Override
	public double calculateCostOfDelivery() {
		return (750.0+0.25*getCargoQuantity());
	}

	private boolean coldItemCheck() {
		Stock stock = getCargo();
		boolean coldItem = false;
		
		for(Item item: stock.keySet()) {
			if(item.getTemperature() != null) {
				coldItem = true;
			}
		}
		
		return coldItem;
	}
	@Override
	public String getManifestIdentification() {
		return ">Ordinary";
	}
}
