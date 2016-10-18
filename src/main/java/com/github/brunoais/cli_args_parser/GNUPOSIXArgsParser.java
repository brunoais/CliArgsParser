package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

public class GNUPOSIXArgsParser extends ArgParser{

	private ArgParser parseArgs;

	GNUPOSIXArgsParser(ArgParser parseArgs) {
		this.parseArgs = parseArgs;
	}

	public GNUPOSIXArgument argument() {
		return new GNUPOSIXArgument(null, this);
	}

	public GNUPOSIXArgument argument(String name) {
		return new GNUPOSIXArgument(name, this);
	}

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
