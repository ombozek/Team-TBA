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
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("[Codebase: ");
		for (Clazz clazz : classes) {
			builder.append(clazz.toString());
		}
		
		builder.append("]");
		
		return builder.toString();
	}

	static class Clazz {
		int numImports;
		String superclass;
		ArrayList<Methodz> methods = new ArrayList<Methodz>();
		String className;
		VarTable varTable = new VarTable();

		public void addMethod(Methodz methodz) {
			methods.add(methodz);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[ClassName: " + className);
			builder.append(", Superclass: " + superclass);
			builder.append(", Imports: " + numImports);
			builder.append(", Variables: " + varTable.toString());
			builder.append(", Methods: [");
			
			for (Methodz method : methods) {
				builder.append(method.toString() + ", ");
			}
			
			return builder.toString().substring(0, builder.length() - 2) + "]]";
		}

	}

	static class Methodz {
		int sloc = 0;
		int parameters = 0;
		String methodName = "";

		@Override
		public String toString() {
			return "[MethodName: " + methodName + ", parameters: " + parameters
					+ ", SLOC: " + sloc + "]";
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

		public Hashtable<String, Integer> getTable() {
			return varTable;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			
			builder.append("[");
			for (String var : vars) {
				builder.append(var + ": " + varTable.get(var) + ", ");
			}
			
			return builder.toString().substring(0, builder.length() - 2) + "]";
		}
	}
}
