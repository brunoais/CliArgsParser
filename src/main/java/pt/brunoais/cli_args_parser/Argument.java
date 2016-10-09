package pt.brunoais.cli_args_parser;

import pt.brunoais.cli_args_parser.callbacks.DoubleValCallback;
import pt.brunoais.cli_args_parser.callbacks.NoValCallback;
import pt.brunoais.cli_args_parser.callbacks.QuadrupleValCallback;
import pt.brunoais.cli_args_parser.callbacks.SingleValCallback;
import pt.brunoais.cli_args_parser.callbacks.TripleValCallback;
import pt.brunoais.cli_args_parser.callbacks.ValCallback;

public class Argument {

	String name;
	private ParseArgs registerTo;

	private int multiplicity;
	private boolean nameIsPrefix;
	private boolean keyEqValue;
	private ValCallback callback;

	Argument(String name, ParseArgs registerTo) {
		this.name = name;
		this.registerTo = registerTo;
		this.multiplicity = 0;
	}
	
	public Argument prefixes() {
		nameIsPrefix = true;
		return this;
	}
	
	public Argument equalValue(){
		return keyEqualValue();
	}
	
	public Argument keyEqualValue(){
		keyEqValue = true;
		nameIsPrefix = true;
		return this;
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
		
		if(nameIsPrefix){
			key = workingArg.substring(name.length());
		}
		if(keyEqValue){
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
				if(keyEqValue){
					callback.c(name, key, keyValueValue, args[workingNum]);
				} else {
					callback.c(name, key, args[workingNum]);
				}
			}
		}
		return workingNum;
	}
	
	private void call(ValCallback callback){
		this.callback = callback;
		if(nameIsPrefix){
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
}
