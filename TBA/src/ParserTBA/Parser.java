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

	// Clazz is because clazz is a java keyword
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
				typeCount(tokens, 0, currentClazz.varTable);
				break;
			}
		}

		// Do some cleanup, close readers and such
		buff.close();
		reader.close();
		currentClazz = null;
		return clazz;
	}

	public void parseMethod(String[] currLine, int param) throws Exception {
		Methodz method = new Methodz();
		String[] tokens = currLine;
		String line;

		// Extract method name
		method.methodName = tokens[param].substring(0,
				tokens[param].indexOf("("));

		// Extract method parameters
		method.parameters = methodSignature(tokens, param);

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

	private void typeCount(String[] line, int index, VarTable table)
			throws Exception {
		switch (line[index]) {
		// These are either methods or variables
		case "public":
		case "private":
		case "static":
			methodOrVar(line, index + 1, table);
			break;

		// This can only be a variable
		case "final":
			if (line.length > index)
				typeCount(line, index + 1, table);
			break;

		// Used for identifying booleans
		case "boolean":
		case "Boolean":
			table.increment(types.BOOLEAN, commaCounter(line, index));
			break;

		// Used for identifying ints
		case "int":
		case "Integer":
			table.increment(types.INT, commaCounter(line, index));
			break;

		// Used for identifying doubles
		case "double":
		case "Double":
			table.increment(types.DOUBLE, commaCounter(line, index));
			break;

		// Used for identifying Strings
		case "String":
			table.increment(types.STRING, commaCounter(line, index));
			break;

		// Used for identifying sets and lists
		default:
			if (line[index].contains("list") || line[index].contains("List")) {
				table.increment(types.LIST);
			} else if (line[index].contains("set")
					|| line[index].contains("Set")) {
				table.increment(types.SET);
			}
		}
	}

	private void methodOrVar(String[] line, int index, VarTable table)
			throws Exception {
		// Ensure we won't overflow
		if (line.length < index) {
			return;
		}

		// If it's a method, parse it
		if (line[index].contains("(")) {
			parseMethod(line, index);
		} else if (line[index + 1].contains("(")) {
			parseMethod(line, index + 1);
		} else if (line.length < index + 2 && line[index + 2].contains("(")) {
			parseMethod(line, index + 2);
		}
		// Check if it's a class declaration
		else if (line[index].equals("class")) {
			classDeclaration(line, index);
		}
		// Otherwise continue counting types if relevant
		else {
			typeCount(line, index, table);
		}
	}

	private void classDeclaration(String[] tokens, int index) {
		// looks for a superclass by searching for "extends" in the 4th element
		// in the class declaration
		// public class <classname> extends <superclass>
		if (tokens[index + 2].equals("extends")) {
			currentClazz.superclass = tokens[index + 3];
		}
	}

	private int methodSignature(String[] tokens, int params) throws Exception {
		int paramCount = 0;
		for (int index = params; index < tokens.length; index++) {
			// Get rid of unnecessary tokens
			if (tokens[index].contains(",") || tokens[index].contains("<")) {
				continue;
			}
			// If we close the parameter section, we're done
			else if (tokens[index].contains(")")) {
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

		return methodSignature(line.trim().split(" "), 0) + paramCount;
	}

	public int commaCounter(String[] tokens, int params) throws Exception {
		int commaCount = 1;
		for (int index = params; index < tokens.length; index++) {
			// Get rid of unnecessary tokens
			if (tokens[index].contains(",")) {
				commaCount++;
				continue;
			}
			// If we close the parameter section, we're done
			else if (tokens[index].contains(";")) {
				return commaCount;
			}
		}

		// If our params take over one line, call self recursively
		String line = null;
		while (line == null) {
			line = buff.readLine();
		}

		return commaCounter(line.trim().split(" "), 0) + commaCount - 1;
	}

	// TEST METHODS ONLY
	
	public void setBufferedReaderForTestOnly(BufferedReader reader) {
		this.buff = reader;
	}
	
	public BufferedReader getBufferedReaderForTestOnly() {
		return this.buff;
	}
	
	public Clazz getCurrentClassForTestOnly() {
		return this.currentClazz;
	}
	
	public void setCurrentClassForTestOnly(Clazz clazz) {
		this.currentClazz = clazz;
	}
	
}
