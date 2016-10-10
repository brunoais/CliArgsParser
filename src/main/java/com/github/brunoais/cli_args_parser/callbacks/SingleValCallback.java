package com.github.brunoais.cli_args_parser.callbacks;

@FunctionalInterface
public interface SingleValCallback extends ValCallback{
	default void c(String val1, String val2, String val3){
		callback(val3);
	}
	
	void callback(String val1);
}
