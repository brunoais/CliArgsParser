package com.github.brunoais.cli_args_parser.callbacks;

public interface ValCallback {
	void c(String val1, String val2, String val3);
	default void c(String val1, String val2, String val3, String val4){
		c(val2, val3, val4);
	}
}
