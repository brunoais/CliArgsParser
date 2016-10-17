package com.github.brunoais.cli_args_parser;

public class Argument extends BaseArg {

	Argument(String name, ArgParser registerTo) {
		super(name, registerTo);
	}
	
	public Argument attr(BaseArg attr){
		super.attr(attr);
		return this;
	}

	@Override
	public Argument prefixes() {
		super.prefixes();
		return this;
	}

	@Override
	public Argument equalValue() {
		super.equalValue();
		return this;
	}

	@Override
	public Argument keyEqualValue() {
		super.keyEqualValue();
		return this;
	}

	@Override
	public Argument keySpaceValue() {
		super.keySpaceValue();
		return this;
	}

	@Override
	public Argument spaceValue() {
		super.spaceValue();
		return this;
	}

	@Override
	public Argument spaceValued(int times) {
		super.spaceValued(times);
		return this;
	}

}
