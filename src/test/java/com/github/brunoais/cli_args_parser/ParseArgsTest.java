package com.github.brunoais.cli_args_parser;

import static org.junit.Assert.*;

import static java.util.Arrays.asList;

import java.util.Iterator;

import org.junit.Test;

import com.github.brunoais.cli_args_parser.ParseArgs;

public class ParseArgsTest{
	
	@Test
	public void simpleArgsTest(){
		int[] passedIn1 = new int[1];
		int[] passedIn2 = new int[1];
		
		ParseArgs parser = new ParseArgs();
		parser.argument(null).call((me) -> {
			assertEquals("do", me);
			passedIn1[0]++;
		});
		parser.argument("-yes").call(() -> {
			passedIn2[0]++;
		});
		parser.parseArgs("do -yes -no".split(" "));
		
		assertEquals("The callback for default argument was not called", 1, passedIn1[0]);
		assertEquals("The callback for '-yes' was not called", 1, passedIn2[0]);
	}
	
	@Test
	public void onlyDefaultSetTest(){
		int[] passedIn1 = new int[1];
		Iterator<String> expected = asList("do", "maybe", "way").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument(null).call((me) -> {
			assertEquals(expected.next(), me);
			passedIn1[0]++;
		});
		parser.parseArgs("do -yes maybe -no way".split(" "));
		assertFalse("The callback for default argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}

	@Test
	public void argWithValueTest(){
		int[] passedIn1 = new int[1];
		Iterator<String> expected = asList("maybe").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-yes").spaceValue().call((me) -> {
			assertEquals(expected.next(), me);
			passedIn1[0]++;
		});
		
		parser.parseArgs("do -yes maybe -no way".split(" "));

		assertFalse("The callback for '-yes' argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}
	
	@Test
	public void multipleOfSameKindTest(){
		int[] passedIn1 = new int[1];
		Iterator<String> expected = asList("maybe", "may").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-yes").spaceValue().call((me) -> {
			assertEquals(expected.next(), me);
			passedIn1[0]++;
		});
		parser.parseArgs("do -yes maybe -no way -yes may".split(" "));
		assertFalse("The callback for '-yes' argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}
	
	
	@Test
	public void keyValueTest(){
		int[] passedIn1 = new int[1];
		Iterator<String> expected = asList("maybe", "may").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-take").keyEqualValue().call((me) -> {
			assertEquals(expected.next(), me);
			passedIn1[0]++;
		});
		parser.parseArgs("do -take=maybe -take=may".split(" "));
		assertFalse("The callback for '-take' argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}
	
	@Test
	public void prefixKeyValueTest(){
		int[] passedIn1 = new int[1];
		Iterator<String> expectedKeys = asList("Work", "Life").iterator();
		Iterator<String> expected = asList("maybe", "may").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-take").keyEqualValue().call((key, value) -> {
			assertEquals(expectedKeys.next(), key);
			assertEquals(expected.next(), value);
			passedIn1[0]++;
		});
		parser.parseArgs("do -takeWork=maybe -takeLife=may".split(" "));
		
		assertFalse("The callback for '-take' argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}

	@Test
	public void prefixKeyValueValueTest(){
		int[] passedIn1 = new int[1];
		Iterator<String> expectedKeys = asList("Work", "Life").iterator();
		Iterator<String> expected = asList("maybe", "may").iterator();
		Iterator<String> expectedValues = asList("yes", "not").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-take").keyEqualValue().spaceValue().call((key, value, value2) -> {
			assertEquals(expectedKeys.next(), key);
			assertEquals(expected.next(), value);
			assertEquals(expectedValues.next(), value2);
			passedIn1[0]++;
		});
		parser.parseArgs("do -takeWork=maybe yes -takeLife=may not".split(" "));
		
		assertFalse("The callback for '-take' argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}
	

	@Test
	public void prefixKeyValueValueWithDefaultTest(){
		int[] passedIn1 = new int[1];
		int[] passedIn2 = new int[1];
		Iterator<String> expectedKeys = asList("Work", "Life").iterator();
		Iterator<String> expected = asList("maybe", "may").iterator();
		Iterator<String> expectedValues = asList("yes", "not").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument(null).call((value) ->{
			assertEquals("default", value);
			passedIn2[0]++;
		});
		parser.argument("-take").keyEqualValue().spaceValue().call((key, value, value2) -> {
			assertEquals(expectedKeys.next(), key);
			assertEquals(expected.next(), value);
			assertEquals(expectedValues.next(), value2);
			passedIn1[0]++;
		});
		parser.parseArgs("-takeWork=maybe yes -takeLife=may not default".split(" "));
		
		assertFalse("The callback for '-take' argument was called only " + passedIn1[0] + " times", expected.hasNext());
		assertTrue("The callback for default argument was called only " + passedIn2[0] + " times", passedIn2[0] == 1);
	}
	
	
	@Test
	public void whenMixKeyEqValueAndEqValueOrderDoesNotMatter1(){
		int[] passedIn1 = new int[1];
		int[] passedIn2 = new int[1];
		Iterator<String> expectedKeys = asList("Life").iterator();
		Iterator<String> expected = asList("maybe", "may").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-takeWork").equalValue().call((key, value) -> {
			assertEquals(expected.next(), value);
			passedIn1[0]++;
		});
		parser.argument("-take").keyEqualValue().call((key, value) -> {
			assertEquals(expectedKeys.next(), key);
			assertEquals(expected.next(), value);
			passedIn2[0]++;
		});
		parser.parseArgs("-takeWork=maybe yes -takeLife=may not default".split(" "));
		
		assertFalse("The callback for '-take' argument was called only " + passedIn1[0] + " times", expected.hasNext());
		assertTrue("The callback for default argument was called only " + passedIn2[0] + " times", passedIn2[0] == 1);
	}
	
	@Test
	public void whenMixKeyEqValueAndEqValueOrderDoesNotMatter2(){
		int[] passedIn1 = new int[1];
		int[] passedIn2 = new int[1];
		Iterator<String> expectedKeys = asList("Life").iterator();
		Iterator<String> expected = asList("maybe", "may").iterator();
		
		ParseArgs parser = new ParseArgs();
		parser.argument("-take").keyEqualValue().call((key, value) -> {
			assertEquals(expectedKeys.next(), key);
			assertEquals(expected.next(), value);
			passedIn2[0]++;
		});
		parser.argument("-takeWork").equalValue().call((key, value) -> {
			assertEquals(expected.next(), value);
			passedIn1[0]++;
		});
		parser.parseArgs("-takeWork=maybe yes -takeLife=may not default".split(" "));
		
		assertFalse("The callback for '-take' argument was called only " + passedIn1[0] + " times", expected.hasNext());
		assertTrue("The callback for default argument was called only " + passedIn2[0] + " times", passedIn2[0] == 1);
	}
	
	@Test
	public void attrAndNormalAreEqual(){
		int[] passedIn1 = new int[1];
		Iterator<String> expected = asList("no", "maybe", "ok").iterator();
		
		ParseArgs parser = new ParseArgs();
		
		parser.argument("--work").attr(parser.arg("-w").spaceValue()).equalValue().call((value) -> {
			assertEquals(expected.next(), value);
			passedIn1[0]++;
		});
		parser.parseArgs("-w no --work=maybe -w ok".split(" "));
		
		assertFalse("The callback for '--work'+'-w' argument was called only " + passedIn1[0] + " times", expected.hasNext());
	}
	
}
