package stock;

import java.util.AbstractCollection;

public class Stock<T> {
	/**
	 * A collection of items. Can be used for representing store inventory,
	 * stock orders, sales logs, and truck cargo.
	 */
	
	// Collect of stock of generic type T. 
	private AbstractCollection<T> stock; // List of trucks 
	
	/** 
	 * Constructor for the stock object. 
	 * @param stock - the private field holding the collection of stock items. 
	 */
	
	public Stock(AbstractCollection<T> stock) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}




