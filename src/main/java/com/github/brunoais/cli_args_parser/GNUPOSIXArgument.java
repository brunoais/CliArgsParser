package com.github.brunoais.cli_args_parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GNUPOSIXArgument extends BaseArg{
	
	static final Pattern ARGUMENT_VALIDATOR = Pattern.compile("^((-[a-z0-9])|([a-z0-9])|(--[a-z][a-z-]+)|([a-z][a-z0-9-]+))$", Pattern.CASE_INSENSITIVE);
	
	boolean isShort = false;
	boolean gaveStrAttr = false;
	
	GNUPOSIXArgument(String name, ArgParser registerTo) {
		super(name, registerTo);
		if(name == null){
			return;
		}
		
		Matcher matched = ARGUMENT_VALIDATOR.matcher(name);
		
		if(!matched.find()){
			throw new IllegalGNUPOSIXArgument(name);
		}
		
		if(matched.group(1) != null){
			isShort = true;
		} else if(matched.group(2) != null){
			name = "-" + name;
			isShort = true;
		} else if(matched.group(3) != null){
			isShort = false;
		} else if(matched.group(4) != null){
			name = "--" + name;
		} else {
			throw new IllegalGNUPOSIXArgument(name);
		}
		
	}
	
	public GNUPOSIXArgument attr(BaseArg attr){
		super.attr(attr);
		return this;
	}
	
	public GNUPOSIXArgument attr(String attr){
		gaveStrAttr = true;
		super.attr(new GNUPOSIXArgument(attr, registerTo));
		return this;
	}

	@Override
	public GNUPOSIXArgument equalValue() {
		if(gaveStrAttr){
			attr.spaceValue();
		}
		super.equalValue();
		return this;
	}

	@Override
	public GNUPOSIXArgument spaceValue() {
		super.spaceValue();
		return this;
	}

}
