package com.github.brunoais.cli_args_parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.brunoais.cli_args_parser.callbacks.DoubleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.NoValCallback;
import com.github.brunoais.cli_args_parser.callbacks.QuadrupleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.SingleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.TripleValCallback;

class GNUPOSIXArgument implements GNUPOSIXArg{
	
	static final Pattern ARGUMENT_VALIDATOR = Pattern.compile("^((-[a-z0-9])|([a-z0-9])|(--[a-z][a-z-]+)|([a-z][a-z0-9-]+))$", Pattern.CASE_INSENSITIVE);
	
	boolean isShort;

	private Argument argument;
	
	GNUPOSIXArgument(String name, ArgParser registerTo) {
		
		Matcher matched = ARGUMENT_VALIDATOR.matcher(name);
		
		if(!matched.find()){
			throw new IllegalGNUPOSIXArgument(name);
		}
		
		String argument;
		if((argument = matched.group(1)) != null){
			isShort = true;
		} else if((argument = matched.group(2)) != null){
			name = "-" + name;
			isShort = true;
		} else if((argument = matched.group(3)) != null){
			isShort = false;
		} else if((argument = matched.group(4)) != null){
			name = "--" + name;
		} else {
			throw new IllegalGNUPOSIXArgument(name);
		}
		
		this.argument = new Argument(name, registerTo);
	}

	@Override
	public GNUPOSIXArg equalValue() {
		return argument.equalValue();
	}

	@Override
	public GNUPOSIXArg spaceValue() {
		return argument.spaceValue();
	}

	@Override
	public void call(NoValCallback callback) {
		argument.call(callback);
	}

	@Override
	public void call(SingleValCallback callback) {
		argument.call(callback);
	}

	@Override
	public void call(DoubleValCallback callback) {
		argument.call(callback);
	}

	@Override
	public void call(TripleValCallback callback) {
		argument.call(callback);
	}

	@Override
	public void call(QuadrupleValCallback callback) {
		argument.call(callback);
	}
}
