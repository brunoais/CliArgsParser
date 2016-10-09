package pt.brunoais.cli_args_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pt.brunoais.cli_args_parser.callbacks.ValCallback;

public class ParseArgs {

	private Map<String, Argument> findArgs;
	private ArrayList<Argument> prefixedArgs;
	private Argument defaultArgument;
	private ValCallback notFoundArgument;
	
	private boolean noDashIsDefaultArgument;
	
	public ParseArgs() {
		findArgs = new HashMap<>();
		prefixedArgs = new ArrayList<>();
		noDashIsDefaultArgument = true;
	}
	
	public Argument argument(String name){
		return new Argument(name, this);
	}
	
	void appendNormal(Argument argument){
		noDashIsDefaultArgument = noDashIsDefaultArgument && argument.name.charAt(0) == '-';
		findArgs.put(argument.name, argument);
	}
	
	void appendPrefixed(Argument argument){
		noDashIsDefaultArgument = noDashIsDefaultArgument && argument.name.charAt(0) == '-';
		prefixedArgs.add(argument);
	}

	void setDefault(Argument argument) {
		defaultArgument = argument;
	}
	
	/**
	 * Sets the callback for when an argument is included but no correspondence was found
	 * @return the previous callback. Null otherwise
	 */
	public ValCallback unknownArgCallback(ValCallback notFoundArgument){
		ValCallback prevNotFoundArgument = this.notFoundArgument;
		this.notFoundArgument = notFoundArgument;
		return prevNotFoundArgument;
	}
	
	public void parseArgs(String[] args){
		parseArgs(args, 0);
	}
	
	public void parseArgs(String[] args, int start){
		parseArgs(args, start, args.length);
	}
	
	public void parseArgs(String[] args, int start, int end){
		
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
					Argument argument = findArgs.get(arg);
					if(argument == null){
						argument = findArgInArray(arg);
					}
					if(argument == null && !noDashIsDefaultArgument){
						argument = defaultArgument;
					}
					if(argument != null){
						argNum = argument.parse(args, argNum);
					} else if(notFoundArgument != null){
						notFoundArgument.c("", "", arg);
					}
				}
				argNum += 1;
				
			}
		
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("The argument" + args[argNum] + "is incompletely set.");
			throw e;
		}
	}
	
	private Argument findArgInArray(String arg){
		for (Iterator<Argument> iterator = prefixedArgs.iterator(); iterator.hasNext();) {
			Argument testingArg = iterator.next();
			if(arg.startsWith(testingArg.name)){
				return testingArg;
			}
		}
		return null;
	}
	
}
	
