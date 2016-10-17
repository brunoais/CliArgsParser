package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.DoubleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.NoValCallback;
import com.github.brunoais.cli_args_parser.callbacks.QuadrupleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.SingleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.TripleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

interface BaseArg {

	void call(NoValCallback callback);
	void call(SingleValCallback callback);
	void call(DoubleValCallback callback);
	void call(TripleValCallback callback);
	void call(QuadrupleValCallback callback);
}
