package ParserTBA;

import java.util.ArrayList;
import java.util.Hashtable;

public class Codebase {

	public enum types {
		INT, STRING, CHAR, DOUBLE, BOOLEAN, LIST, SET, ARRAY
	}

	Hashtable<String, Clazz> classes;

	public Codebase(Hashtable<String, Clazz> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[Codebase: ");
		for (Clazz clazz : classes.values()) {
			builder.append(clazz.toString());
			builder.append(", ");
		}
		return builder.toString().substring(0, builder.length() - 2) + "]";
	}

	public static class Clazz {
		public int numImports;
		private Clazz superclass;
		public String superclassName;
		private ArrayList<Clazz> subclasses;
		private ArrayList<Methodz> methods = new ArrayList<Methodz>();
		private String className;

		VarTable varTable = new VarTable();

		public Clazz(String className) {
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		public void addMethod(Methodz methodz) {
			methods.add(methodz);
		}

		public ArrayList<Methodz> getMethods() {
			return methods;
		}

		public void addSubclass(Clazz subclass) {
			if (subclasses == null)
				subclasses = new ArrayList<Clazz>();
			subclasses.add(subclass);
			subclass.superclass = this;

		}

		public ArrayList<Clazz> getSubclasses() {
			return subclasses;
		}
		
		// TEST METHODS ONLY
		
		public void SetClassName(String name)
		{
			className = name;
		}
		
		public String getSuperClassNameForTestOnly() {
			if(superclass == null)
				return "";
			
			return this.superclass.className;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[ClassName: " + className);
			builder.append(", Imports: " + numImports);
			builder.append(", Superclass: " + superclassName);
			builder.append(", Variables: " + varTable.toString());
			builder.append(", Methods: [");

			StringBuilder methodBuilder = new StringBuilder();
			for (Methodz method : methods) {
				methodBuilder.append(method.toString() + ", ");
			}
			if (methodBuilder.length() > 2)
				builder.append(methodBuilder.toString().substring(0,
						methodBuilder.length() - 2)
						+ "], ");

			if (subclasses != null && !subclasses.isEmpty()) {
				StringBuilder subBuilder = new StringBuilder();
				builder.append("Subclasses: [");
				for (Clazz subclass : subclasses) {
					subBuilder.append(subclass.toString() + ", ");
				}
				builder.append(subBuilder.toString().substring(0,
						subBuilder.length() - 2));
				builder.append("], ");
			}

			return builder.toString().substring(0, builder.length() - 2) + "]";
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
