package pt.brunoais.cli_args_parser;

public class UnexpectedEndOfArgs extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String argName;

	public UnexpectedEndOfArgs(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnexpectedEndOfArgs(String message, Throwable cause) {
		super(message, cause);
	}

	public UnexpectedEndOfArgs(String message) {
		super(message);
	}

	public UnexpectedEndOfArgs(Throwable cause) {
		super(cause);
	}

	public UnexpectedEndOfArgs(String message, Throwable cause, String argName) {
		super(message, cause);
		this.argName = argName;
	}
	
	public String incompleteArgument() {
		return argName;
	}

}
