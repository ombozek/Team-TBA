package ParserTBA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import ParserTBA.Codebase.Clazz;
import ParserTBA.Codebase.Methodz;
import ParserTBA.Codebase.VarTable;

public class Parser {

	public ArrayList<String> files;
	public FileReader reader;
	public BufferedReader buff;
	public Clazz currentClazz;

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
		clazz.className = file.getName().substring(0, file.getName().indexOf("."));
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
			case "extends":
				// TODO: get superclass out of there
				break;
			case "implements":
				// TODO: get interfaces out of there
				break;
			default:
				typeCount(tokens, 0, currentClazz.varTable);
				break;
			}
		}

		buff.close();
		reader.close();
		currentClazz = null;
		return clazz;
	}

	public void parseMethod(String[] currLine, int param) throws Exception {
		Methodz method = new Methodz();
		String[] tokens = currLine;
		String line;
		int curlyCounter = 1;

		method.methodName = tokens[param].substring(0,
				tokens[param].indexOf("("));

		// TODO: find method parameters

		while ((line = buff.readLine()) != null) {
			tokens = line.trim().split(" ");
			method.sloc++;

			// Track curly braces
			for (String token : tokens) {
				if (token.contains("{"))
					curlyCounter++;
				else if (token.contains("}"))
					curlyCounter--;
			}

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
			if (line.length < index)
				typeCount(line, index + 1, table);
			break;

		// Used for identifying booleans
		case "boolean":
		case "Boolean":
			table.increment("boolean");
			break;

		// Used for identifying ints
		case "int":
		case "Integer":
			table.increment("int");
			break;

		// Used for identifying Strings
		case "String":
			table.increment("String");
			break;

		// Used for identifying sets and lists
		default:
			if (line[index].contains("list") || line[index].contains("List")) {
				table.increment("list");
			} else if (line[index].contains("set")
					|| line[index].contains("Set")) {
				table.increment("set");
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
		}
		// Otherwise continue counting types if relevant
		else {
			typeCount(line, index, table);
		}
	}
}
