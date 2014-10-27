package ParserTests;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import ParserTBA.Codebase.Clazz;
import ParserTBA.Parser;

public class ParserTest1 {
	
	@BeforeClass
	public static void setUp() {
		
		// Create mock object of BufferedReader
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		
		ArrayList<String> files = new ArrayList<String>();
		Parser parser = new Parser(files);
		Clazz clazz = new Clazz();
		
	}
	
	@Test
	public void testparse() {
		
		ArrayList<Clazz> testClasses = parser.parse();
		
	}
	
	@Test
	public void testparseClass() {
		
		// TODO: TEST
		
	}

	@Test
	public void testparseMethod() {	
		
		// TODO: TEST
		
	}

	@Test
	public void testtypeCount() {
		
		// TODO: TEST
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
	public int testmethodSignature() {

		// TODO: TEST
		
	}

	@Test 
	public int testcommaCounter() {

		// TODO: TEST
		
	}
}