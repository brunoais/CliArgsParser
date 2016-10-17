package com.github.brunoais.cli_args_parser;

interface ArgParser {

	void appendEqValue(BaseArg argument);
	void appendPrefixed(BaseArg argument);
	void setDefault(BaseArg argument);
	void appendNormal(BaseArg argument);

}
