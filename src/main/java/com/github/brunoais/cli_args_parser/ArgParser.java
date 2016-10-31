package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

abstract class ArgParser<T> {

	abstract void appendNormal(BaseArg argument);
	abstract void appendPrefixed(BaseArg argument);
	abstract void appendEqValue(BaseArg argument);
	abstract void setDefault(BaseArg argument);
	
	abstract ValCallback unknownArgCallback(ValCallback notFoundArgument);
	
	/**
	 * Short form of {@code argument()}
	 * Creates a new Argument to parse the default arguments
	 * @return A new Argument for the default arguments for configuration
	 */
	public T arg(){
		return argument();
	}
	public abstract T argument();
	
	public T arg(String name){
		return argument(name);
	}
	public abstract T argument(String name);
	
	/**
	 * Parses the args array fully
	 * 
	 * @param args The args array to parse
	 */
	public void parseArgs(String[] args) {
		parseArgs(args, 0);
	}
	
	/**
	 * Parses the args array fully from start (inclusive)
	 *  
	 * @param args The args array to parse
	 * @param start The first index of args array to parse
	 */
	public void parseArgs(String[] args, int start) {
		parseArgs(args, start, args.length);
	}

	public abstract void parseArgs(String[] args, int start, int end) throws ArrayIndexOutOfBoundsException;
}
