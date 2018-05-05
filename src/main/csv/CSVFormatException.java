package csv;

@SuppressWarnings("serial")
public class CSVFormatException extends Exception {
	
	/**
	 * Constructs a CSVFormatException with null as its error message string.
	 */
	public CSVFormatException() {
		super();
	}
	
	/**
	 * Constructs a CSVFormatException with a particular error message string.
	 * 
	 * @param message a description of the exception's cause
	 */
	public CSVFormatException(String message) {
		super(message);
	}

}
