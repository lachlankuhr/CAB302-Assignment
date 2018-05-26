package stock;

/**
 * StockException for exceptions relating to stocks. 
 * @author Lachlan Kuhr
 */

@SuppressWarnings("serial")
public class StockException extends Exception {
	/**
	 * Constructs a StockException with a particular error message string.
	 * 
	 * @param message a description of the exception's cause
	 * @author Lachlan Kuhr
	 */
	public StockException(String message) {
		super(message);
	}

}
