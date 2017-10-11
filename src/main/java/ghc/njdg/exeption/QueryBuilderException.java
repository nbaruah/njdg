package ghc.njdg.exeption;

public class QueryBuilderException extends Exception{
	private static final long serialVersionUID = 3841105970978400302L;

	public QueryBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueryBuilderException(String message) {
		super(message);
	}

	public QueryBuilderException(Throwable cause) {
		super(cause);
	}
	
	
}
