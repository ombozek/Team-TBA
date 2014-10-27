package ParserTests;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import ParserTBA.Codebase.Clazz;
import ParserTBA.Codebase.VarTable;
import ParserTBA.Codebase.types;
import ParserTBA.Parser;

public class ParserTest1 {
	
	@BeforeClass
	public static void setUp() {
		
		// Create mock object of BufferedReader
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		
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
		
		// TODO: TEST
		ArrayList<String> files = new ArrayList<String>();
		Parser parser = new Parser(files);
		Clazz clazz = new Clazz();
		String[] currLine = {"("};
		parser.setCurrentClassForTestOnly(clazz);
		parser.parseMethod(currLine, 0);
		
		// assertEquals();
	}

	@Test
	public void testtypeCount() throws Exception {
		
		// TODO: TEST
		ArrayList<String> files = new ArrayList<String>();
		Parser parser = new Parser(files);
		Clazz clazz = new Clazz();
		VarTable vTable = new VarTable();
		String[] line = {"boolean"};
		parser.setCurrentClassForTestOnly(clazz);
		parser.typeCount(line, 0, vTable);
		
		assertEquals(1, vTable.getTable()[types.BOOLEAN.ordinal()]);
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