package delivery;

@SuppressWarnings("serial")
public class DeliveryException extends Exception {
	
	/**
	 * Constructs a DeliveryException with a particular error message string.
	 * @param message - A message describing the cause of the exception.
	 * @author Atrey Gajjar
	 */
	public DeliveryException(String message) {
		super(message);
	}

}
