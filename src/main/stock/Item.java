package stock;

public class Item {
	
	/**
	 * An item with the below properties. 
	 */
	
	// Item properties / fields
	private String name; 
	private double manufacturingCost;
	private double sellPrice; 
	private int reorderPoint;
	private int reorderAmount; 
	private Double temperature; 
	
	/**
	 * The constructor for item. 
	 * @param name - The name of the item. 
	 * @param manufacturingCost - The manufacturing cost of the item. 
	 * @param sellPrice - The sell price of the item. 
	 * @param reorderPoint - The reorder point of the item. 
	 * @param reorderAmount - The reorder amount of the item. 
	 * @param temperature - The temperature of the item. Is a boxed object (i.e. class) and hence is null-able if no temperature is associated with the item. 
	 */
	
	public Item(String name, double manufacturingCost, double sellPrice, int reorderPoint, int reorderAmount, Double temperature) {
		this.name = name;
		this.manufacturingCost = manufacturingCost;
		this.sellPrice = sellPrice;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.temperature = temperature;
	}

	/**
	 * Gets the name of this item.
	 * @return The name of the item.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the cost price of this item for the store.
	 * @return The cost price of the item
	 */
	public double getManufacturingCost() {
		return manufacturingCost;
	}

	/**
	 * Gets the price this item is sold to customers at.
	 * @return The sell price of the item
	 */
	public double getSellPrice() {
		return sellPrice;
	}

	/**
	 * Gets the threshold level of stock, this item has to fall below to be reordered.
	 * @return The reorder point of the item
	 */
	public int getReorderPoint() {
		return reorderPoint;
	}

	/**
	 * Gets the quantity that is ordered, when this item is reordered.
	 * @return The reorder point of the item
	 */
	public int getReorderAmount() {
		return reorderAmount;
	}

	/**
	 * Gets the temperature level (in Celcius) this item needs to be kept under.
	 * Items that do not need temperature maintenance have a null value.
	 * @return The maximum temperature the item can be stored at.
	 */
	public Double getTemperature() {
		return temperature;
	}
}
