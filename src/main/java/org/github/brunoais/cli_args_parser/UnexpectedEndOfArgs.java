package org.github.brunoais.cli_args_parser;

/**
 * This exception represents an unexpected input where the input ended too early 
 */
public class UnexpectedEndOfArgs extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String argName;

	UnexpectedEndOfArgs(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	UnexpectedEndOfArgs(String message, Throwable cause) {
		super(message, cause);
	}

	UnexpectedEndOfArgs(String message) {
		super(message);
	}

	UnexpectedEndOfArgs(Throwable cause) {
		super(cause);
	}

	UnexpectedEndOfArgs(String message, Throwable cause, String argName) {
		super(message, cause);
		this.argName = argName;
	}

	/**
	 * @return The argument that was being parsed while the unexpected end happened
	 */
	public String incompleteArgument() {
		return argName;
	}
}
