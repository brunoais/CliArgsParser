package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

abstract class ArgParser {

	abstract void appendNormal(BaseArg argument);
	abstract void appendPrefixed(BaseArg argument);
	abstract void appendEqValue(BaseArg argument);
	abstract void setDefault(BaseArg argument);
	
	abstract ValCallback unknownArgCallback(ValCallback notFoundArgument);
	
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
