package delivery;

@SuppressWarnings("serial")
public class DeliveryException extends Exception {
	
	/**
	 * Constructs a DeliveryException with null as its error message string.
	 */
	public DeliveryException() {
		super();
	}
	
	/**
	 * Constructs a DeliveryException with a particular error message string.
	 * 
	 * @param message a description of the exception's cause
	 */
	public DeliveryException(String message) {
		super(message);
	}

}
