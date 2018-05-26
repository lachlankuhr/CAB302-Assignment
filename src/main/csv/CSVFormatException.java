package csv;

/**
 * StockException for exceptions relating to stocks. 
 * @author Lachlan Kuhr
 */

@SuppressWarnings("serial")
public class CSVFormatException extends Exception {
	
	/**
	 * Constructs a CSVFormatException with a particular error message string.
	 * @param message A description of the exception's cause
	 * @author Lachlan Kuhr
	 */
	public CSVFormatException(String message) {
		super(message);
	}

}
