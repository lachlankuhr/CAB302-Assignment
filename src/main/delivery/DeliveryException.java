package delivery;

/**
 * Exception class that is thrown when there is a problem importing a manifest file. A message is always provided to provide more information to the user, so they are able to rectify the issue.
 * @author Atrey Gajjar
 */
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
