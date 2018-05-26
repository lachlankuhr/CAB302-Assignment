package delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Refrigerated Truck class extended from base Truck class.
 * Can be used to transport items, regardless of required temperature control.
 * Truck cost is based on the temperature of its coldest item.
 * @author Atrey Gajjar
 */
public class RefrigeratedTruck extends Truck {
	
	
	//Maximum number of items it can store 
	private static final int MAX_CARGO = 800;
	//Tag used in reading/creating manifests
	public static final String MANIFEST_TAG = ">Refrigerated";
	
	/**
	 * Constructor for the refrigerated truck. Uses super constructor to initialise its stock.
	 * @author Atrey Gajjar
	 */
	public RefrigeratedTruck() {
		super();
	}
	
	/**
	 * Calculates the cost of delivery for a Refrigerated Truck, based on it's required temperature to carry the coldest item held. 
	 * @return The cost of delivery with this truck.
	 * @author Atrey Gajjar
	 */
	@Override
	public double calculateCostOfDelivery() {
		Double lowestTemperature = getCargo().findColdestItem().getTemperature();
		double cost = 900.0 + 200.0 * Math.pow(0.7, lowestTemperature/5.0);
		
		BigDecimal roundedCost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);	
		return roundedCost.doubleValue();
	}

	/**
	 * Get the string tag used to represent a refrigerated truck. Used to read and write the manifest
	 * @return String representing a refrigerated truck
	 * @author Atrey Gajjar
	 */
	@Override
	public String getManifestIdentification() {
		return MANIFEST_TAG;
	}
	
	/**
	 * Gets the maximum quantity of items a refrigerated truck can hold.
	 * @return The maximum quantity of cargo a refrigerated truck can hold.
	 * @author Atrey Gajjar
	 */
	@Override
	public int getMaxCargo() {
		return MAX_CARGO;
	}
}