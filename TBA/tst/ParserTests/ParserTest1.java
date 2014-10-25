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
	public static void setUp() {
		private ArrayList<String> TestFiles;
		parser = new Parser(null /*TODO make args*/);
	}
	// Are exception tests necessary? eg.
	@Test (expected = Exception.class)
	public void testExceptionIsThrown() {
		parser.parseClass(NoFile.java);
	}
	
	@Test (expected = Exception.class)
	public void testcountExceptionIsThrown() {
		parser.typeCount(line, index, table);
	}
	
	@Test
	public void testparseClass() {
		
		BufferedReader mockReader = Mockito.mock(BufferedReader.class);
		parser.setBufferedReaderForTestOnly(mockReader);
		Mockito.when(mockReader.nextLine).thenReturn("TEXT TO BE PARSED");
		
		// Clazz mockClazz = Mockito.mock(Clazz.class);
		
		// Clazz result = parser.parseClass(SampleFile.java);
		// assertEquals("", result);
	}

	@Test
	public void testparseMethod() {
		ArrayList mockList = Mockito.mock(ArrayList.class);
		
		// result = parser.parseMethod(currLine, param);
		// assertEquals("", result); 

	}

	@Test
	public void testtypeCount() {
		result = parser.typeCount(line, index, table);
		assertEquals("", result);

	}
	
	@Test
	public void testmethodOrVar() {
		result = parser.methodOrVar(line, index, table);
		assertEquals("", result);
	}

	@Test
	public void testclassDeclaration() {
		result = parser.classDeclaration(tokens, index);
		assertEquals("", result);

	}

	@Test 
	public int testmethodSignature() {

		int result = parser.methodSignature(tokens, params);
		assertEquals("", result);
	}

	@Test 
	public int testcommaCounter() {

		int result = parser.commaCounter(tokens, params);
		assertEquals("", result);
	}
}