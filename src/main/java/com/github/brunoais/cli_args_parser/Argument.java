package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.DoubleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.NoValCallback;
import com.github.brunoais.cli_args_parser.callbacks.QuadrupleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.SingleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.TripleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

public class Argument {

	String name;
	private ParseArgs registerTo;

	static final String KEY_VALUE_SEPARATOR = "=";
	String searchSuffix;
	
	int multiplicity;
	boolean nameIsPrefix;
	boolean hasKey;
	boolean eqValue;
	ValCallback callback;

	Argument(String name, ParseArgs registerTo) {
		this.name = name;
		this.registerTo = registerTo;
		this.multiplicity = 0;
		this.hasKey = false;
		this.eqValue = false;
		searchSuffix = KEY_VALUE_SEPARATOR;
	}
	
	public Argument prefixes() {
		nameIsPrefix = true;
		searchSuffix = "";
		return this;
	}
	
	public Argument equalValue(){
		eqValue = true;
		return this;
	}
	
	public Argument keyEqualValue(){
		hasKey = true;
		searchSuffix = "";
		return equalValue();
	}

	public Argument keySpaceValue(){
		return spaceValue();
	}
	
	public Argument spaceValue(){
		multiplicity = 1;
		return this;
	}
	
	public Argument spaceValued(int times){
		multiplicity = times;
		return this;
	}
	
	void found(String value){
		callback.c("", "", value);
	}
	
	int parse(String[] args, int startNum){
		int workingNum = startNum;
		String workingArg = args[workingNum];
		String key = workingArg;
		String keyValueValue = "";
		
		if(nameIsPrefix || hasKey){
			key = workingArg.substring(name.length());
		}
		
		if(eqValue){
			String[] key_value = key.split("=", 2);
			key = key_value[0];
			keyValueValue = key_value[1];
		}

		workingNum += 1;
		
		if(multiplicity == 0){
			callback.c(name, key, keyValueValue);
		} else {
			int getUntil = startNum + multiplicity + 1;
			for (; workingNum < getUntil; workingNum++) {
				if(eqValue){
					callback.c(name, key, keyValueValue, args[workingNum]);
				} else {
					callback.c(name, key, args[workingNum]);
				}
			}
		}
		return workingNum;
	}
	
	void call(ValCallback callback){
		this.callback = callback;
		if(nameIsPrefix || eqValue){
			registerTo.appendPrefixed(this);
		} else if(name == null || name.isEmpty()){
			registerTo.setDefault(this);
		} else {
			registerTo.appendNormal(this);
		}
	}
	
	public void call(NoValCallback callback){
		call((ValCallback) callback);
	}
	public void call(SingleValCallback callback){
		call((ValCallback) callback);
	}
	public void call(DoubleValCallback callback){
		call((ValCallback) callback);
	}
	public void call(TripleValCallback callback){
		call((ValCallback) callback);
	}
	public void call(QuadrupleValCallback callback){
		call((ValCallback) callback);
	}
	
	@Override
	public String toString() {
		String answer = 
				name +
				(nameIsPrefix? "<prefix>" : "") +
				(eqValue? (hasKey? "<key>" : "") + "=<value>" : "");
		
		for(int i = 0; i < multiplicity; i++){
			answer +=" <value>";
		}
		
		return answer;
	}
}
