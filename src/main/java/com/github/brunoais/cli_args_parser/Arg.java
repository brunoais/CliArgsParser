package com.github.brunoais.cli_args_parser;

public interface Arg extends GNUPOSIXArg, BaseArg {

	Arg prefixes();

	Arg equalValue();

	Arg keyEqualValue();

	Arg keySpaceValue();

	Arg spaceValue();

	Arg spaceValued(int times);
}
