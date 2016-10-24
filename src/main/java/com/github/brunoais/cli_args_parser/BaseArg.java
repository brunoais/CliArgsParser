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
	Callback callback;

	BaseArg(String name, ArgParser registerTo) {
		this.name = name;
		this.registerTo = registerTo;
		this.multiplicity = 0;
		this.hasKey = false;
		this.eqValue = false;
	}
	
	/**
	 * Adds a shorter alternate version of this argument to this argument.
	 * Syntactically, both are meant to be synonyms.
	 * @param attr
	 * @return
	 */
	BaseArg attr(BaseArg attr){
		this.attr = attr;
		return this;
	}
	
	/**
	 * Specifies the name given is a prefix for params as in:<br>
	 * {@code <name><key>=<value>} or {@code <name><key> <value>} fashion.
	 * @return
	 */
	BaseArg prefixes() {
		prefixed = true;
		return this;
	}
	
	/**
	 * Specifies the name given is the "key" part in a {@code <key>=<value>} as in:<br>
	 * {@code <name>=<value>} fashion.
	 */
	BaseArg equalValue(){
		prefixed = true;
		eqValue = true;
		return this;
	}
	
	/**
	 * Specifies the name given is the "name" part as in:<br>
	 * {@code <name><key>=<value>} fashion.
	 * The key difference between this method and {@link #prefixes()} is that this method implies {@link #prefixes()}
	 * while {@link #prefixes()} does not imply the {@code =} separator.
	 */
	BaseArg keyEqualValue(){
		prefixes();
		hasKey = true;
		return equalValue();
	}
	
	/**
	 * @see #spaceValue()
	 */
	BaseArg keySpaceValue(){
		return spaceValue();
	}
	
	/**
	 * Specifies the name given is the "name" part as in:<br>
	 * {@code <name> <value>} fashion. (the most common)
	 */
	BaseArg spaceValue(){
		multiplicity = 1;
		return this;
	}
	
	/**
	 * Used when there can be multiple consequent values for a "key"
	 * E.g. With a name of "-arg" and after calling ".spaceValued(3)", the user inputs:<br>
	 * "-arg val1 val2 val3"
	 * 
	 * your callback is called 3 times. The 3 times with "-arg" as the name and each different valX each time. 
	 * 
	 * @param times The number of times values will appear after the "key" argument.
	 * @return this
	 */
	BaseArg spaceValued(int times){
		multiplicity = times;
		return this;
	}
	
	void found(String value){
		callback.c(name, "", "", value);
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
			callback.c(name, key, keyValueValue, "");
		} else {
			int getUntil = startNum + multiplicity + 1;
			for (; workingNum < getUntil; workingNum++) {
				if(eqValue){
					callback.c(name, key, keyValueValue, args[workingNum]);
				} else {
					callback.c(name, key, "", args[workingNum]);
				}
			}
		}
		return workingNum;
	}
	
	void call(ValCallback callback){
		this.callback = new Callback(callback);
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
	
	private class Callback {
		private ValCallback callback;
		public Callback(ValCallback callback) {
			this.callback = callback;
			
		}
		
		void c(String name, String key, String keyValue, String spaceValue){
			if(callback instanceof NoValCallback){
				((NoValCallback) callback).callback();
			} else if(callback instanceof SingleValCallback){
				((SingleValCallback) callback).callback(spaceValue.isEmpty()? keyValue : spaceValue);
			} else if(callback instanceof DoubleValCallback){
				((DoubleValCallback) callback).callback(key, keyValue);
			} else if(callback instanceof TripleValCallback){
				((TripleValCallback) callback).callback(key, keyValue, spaceValue);
			} else if(callback instanceof QuadrupleValCallback){
				((QuadrupleValCallback) callback).callback(name, key, keyValue, spaceValue);
			} else {
				throw new RuntimeException("BUG IN Callback!!!");
			}
		}
	}
}
