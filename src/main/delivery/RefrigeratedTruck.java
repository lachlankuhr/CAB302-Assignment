package delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;

import stock.Item;
import stock.Stock;

public class RefrigeratedTruck extends Truck {
	
	/**
	 * The Refrigerated Truck concrete class for Truck. 
	 */
	
	// Fields 
	private static final int MAX_CARGO = 800;
	
	public RefrigeratedTruck() {
		super();
	}
	
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
		Double lowestTemperature = findLowestTemperature();
		lowestTemperature = (lowestTemperature == null) ? 0 : lowestTemperature;
		double cost = 900.0 + 200.0 * Math.pow(0.7, lowestTemperature/5.0);
		
		BigDecimal roundedCost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);	
		return roundedCost.doubleValue();
	}
	

	@Override
	public void addCargo(int quantity, Item item) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	private Double findLowestTemperature() {
		Stock stock = getCargo();
		Double lowestTemp = null;
		for(Item item: stock.keySet()) {
			Double itemTemp = item.getTemperature();
			if(itemTemp == null) {
				continue;
			}
			if(lowestTemp == null) {
				lowestTemp = itemTemp;
			}else if(itemTemp < lowestTemp) {
				lowestTemp = itemTemp;
			}
		}
		return lowestTemp;
	}

	@Override
	public String getManifestIdentification() {
		return ">Refrigerated";
	}

}
