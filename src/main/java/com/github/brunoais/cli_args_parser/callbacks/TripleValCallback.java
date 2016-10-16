package com.github.brunoais.cli_args_parser.callbacks;

@FunctionalInterface
public interface TripleValCallback extends ValCallback{
	default void c(String val1, String val2, String val3){
		callback(val1, val2, val3);
	}
	
	void callback(String val1, String val2, String val3);
}