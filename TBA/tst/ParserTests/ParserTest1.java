package ParserTests;

public class ParserTest1 {
	//J-Unit format, will change to mock objects
	
	Parser parser = new Parser();
	
	@Test
	public void testparseClass() {
		private Clazz result = parser.parseClass(SampleFile.java);
		assertEquals("", result);
	}

	@Test
	public void testparseMethod() {
	
		private /*type*/ result = parser.parseMethod(currLine, param);
		assertEquals("", result); 

	}

	@Test
	public void testtypeCount() {

		private /*type*/ result = parser.typeCount(line, index, table);
		assertEquals("", result);

	}
	
	@Test
	public void testmethodOrVar() {
		
		private /*type*/ result = parser.methodOrVar(line, index, table);
		assertEquals("", result);
	}

	@Test
	public void testclassDeclaration() {

		private /*type*/ result = parser.classDeclaration(tokens, index);
		assertEquals("", result);

	}

	@Test 
	public void testmethodSignature() {

		private int result = parser.methodSignature(tokens, params);
		assertEquals("", result);
	}

	@Test 
	public void testcommaCounter() {

		private int result = parser.commaCounter(tokens, params);
		assertEquals("", result);
	}
