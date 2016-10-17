package com.github.brunoais.cli_args_parser;

public class IllegalGNUPOSIXArgument extends RuntimeException {

	static String formatException(String arg){
		return "The argument '" + arg + "' is not a GNU/POSIX valid argument.\n" +
				"See: http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html";
	}
	
	IllegalGNUPOSIXArgument(String message, Throwable ex) {
		super(message, ex);
	}

	IllegalGNUPOSIXArgument(String message) {
		super(message);
	}

	IllegalGNUPOSIXArgument(Throwable ex) {
		super(ex);
	}

}
