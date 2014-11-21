package ParserTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ParserTBA.Codebase;
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
	public final String[] classNames = { "class1", "class2", "class3",
			"class4", "class5" };
	public final String[] subClassNames = { "subclass1", "subclass2",
			"subclass3", "subclass4", "subclass5" };
	ArrayList<Clazz> subclasses;
	Clazz superclass;

	@Before
	public void setUp() {
		files = new ArrayList<String>();
		parser = new Parser(files);
		mockReader = Mockito.mock(BufferedReader.class);
		clazz = new Clazz(classNames[0]);
		vTable = new VarTable();
		subclasses = new ArrayList<Clazz>();
	}

	@Test
	public void testParse() {

		/*Clazz testClass = new Clazz("This");
		try {
			File file = new File("src/tst/ParserTest1.java");
			parser.setCurrentClassForTestOnly(testClass);
			parser.parseClass(file);
			assertEquals(testClass.numImports,17);
			assertEquals(testClass.getMethods().size(),14);
			assertEquals(testClass.getClassName(),"ParserTest1");
			assertEquals(testClass.getClass(), this);
		} catch (Exception e) {
			fail();
			System.out.println("fail");
			e.printStackTrace();
		}		*/
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
	public void testMethodOrVarMethod() throws Exception {

		String[] currLine = { "pubic", "void", "method()" };
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.methodOrVar(currLine, 2, vTable);
		method = parser.getCurrentClassForTestOnly().getMethods().get(0);
		assertEquals("method", method.methodName);

	}
	
	@Test
	public void testMethodOrVarVar() throws Exception {
		
		String[] currLine = { "public", "int", "hello", "=", "1", ",", "static", "int", "a", ";" };
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.methodOrVar(currLine, 0, vTable);
		assertEquals(2, vTable.getTable()[types.INT.ordinal()]);
		
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

		String[] currLine = { "public", "int", "x", "=", "1", ",", "y", "=", "2", ",", "z", "=", "3", ";" };
		
		parser.setCurrentClassForTestOnly(clazz);
		parser.getCurrentClassForTestOnly();
		parser.setBufferedReaderForTestOnly(mockReader);
		parser.getBufferedReaderForTestOnly();
		int num = parser.countDeclaredVariables(currLine, 4);
		assertEquals(3, num);
		
	}

	@Test
	public void testOrganizeHierarchyNoInheritance() {
		Hashtable<String, Clazz> classes = makeFileList();
		Codebase codebase = new Codebase(classes);
		codebase.organizeHierarchy();
		assertEquals(classNames.length, classes.values().size());
	}

	@Test
	public void testOrganizeHierarchySingleInheritance() {
		Hashtable<String, Clazz> classes = makeFileList();

		Clazz tempClazz;
		for (int i = 0; i < subClassNames.length; i++) {

			tempClazz = new Clazz(subClassNames[i]);
			tempClazz.superclassName = classNames[i];
			classes.put(subClassNames[i], tempClazz);
		}
		Codebase codebase = new Codebase(classes);

		assertEquals(classNames.length + subClassNames.length, classes.values()
				.size());
		codebase.organizeHierarchy();
		assertEquals(classNames.length, classes.values().size());

		for (int i = 0; i < classNames.length; i++) {
			// Test each class only has 1 subclass
			assertEquals(1, classes.get(classNames[i]).getSubclasses().size());
			// Test each class has it's proper subclass
			assertEquals(subClassNames[i], classes.get(classNames[i])
					.getSubclasses().get(0).getClassName());
		}
	}

	@Test
	public void testOrganizeHierarchyDoubleInheritance() {
		Hashtable<String, Clazz> classes = makeFileList();
		final String PREFIX = "DOUBLE";

		Clazz tempClazz;
		for (int i = 0; i < subClassNames.length; i++) {

			tempClazz = new Clazz(subClassNames[i]);
			tempClazz.superclassName = classNames[i];
			classes.put(subClassNames[i], tempClazz);

			// DOUBLE RAINBOW (or sub-sub-class, whatever)
			tempClazz = new Clazz(PREFIX + subClassNames[i]);
			tempClazz.superclassName = subClassNames[i];
			classes.put(PREFIX + subClassNames[i], tempClazz);
		}
		Codebase codebase = new Codebase(classes);
		assertEquals(classNames.length + subClassNames.length * 2, classes
				.values().size());
		codebase.organizeHierarchy();
		assertEquals(classNames.length, classes.values().size());

		for (int i = 0; i < classNames.length; i++) {
			tempClazz = classes.get(classNames[i]);
			// Test each class only has 1 subclass
			assertEquals(1, tempClazz.getSubclasses().size());
			// Test each class has it's proper subclass
			assertEquals(subClassNames[i], tempClazz.getSubclasses().get(0)
					.getClassName());

			// FLIPPIN DOUBLES -> Testing double inheritance
			tempClazz = tempClazz.getSubclasses().get(0);
			// Test each class only has 1 subclass
			assertEquals(1, tempClazz.getSubclasses().size());
			// Test each class has it's proper subclass
			assertEquals(PREFIX + subClassNames[i], tempClazz.getSubclasses()
					.get(0).getClassName());

		}
	}

	public Hashtable<String, Clazz> makeFileList() {
		Hashtable<String, Clazz> classes = new Hashtable<String, Clazz>();

		for (String name : classNames) {
			classes.put(name, new Clazz(name));
		}
		return classes;
	}
}