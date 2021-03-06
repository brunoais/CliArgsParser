# CLI Args Parser

Quick & simple, no quirks arguments parser.  
This is only a parser. This does <b>not</b> do fancy stuff including (but not limited to):

* Providing a help output (for "--help", for example).
* Identify the input is invalid.
* Prevent yourself from committing mistakes while setting up.
* (when there are options that do not start with '-') Identify options not set. 

Feel free to provide changes to this code so it supports the above mentioned features.

## Requirements

This library requires java 8. If you require one for older java versions, try Apache's [Common's CLI](https://commons.apache.org/cli/)  
No more requirements.

## Usage warning
This project is still in its initial stages of development, the API may change to accommodate with its issues when used in the real world (although I do not intent to).  


## Quick Start

```java
import com.github.brunoais.cli_args_parser.ParseArgs;
import com.github.brunoais.cli_args_parser.Argument;

public class HelloWorld {
	// Callback for the -do argument.
	// The format will be "-do<space><value>" in the CLI
	public static void doArgument(String value){
		System.out.println("Received value of -do: " + value);
	}
	// The format will be "<space><value>" in the CLI
	public static void defaultArgument(String value){
		System.out.println("Received default argument with value " + value);
	}
    public static void main(String[] args) {
		ParseArgs when = new ParseArgs();
		when.argument("-do").spaceValue().call(this::doArgument);
		when.argument().call(this::defaultArgument);
		when.parse(args);
    }
}
```

## Install

Latest stable version:
```xml
<dependency>
	<groupId>com.github.brunoais</groupId>
	<artifactId>cli-args-parser</artifactId>
	<version>0.3.0</version>
</dependency>
``` 

Latest "stable" master version
```xml
<dependency>
	<groupId>com.github.brunoais</groupId>
	<artifactId>cli-args-parser</artifactId>
	<version>0.3.1-SNAPSHOT</version>
</dependency>
``` 

## How to use

The API for this parser was thought so for each argument definition can be fully defined on a chain in a single line.  
Create a new instance of `ParseArgs`.
Each argument definition starts with a call to `.argument()` on the `ParseArgs` instance
and follow the API until you call `.call()` which will terminate the chain and register the argument.

### Methods

#### Parser

```java

	ParseArgs parser = new ParseArgs();
	parser.argument(...) /* ... */
	/* ... */
	parser.unknownArgCallback((value) -> System.out.println("Argument " + value + " is unknown"));
	parser.parse(args);
	
```

#### Parser methods
	
* `.argument()` -> Start building a new argument.
* `.unknownArgCallback()` -> If an argument is found but it had not been defined.
* `.parse()` -> Parses the string array. Corresponding callbacks are called

#### Argument building

##### Names and small descriptions

* `.prefixes()` -> Specifies the name given is a prefix for params as in `<name><key>=<value>` or `<name><key> <value>` fashion.
* `.equalValue()` -> Specifies the name given is the "key" part in a `<key>=<value>` fashion.
* `.keyEqualValue()` -> Specifies the param is in format `<prefix><key>=<value>`
* `.keySpaceValue()` -> alias to `.spaceValue()`.
* `.spaceValue()` -> Specifies the param is in format `<something><space><value>`
* `.spaceValued(int)` -> Specifies the param is in format `<something>(<space><value>)*int`

##### Recommended calling order

Although the order of the method calls is irrelevant, if you keep a certain order, reading the code becomes an easier.  
My recommended order:
```java
	parser.argument("-name").keyEqualValue().spaceValue().call(...)
```	
	
or

```java
	parser.argument("-name").prefixes().equalValue().spaceValue().call(...)
```

#### Examples

To capture `-doodle`

```java
	parser.argument("-doodle").call(()-> System.out.println("doodleFound"))
```

To capture `--option` & `--readable-option value` 

```java
	parser.argument("--option").call(()-> System.out.println("optionFound"))
	parser.argument("--readable-option").spaceValue().call((value)-> System.out.println(value + "Found"))
```

To capture `-O2` `-O3` `-O4`, etc...

```java
	parser.argument("-O").prefixed().call((key, value)-> 
		System.out.println(
			(key.equals("2") ? "Oxigen": key.equals("3") ? "Ozone" : "something")+ " Found"
		)
	)
```

To capture `-DsomeKey=true`

```java
	parser.argument("-D").prefixes().keyEqualValue().call((key, value)-> System.out.println(key + "=" + value))
```

To capture `-DotherKey=yay Foo` (cannot be used with `-DsomeKey=true` due to the conflict in the prefix)

```java
	parser.argument("-D").prefixes().keyEqualValue().spaceValue().call((key, value, spaceValue)-> System.out.println(key + "=" + value + " " + spaceValue))
```


#### Default argument

To capture the default argument, just call the `.argument()` method followed by the callback

```java
	parser.argument().call((value)-> System.out.println("Default arg with " + value))
```

#### Unknown argument

If all arguments you provide does not have a name starting without a hyphen ('-'), the library can identify arguments 
(arguments starting with `-`) that cannot be identified. You may define a callback for such event using the method
`.unknownArgCallback()` method on the parser instance.

### How callbacks are called

**Note:**
There are no limits as to when each callback you provide is called.
Every time a definition matches, the corresponding callback is called.

There are 5 different callbacks you can build I will list below. I named the variables so it can be easier to know
which values each variable in the callback has. Here's my naming schema (in java8's lambda syntax):

	<name><key>=<eqValue> <sValue>

These are the callbacks:

```java
	() -> 
	(sValue) ->
	(key, eqValue) ->
	(key, eqValue, sValue) ->
	(name, key, eqValue, sValue) ->
```


