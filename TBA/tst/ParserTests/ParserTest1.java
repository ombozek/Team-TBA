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
	}

	@Test
	public void testParse() {

		// ArrayList<Clazz> testClasses = parser.parse();
		// TODO: TEST

	}

	@Test
	public void testParseClass() {

		// TODO: TEST

	}

	@Test
	public void testParseMethodBodyEmpty() throws Exception {

		String[] currLine = { "public", "void", "testMethod(){}" };
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.getBufferedReaderForTestOnly();
		parser.parseMethodBody(currLine, 2);

		// There should only be one method in there
		clazz = parser.getCurrentClassForTestOnly();
		assertEquals(1, clazz.getMethods().size());
		method = parser.getCurrentClassForTestOnly().getMethods().get(0);

		assertEquals("testMethod", method.methodName);
		assertEquals(0, method.parameters);
		assertEquals(0, method.sloc);
	}

	@Test
	public void testCountKeywordsBoolean() throws Exception {

		String[] line = { "public", "boolean", "hello,", "swag;" };
		parser.setCurrentClassForTestOnly(clazz);
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.countKeywords(line, 0, vTable);

		assertEquals(2, vTable.getTable()[types.BOOLEAN.ordinal()]);
	}

	@Test
	public void testMethodOrVar() {

		// TODO: TEST

	}

	@Test
	public void testParseClassDeclaration() {

		// TODO: TEST

	}

	@Test
	public void testParseMethodSignature() {

		// TODO: TEST

	}

	@Test
	public void testCountDeclaredVariables() {

		// TODO: TEST

	}
}