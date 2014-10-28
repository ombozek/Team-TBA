package ParserTBA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import ParserTBA.Codebase.Clazz;
import ParserTBA.Codebase.Methodz;
import ParserTBA.Codebase.VarTable;
import ParserTBA.Codebase.types;

public class Parser {

	private ArrayList<String> files;
	private FileReader reader;
	private BufferedReader buff;
	private Clazz currentClazz;

	public Parser(ArrayList<String> files) {
		this.files = files;
	}

	/**
	 * Runs the parser on every file passed in by our codebase
	 * 
	 * @return Codebase object representing entire codebase structure
	 */
	public Codebase parse() {
		ArrayList<Clazz> classes = new ArrayList<Clazz>(files.size());

		// Parse every file
		for (String file : files) {
			try {
				classes.add(parseClass(new File(file)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new Codebase(classes);
	}

	/**
	 * Parses a class by counting variables and gathering information on methods
	 * 
	 * @param file
	 *            the current file being parsed
	 * @return Clazz object filled with representative data
	 * @throws Exception
	 *             I/O Exceptions
	 */
	public Clazz parseClass(File file) throws Exception {
		Clazz clazz = new Clazz();
		clazz.className = file.getName().substring(0,
				file.getName().indexOf("."));
		currentClazz = clazz;

		// Spin up readers
		reader = new FileReader(file);
		buff = new BufferedReader(reader);
		String line;

		// go through entire file
		while ((line = buff.readLine()) != null) {
			// get rid of white space and tokenize
			line = line.trim();
			String[] tokens = line.split(" ");

			switch (tokens[0]) {
			case "import":
				currentClazz.numImports++;
				break;
			default:
				countKeywords(tokens, 0, currentClazz.varTable);
				break;
			}
		}

		// Do some cleanup, close readers and such
		buff.close();
		reader.close();
		currentClazz = null;
		return clazz;
	}

	/**
	 * Reads in and parses a class declaration
	 * 
	 * @param currLine
	 *            The tokenized version of the current line being parsed
	 * @param currIndex
	 *            0-based index of the keyword "class"
	 */
	public void parseClassDeclaration(String[] currLine, int currIndex) {
		// looks for a superclass by searching for "extends" in the 4th element
		// in the class declaration
		// public class <classname> extends <superclass>
		if (currLine[currIndex + 2].equals("extends")) {
			currentClazz.superclass = currLine[currIndex + 3];
		}
	}

	/**
	 * Reads in and parses a method signature
	 * 
	 * @param currLine
	 *            The tokenized version of the current line being parsed
	 * @param currIndex
	 *            the current index in the token array
	 * @return
	 * @throws Exception
	 *             I/O Exceptions
	 */
	public int parseMethodSignature(String[] currLine, int currIndex)
			throws Exception {
		int paramCount = 0;
		for (int index = currIndex; index < currLine.length; index++) {
			// Get rid of unnecessary tokens
			if (currLine[index].contains(",") || currLine[index].contains("<")) {
				continue;
			}
			// If we close the parameter section, we're done
			else if (currLine[index].contains(")")) {
				return paramCount;
			}
			// count the parameters in the method signature
			else {
				paramCount++;
			}
		}

		// If our params take over one line, call self recursively
		String line = null;
		while (line == null) {
			line = buff.readLine();
		}

		return parseMethodSignature(line.trim().split(" "), 0) + paramCount;
	}

	/**
	 * Parses a method by looking for parameters, name and lines of code.
	 * Terminate on occurrence of closed bracket "}"
	 * 
	 * @param currLine
	 *            The tokenized version of the current line being parsed
	 * @param currIndex
	 *            The 0-based index of the method name in currLine
	 * @throws Exception
	 *             I/O Exceptions
	 */

	public void parseMethodBody(String[] currLine, int currIndex)
			throws Exception {
		Methodz method = new Methodz();
		String[] tokens = currLine;
		String line;

		// Extract method name
		method.methodName = tokens[currIndex].substring(0,
				tokens[currIndex].indexOf("("));

		// Extract method parameters
		method.parameters = parseMethodSignature(tokens, currIndex);

		int curlyCounter = 1;
		while ((line = buff.readLine()) != null) {
			tokens = line.trim().split(" ");
			if (tokens.length == 0)
				continue;
			method.sloc++;

			// Track curly braces to find end of method
			for (String token : tokens) {
				if (token.contains("{"))
					curlyCounter++;
				else if (token.contains("}"))
					curlyCounter--;
			}

			// If we close all of our curly braces, we've reached the end of our
			// method
			if (curlyCounter == 0)
				break;
		}

		currentClazz.addMethod(method);
		return;
	}

	/**
	 * Searches for and counts keywords from inputed line
	 * 
	 * @param currLine
	 *            The tokenized version of the current line being parsed
	 * @param currIndex
	 *            the current index of the token array
	 * @param table
	 *            the variable table we're populating through parsing
	 * @throws Exception
	 *             I/O Exceptions
	 */
	public void countKeywords(String[] currLine, int currIndex, VarTable table)
			throws Exception {
		switch (currLine[currIndex]) {
		// These are either methods or variables
		case "public":
		case "private":
		case "static":
			methodOrVar(currLine, currIndex + 1, table);
			break;

		// This can only be a variable
		case "final":
			if (currLine.length > currIndex)
				countKeywords(currLine, currIndex + 1, table);
			break;

		// Used for identifying booleans
		case "boolean":
		case "Boolean":
			table.increment(types.BOOLEAN,
					countDeclaredVariables(currLine, currIndex));
			break;

		// Used for identifying ints
		case "int":
		case "Integer":
			table.increment(types.INT,
					countDeclaredVariables(currLine, currIndex));
			break;

		// Used for identifying doubles
		case "double":
		case "Double":
			table.increment(types.DOUBLE,
					countDeclaredVariables(currLine, currIndex));
			break;

		// Used for identifying Strings
		case "String":
			table.increment(types.STRING,
					countDeclaredVariables(currLine, currIndex));
			break;

		// Used for identifying sets and lists
		default:
			if (currLine[currIndex].contains("list")
					|| currLine[currIndex].contains("List")) {
				table.increment(types.LIST);
			} else if (currLine[currIndex].contains("set")
					|| currLine[currIndex].contains("Set")) {
				table.increment(types.SET);
			}
		}
	}

	/**
	 * Check whether the current structure is a method or variable declaration
	 * 
	 * @param currLine
	 *            The tokenized version of the current line being parsed
	 * @param currIndex
	 *            the current index in the token array
	 * @param table
	 *            the variable table we're populating through parsing
	 * @throws Exception
	 *             I/O Exceptions
	 */
	public void methodOrVar(String[] currLine, int currIndex, VarTable table)
			throws Exception {
		// Ensure we won't overflow
		if (currLine.length < currIndex) {
			return;
		}

		// If it's a method, parse it
		if (currLine[currIndex].contains("(")) {
			parseMethodBody(currLine, currIndex);
		} else if (currLine[currIndex + 1].contains("(")) {
			parseMethodBody(currLine, currIndex + 1);
		} else if (currLine.length < currIndex + 2
				&& currLine[currIndex + 2].contains("(")) {
			parseMethodBody(currLine, currIndex + 2);
		}
		// Check if it's a class declaration
		else if (currLine[currIndex].equals("class")) {
			parseClassDeclaration(currLine, currIndex);
		}
		// Otherwise continue counting types if relevant
		else {
			countKeywords(currLine, currIndex, table);
		}
	}

	/**
	 * Counts the number of declared objects in a given line. Terminates on
	 * occurrence of a semicolon ";"
	 * 
	 * @param currLine
	 *            The tokenized version of the current line being parsed
	 * @param currIndex
	 *            the current index in the token array
	 * @return
	 * @throws Exception
	 *             I/O Exceptions
	 */
	public int countDeclaredVariables(String[] currLine, int currIndex)
			throws Exception {
		int commaCount = 1;
		for (int index = currIndex; index < currLine.length; index++) {
			// Get rid of unnecessary tokens
			if (currLine[index].contains(",")) {
				commaCount++;
				continue;
			}
			// If we close the parameter section, we're done
			else if (currLine[index].contains(";")) {
				return commaCount;
			}
		}

		// If our params take over one line, call self recursively
		String line = null;
		while (line == null) {
			line = buff.readLine();
		}

		return countDeclaredVariables(line.trim().split(" "), 0) + commaCount
				- 1;
	}

	// ------------------
	// TEST METHODS ONLY!
	// ------------------

	/**
	 * SHOULD ONLY BE USED IN TEST CLASSES
	 */
	public void setBufferedReaderForTestOnly(BufferedReader reader) {
		this.buff = reader;
	}

	/**
	 * SHOULD ONLY BE USED IN TEST CLASSES
	 */
	public BufferedReader getBufferedReaderForTestOnly() {
		return this.buff;
	}

	/**
	 * SHOULD ONLY BE USED IN TEST CLASSES
	 */
	public Clazz getCurrentClassForTestOnly() {
		return this.currentClazz;
	}

	/**
	 * SHOULD ONLY BE USED IN TEST CLASSES
	 */
	public void setCurrentClassForTestOnly(Clazz clazz) {
		this.currentClazz = clazz;
	}

}
