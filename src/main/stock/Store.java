package stock;

public class Store {
	
	/**
	 * An object for representing the store itself.
	 */
	
	// Fields
	private double capital;
	private Stock inventory; 
	private String name; 
	
	/**
	 * Implementing the singleton design pattern. 
	 * @param capital
	 * @param name
	 */
	
	private Store(double capital, String name) {
		this.capital = capital; 
		this.name = name;
	}
	
	/**
	 * Implementing the singleton design pattern. 
	 * @return The initial store object. 
	 */
	
	public Store generateStoreInstance() {
		return new Store(100000, "SuperMart");
	}

}
