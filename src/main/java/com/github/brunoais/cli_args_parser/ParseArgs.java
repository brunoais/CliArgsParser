package com.github.brunoais.cli_args_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.brunoais.cli_args_parser.callbacks.ValCallback;

/**
 * Simple Arguments parser
 * 
 * 
 * @author brunoais
 */
public class ParseArgs implements ArgParser{
	static final Logger LOG = LoggerFactory.getLogger(ParseArgs.class);
	
	private Map<String, Argument> keySpaceValArgs;
	private ArrayList<Argument> prefixedArgs;
	private ArrayList<Argument> keyValueArgs;
	private Argument defaultArgument;
	private ValCallback notFoundArgument;
	
	private boolean noDashIsDefaultArgument;
	
	public ParseArgs() {
		keySpaceValArgs = new HashMap<>();
		prefixedArgs = new ArrayList<>();
		keyValueArgs = new ArrayList<>();
		noDashIsDefaultArgument = true;
	}


	public GNUPOSIXParseArgs asGNUPOSIXParser(){
		return new GNUPOSIXArgsParser();
	}
	
	class GNUPOSIXArgsParser implements GNUPOSIXParseArgs{
		
		public GNUPOSIXArg argument() {
			return new GNUPOSIXArgument(null, this);
		}

		public GNUPOSIXParseArgs argument(String name) {
			
		}

		public ValCallback unknownArgCallback(ValCallback notFoundArgument) {
			return ParseArgs.this.unknownArgCallback(notFoundArgument);
		}

		public void parseArgs(String[] args) {
			ParseArgs.this.parseArgs(args);
		}

		public void parseArgs(String[] args, int start) {
			ParseArgs.this.parseArgs(args, start);
		}

		public void parseArgs(String[] args, int start, int end) throws ArrayIndexOutOfBoundsException {
			ParseArgs.this.parseArgs(args, start, end);
		}
		
	}
	
	/**
	 * Creates a new Argument to parse the default arguments
	 * @return A new Argument for the default arguments for configuration
	 */
	public Argument argument(){
		return new Argument(null, this);
	}
	
	/**
	 * Creates a new Argument to parse input
	 * Provide a null name if it is a default argument 
	 * @param name The name of the argument
	 * @return A new Argument for this name for configuration
	 */
	public Argument argument(String name){
		return new Argument(name, this);
	}
	
	void appendNormal(Argument argument){
		noDashIsDefaultArgument = noDashIsDefaultArgument && argument.name.charAt(0) == '-';
		keySpaceValArgs.put(argument.name, argument);
	}
	
	void appendPrefixed(Argument argument){
		noDashIsDefaultArgument = noDashIsDefaultArgument && argument.name.charAt(0) == '-';
		prefixedArgs.add(argument);
	}
	
	void appendEqValue(Argument argument){
		noDashIsDefaultArgument = noDashIsDefaultArgument && argument.name.charAt(0) == '-';
		keyValueArgs.add(argument);
	}

	void setDefault(Argument argument) {
		defaultArgument = argument;
	}
	
	/**
	 * Sets the callback for when an argument is included but no correspondence was found
	 * @param notFoundArgument The callback called if an unknown argument is found 
	 * @return the previous callback. Null otherwise
	 */
	public ValCallback unknownArgCallback(ValCallback notFoundArgument){
		ValCallback prevNotFoundArgument = this.notFoundArgument;
		this.notFoundArgument = notFoundArgument;
		return prevNotFoundArgument;
	}
	
	/**
	 * Parses the args array fully
	 * 
	 * @param args The args array to parse
	 */
	public void parseArgs(String[] args){
		parseArgs(args, 0);
	}
	/**
	 * Parses the args array fully from start (inclusive)
	 *  
	 * @param args The args array to parse
	 * @param start The first index of args array to parse
	 */
	public void parseArgs(String[] args, int start){
		parseArgs(args, start, args.length);
	}
	/**
	 * Parses the args array from start (inclusive) to end (exclusive)
	 *  
	 * @param args The args array to parse
	 * @param start The first index of args array to parse
	 * @param end The (last - 1) index of args array to parse
	 * @throws ArrayIndexOutOfBoundsException if:
	 * 										* start &lt; 0 || end &gt; args.lengh
	 * 										* args ended too soon when trying to parse a value with spaces
	 */
	public void parseArgs(String[] args, int start, int end) throws ArrayIndexOutOfBoundsException{
		
		if(start < 0 || end > args.length){
			if(start < 0) throw new ArrayIndexOutOfBoundsException(start);
			if(end > args.length) throw new ArrayIndexOutOfBoundsException(end);
		}
		
		int argNum = start;
		
		try{
			
			// Warning: argNum will be manipulated inside the loop
			while(argNum < end){
				String arg = args[argNum];
				
				if(noDashIsDefaultArgument && arg.charAt(0) != '-' && defaultArgument != null){
					defaultArgument.found(arg);
				} else {
					Argument argument = keySpaceValArgs.get(arg);
					if(argument == null){
						argument = findArgInArray(arg, keyValueArgs);
					}
					if(argument == null){
						argument = findArgInArray(arg, prefixedArgs);
					}
					if(argument == null && !noDashIsDefaultArgument){
						argument = defaultArgument;
					}
					if(argument != null){
						argNum = argument.parse(args, argNum) - 1;
					} else if(notFoundArgument != null){
						notFoundArgument.c("", "", arg);
					}
				}
				argNum += 1;
				
			}
		
		}catch (ArrayIndexOutOfBoundsException e) {
			throw new UnexpectedEndOfArgs("The argument" + args[argNum] + "is incompletely set by the user.", e, args[argNum]);
		}
	}
	
	static Argument findArgInArray(String arg, ArrayList<Argument> searchOn){
		for (Iterator<Argument> iterator = searchOn.iterator(); iterator.hasNext();) {
			Argument testingArg = iterator.next();
			if(arg.startsWith(testingArg.name)){
				return testingArg;
			}
		}
		return null;
	}
	
}
	
