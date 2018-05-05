package stock;

@SuppressWarnings("serial")
public class StockException extends Exception {
	
	/**
	 * Constructs a StockException with null as its error message string.
	 */
	public StockException() {
		super();
	}
	
	/**
	 * Constructs a StockException with a particular error message string.
	 * 
	 * @param message a description of the exception's cause
	 */
	public StockException(String message) {
		super(message);
	}

}
