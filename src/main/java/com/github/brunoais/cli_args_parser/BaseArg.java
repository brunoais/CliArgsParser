package com.github.brunoais.cli_args_parser;

import com.github.brunoais.cli_args_parser.callbacks.DoubleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.NoValCallback;
import com.github.brunoais.cli_args_parser.callbacks.QuadrupleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.SingleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.TripleValCallback;
import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

abstract class BaseArg {

	String name;
	ArgParser registerTo;
	BaseArg attr;

	int multiplicity;
	boolean prefixed;
	boolean hasKey;
	boolean eqValue;
	ValCallback callback;

	BaseArg(String name, ArgParser registerTo) {
		this.name = name;
		this.registerTo = registerTo;
		this.multiplicity = 0;
		this.hasKey = false;
		this.eqValue = false;
	}
	
	BaseArg attr(BaseArg attr){
		this.attr = attr;
		return this;
	}
	
	BaseArg prefixes() {
		prefixed = true;
		return this;
	}
	
	BaseArg equalValue(){
		prefixed = true;
		eqValue = true;
		return this;
	}
	
	BaseArg keyEqualValue(){
		prefixes();
		hasKey = true;
		return equalValue();
	}

	BaseArg keySpaceValue(){
		return spaceValue();
	}
	
	BaseArg spaceValue(){
		multiplicity = 1;
		return this;
	}
	
	BaseArg spaceValued(int times){
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
		
		if(prefixed){
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
		if(!hasKey && prefixed){
			registerTo.appendEqValue(this);
		} else if(prefixed){
			registerTo.appendPrefixed(this);
		} else if(name == null || name.isEmpty()){
			registerTo.setDefault(this);
		} else {
			registerTo.appendNormal(this);
		}
	}
	
	public void call(NoValCallback callback){
		if(attr != null){
			attr.call(callback);
		}
		call((ValCallback) callback);
	}
	public void call(SingleValCallback callback){
		if(attr != null){
			attr.call(callback);
		}
		call((ValCallback) callback);
	}
	public void call(DoubleValCallback callback){
		if(attr != null){
			attr.call(callback);
		}
		call((ValCallback) callback);
	}
	public void call(TripleValCallback callback){
		if(attr != null){
			attr.call(callback);
		}
		call((ValCallback) callback);
	}
	public void call(QuadrupleValCallback callback){
		if(attr != null){
			attr.call(callback);
		}
		call((ValCallback) callback);
	}
	
	@Override
	public String toString() {
		String answer = 
				name +
				(prefixed? "<prefix>" : "") +
				(eqValue? (hasKey? "<key>" : "") + "=<value>" : "");
		
		for(int i = 0; i < multiplicity; i++){
			answer +=" <value>";
		}
		
		return answer;
	}
}
