package ParserTBA;

import java.util.ArrayList;
import java.util.Hashtable;

public class Codebase {

	public int files;
	public static final String[] vars = { "int", "String", "char", "double",
			"boolean", "list", "set", "array" };
	ArrayList<Clazz> classes;

	public Codebase(ArrayList<Clazz> classes) {
		this.classes = classes;
	}

	static class Clazz {
		int numImports;
		String superclass;
		ArrayList<Methodz> methods;
		String className;

		public Clazz() {

		}

	}

	static class Methodz {
		int sloc;
		int parameters;
		String methodName;

		public Methodz() {

		}

	}

	static class VarTable {

		Hashtable<String, Integer> varTable = new Hashtable<String, Integer>(
				vars.length);

		public VarTable() {
			for (String var : vars)
				varTable.put(var, 0);
		}

		public void increment(String var) {
			varTable.put(var, varTable.get(var) + 1);
		}
	}
}
