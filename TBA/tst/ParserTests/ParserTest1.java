package ParserTests;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import ParserTBA.Codebase.Clazz;
import ParserTBA.Parser;

public class ParserTest1 {
	//J-Unit format, will change to mock objects
	
	Parser parser;
	
	@BeforeClass
	public void setUp(){
		parser = new Parser(null /*TODO make args*/);
	}
	
	@Test
	public void testparseClass() {
		Clazz result = parser.parseClass(SampleFile.java);
		assertEquals("", result);
	}

	@Test
	public void testparseMethod() {
		/*type*/ result = parser.parseMethod(currLine, param);
		assertEquals("", result); 

	}

	@Test
	public void testtypeCount() {
		/*type*/ result = parser.typeCount(line, index, table);
		assertEquals("", result);

	}
	
	@Test
	public void testmethodOrVar() {
		/*type*/ result = parser.methodOrVar(line, index, table);
		assertEquals("", result);
	}

	@Test
	public void testclassDeclaration() {
		/*type*/ result = parser.classDeclaration(tokens, index);
		assertEquals("", result);

	}

	@Test 
	public void testmethodSignature() {

		int result = parser.methodSignature(tokens, params);
		assertEquals("", result);
	}

	@Test 
	public void testcommaCounter() {

		int result = parser.commaCounter(tokens, params);
		assertEquals("", result);
	}
