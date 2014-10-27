package ParserTests;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import ParserTBA.Codebase.Clazz;
import ParserTBA.Codebase.Methodz;
import ParserTBA.Codebase.VarTable;
import ParserTBA.Codebase.types;
import ParserTBA.Parser;

public class ParserTest1 {
	
	ArrayList<String> files;
	Parser parser;
	BufferedReader mockReader;
	Clazz clazz;
	VarTable vTable;
	Methodz method;
	
	@Before
	public void setUp() {
		
		files = new ArrayList<String>();
		parser = new Parser(files);
		mockReader = Mockito.mock(BufferedReader.class);
		clazz = new Clazz();
		vTable = new VarTable();
		method = new Methodz();
		
	}

	@Test
	public void testparse() {

		// ArrayList<Clazz> testClasses = parser.parse();
		// TODO: TEST

	}

	@Test
	public void testparseClass() {

		// TODO: TEST

	}

	@Test
	public void testparseMethod() throws Exception {

		String[] currLine = {"testMethod(){}"};
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.getBufferedReaderForTestOnly();
		parser.parseMethod(currLine, 0);
		
		assertEquals("[MethodName: " + method.methodName + ", parameters: " + method.parameters
				+ ", SLOC: " + method.sloc + "]", method.toString());
	}

	@Test
	public void testtypeCount() throws Exception {

		String[] line = {"public", "boolean", "hello,", "swag;"};
		parser.setCurrentClassForTestOnly(clazz);
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.typeCount(line, 0, vTable);

		assertEquals(2, vTable.getTable()[types.BOOLEAN.ordinal()]);
	}

	@Test
	public void testmethodOrVar() {

		// TODO: TEST

	}

	@Test
	public void testclassDeclaration() {

		// TODO: TEST

	}

	@Test
	public void testmethodSignature() {

		// TODO: TEST

	}

	@Test
	public void testcommaCounter() {

		// TODO: TEST

	}
}