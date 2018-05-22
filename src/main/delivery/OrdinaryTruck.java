package delivery;

public class OrdinaryTruck extends Truck {
	
	/**
	 * The Ordinary Truck class extended from base Truck class.
	 * Can be used to transport items, but no temperature controlled items.
	 * Truck cost is based on the amount of cargo it carries.
	 * @author Atrey Gajjar 
	 */
	
	//Maximum number of items it can store 
	private static final int MAX_CARGO = 1000;
	//Tag used in reading/creating manifests
	public static final String MANIFEST_TAG = ">Ordinary";
	
	/**
	 * Constructor for the ordinary truck. Uses super constructor, setting the correct manifest tag and maximum cargo amounts.
	 * @author Atrey Gajjar
	 */
	public OrdinaryTruck() {
		super();
	}
	
	/**
	 * Calculates the cost of delivery for a Ordinary Truck. 
	 * Accurate to two decimal places
	 * @return The cost of delivery with this truck 
	 * @author Atrey Gajjar
	 */
	@Override
	public double calculateCostOfDelivery() {
		return (750.0+0.25*getCargoQuantity());
	}

	/**
	 * Get the string tag used to represent the an ordinary truck. Used to read and write the manifest
	 * @return String representing an ordinary truck
	 * @author Atrey Gajjar
	 */
	@Override
	public String getManifestIdentification() {
		return MANIFEST_TAG;
	}
	
	/**
	 * Gets the maximum quantity of items an ordinary truck can hold.
	 * @return The maximum quantity of cargo an ordinary truck can hold.
	 * @author Atrey Gajjar
	 */
	@Override
	public int getMaxCargo() {
		return MAX_CARGO;
	}
}
