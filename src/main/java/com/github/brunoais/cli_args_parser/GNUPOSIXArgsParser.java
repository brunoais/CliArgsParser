package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

public class GNUPOSIXArgsParser extends ArgParser{

	private ArgParser parseArgs;

	GNUPOSIXArgsParser(ArgParser parseArgs) {
		this.parseArgs = parseArgs;
	}
	
	/**
	 * Short form of {@code argument()}
	 * Creates a new Argument to parse the default arguments
	 * @return A new Argument for the default arguments for configuration
	 */
	public GNUPOSIXArgument arg(){
		return argument();
	}
	/**
	 * Creates a new Argument to parse the default arguments
	 * @return A new Argument for the default arguments for configuration
	 */
	public GNUPOSIXArgument argument() {
		return new GNUPOSIXArgument(null, this);
	}

	/**
	 * Short form of {@code argument(String)}
	 * Creates a new Argument to parse input
	 * Provide a null name if it is a default argument 
	 * @param name The name of the argument
	 * @return A new Argument for this name for configuration
	 */
	public GNUPOSIXArgument arg(String name){
		return argument(name);
	}

	/**
	 * Creates a new Argument to parse input
	 * Provide a null name if it is a default argument 
	 * @param name The name of the argument
	 * @return A new Argument for this name for configuration
	 */
	public GNUPOSIXArgument argument(String name) {
		return new GNUPOSIXArgument(name, this);
	}

	/**
	 * Sets the callback for when an argument is included but no correspondence was found
	 * @param notFoundArgument The callback called if an unknown argument is found 
	 * @return the previous callback. Null otherwise
	 */
	public ValCallback unknownArgCallback(ValCallback notFoundArgument) {
		return parseArgs.unknownArgCallback(notFoundArgument);
	}

	public void parseArgs(String[] args) {
		parseArgs.parseArgs(args);
	}

	public void parseArgs(String[] args, int start) {
		parseArgs.parseArgs(args, start);
	}

	public void parseArgs(String[] args, int start, int end) throws ArrayIndexOutOfBoundsException {
		parseArgs.parseArgs(args, start, end);
	}

	@Override
	void appendNormal(BaseArg argument) {
		parseArgs.appendNormal(argument);
	}
	@Override
	void appendPrefixed(BaseArg argument) {
		parseArgs.appendPrefixed(argument);
	}
	@Override
	void appendEqValue(BaseArg argument) {
		parseArgs.appendEqValue(argument);
	}
	@Override
	void setDefault(BaseArg argument) {
		parseArgs.setDefault(argument);
	}

}
