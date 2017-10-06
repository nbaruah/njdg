package ghc.njdg.exeption;

public class ServiceBException extends Exception {
	
	private static final long serialVersionUID = -8131508111578374799L;

	public ServiceBException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceBException(String message) {
		super(message);
	}

	public ServiceBException(Throwable cause) {
		super(cause);
	}
	
	

}
