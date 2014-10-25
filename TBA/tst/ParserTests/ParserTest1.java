package ParserTests;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import ParserTBA.Codebase.Clazz;
import ParserTBA.Parser;

public class ParserTest1 {
	
	private static Parser mockedParser;
	private static BufferedReader mockedReader;
	private static Codebase codebase1;
	
	@BeforeClass
	public static void setUp() {
		// Create mock object of Parser
		mockedParser = new Parser(null /*TODO make args*/);
		
		// Create mock object of BufferedReader
		mockedReader = Mockito.mock(BufferedReader.class);
		mockedParser.setBufferedReaderForTestOnly(mockedReader);
		
		// Instance of Codebase class
		codebase1 = new Codebase(classes);
		
		// Clazz mockClazz = Mockito.mock(Clazz.class);
		// ArrayList mockList = Mockito.mock(ArrayList.class);
		
		// Stubbing methods of mocked parser ???
		when(mockedParser.parse(/*args*/)).thenReturn(codebase1);
		when(mockedParser.parseClass(/*args*/)).thenReturn(clazz);
		when(mockedParser.parseMethod(/*args*/)).thenReturn();
		when(mockedParser.typeCount(/*args*/)).thenReturn();
	}
	
	@Test
	public void testparse() {
		
		ArrayList<Clazz> testClasses = mockedParser.parse();
	}
	
	@Test
	public void testparseClass() {
		
		when(mockedParser.getCurrentClazzForTestOnly()).thenReturn();
		// Clazz result = parser.parseClass(SampleFile.java);
		// assertEquals("", result);
	}

	@Test
	public void testparseMethod() {	
		
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
	
	// Are exception tests necessary? eg.
	@Test (expected = Exception.class)
	public void testExceptionIsThrown() {
		parser.parseClass(NoFile.java);
	}
	
	@Test (expected = Exception.class)
	public void testcountExceptionIsThrown() {
		parser.typeCount(line, index, table);
	}
}