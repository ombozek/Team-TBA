package ParserTBA;

import java.io.File;
import java.util.ArrayList;

import ParserTBA.Codebase.Clazz;
import ParserTBA.Codebase.Methodz;

public class Parser {

	public ArrayList<String> files;

	public Parser(ArrayList<String> files) {
		this.files = files;
	}

	public Codebase parse() {
		ArrayList<Clazz> classes = new ArrayList<Clazz>(files.size());
		for (String file : files) {
			classes.add(parseClass(new File(file)));
		}

		return new Codebase(classes);
	}

	// Clazz is because clazz is a java keyword
	public Clazz parseClass(File file) {
		Clazz clazz = new Clazz();
		
		/**
		 * Put tokenizing & parsing logic here
		 */
		
		return clazz;
	}

	// Methodz is because Method is already a java class
	public Methodz parseMethod() {
		Methodz method = new Methodz();
		
		/**
		 * Put tokenizing parsing logic here
		 */

		return method;
	}

}
