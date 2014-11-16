package ParserTests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
	ArrayList<Clazz> subclasses;
	Clazz superclass;

	@Before
	public void setUp() {
		files = new ArrayList<String>();
		parser = new Parser(files);
		mockReader = Mockito.mock(BufferedReader.class);
		clazz = new Clazz();
		vTable = new VarTable();
		subclasses = new ArrayList<Clazz>();
	}

	@Test
	public void testParse() {

		/*ArrayList<Clazz> testClasses;
		parser.parse();
		File file = new File("C:\\MyFile.txt");
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;*/
   
	}

	@Test
	public void testParseClass() {
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		// TODO: 

	}
	
	@Test
	public void testparseClassDeclaration() {
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.getBufferedReaderForTestOnly();

		Clazz superClazz = new Clazz();
		superClazz.SetClassName("OtherClass");
		superClazz.addSubclass(clazz);
		clazz = parser.getCurrentClassForTestOnly();
		
		assertEquals("OtherClass", clazz.getSuperClassNameForTestOnly());
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
	public void testMethodOrVarMethod() throws Exception {

		String[] currLine = { "public", "void", "method()" };
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.methodOrVar(currLine, 2, vTable);
		method = parser.getCurrentClassForTestOnly().getMethods().get(0);
		assertEquals("method", method.methodName);
	}
	
	@Test
	public void testMethodOrVarVar() throws Exception {
		// TODO: TEST

	}

	@Test
	public void testParseMethodSignature() throws Exception {

		String[] currLine = { "public", "void", "method(","int", "i","boolean", "j)" };
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.getBufferedReaderForTestOnly();
		int num = parser.parseMethodSignature(currLine, 4);
		assertEquals(2, num);

	}

	@Test
	public void testCountDeclaredVariables() throws Exception {

		// TODO: TEST
		String[] currLine = { "public", "void", "varTable", "var1", ",", "var2", "var3",",", ";" };
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.getBufferedReaderForTestOnly();
		int num = parser.countDeclaredVariables(currLine, 4);
		assertEquals(3, num);
		
	}
	
	@Test
	public void testorganizeHierarchy() {
		
			// TODO: TEST
		
	}
}