package ghc.njdg.exeption;

public class WebServiceProcessException extends Exception{
	private static final long serialVersionUID = 5233360082959159265L;

	public WebServiceProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebServiceProcessException(String message) {
		super(message);
	}

	public WebServiceProcessException(Throwable cause) {
		super(cause);
	}
	
	
	
}
