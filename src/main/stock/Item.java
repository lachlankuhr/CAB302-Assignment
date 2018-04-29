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
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
