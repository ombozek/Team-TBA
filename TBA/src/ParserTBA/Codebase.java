package ParserTBA;

import java.util.ArrayList;

public class Codebase {

	public int files;
	public static final String[] vars = { "int", "string", "char", "double",
			"boolean", "list", "set", "array" };

	public enum types {
		INT, STRING, CHAR, DOUBLE, BOOLEAN, LIST, SET, ARRAY
	}

	ArrayList<Clazz> classes;

	public Codebase(ArrayList<Clazz> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[Codebase: ");
		for (Clazz clazz : classes) {
			builder.append(clazz.toString());
			builder.append(", ");
		}
		return builder.toString().substring(0, builder.length() - 2) + "]";
	}

	public static class Clazz {
		int numImports;
		String superclass;
		ArrayList<Methodz> methods = new ArrayList<Methodz>();
		String className;
		VarTable varTable = new VarTable();

		public void addMethod(Methodz methodz) {
			methods.add(methodz);
		}
		
		public ArrayList<Methodz> getMethods(){
			return  methods;
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

	public static class Methodz {
		public int sloc = 0;
		public int parameters = 0;
		public String methodName = "";

		@Override
		public String toString() {
			return "[MethodName: " + methodName + ", parameters: " + parameters
					+ ", SLOC: " + sloc + "]";
		}
	}

	public static class VarTable {

		int[] varTable;

		public VarTable() {
			varTable = new int[types.values().length];
		}

		public void increment(Enum<types> type) {
			increment(type, 1);
		}

		public void increment(Enum<types> type, int times) {
			varTable[type.ordinal()] += times;
		}

		public int[] getTable() {
			return varTable;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();

			builder.append("[");
			for (Enum<types> type : types.values()) {
				builder.append(type.name().toLowerCase() + ": "
						+ varTable[type.ordinal()] + ", ");
			}

			return builder.toString().substring(0, builder.length() - 2) + "]";
		}
	}
}
