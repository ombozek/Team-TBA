package ParserTBA;

import java.util.ArrayList;
import java.util.Hashtable;

import GalacticTBA.ETConst.Range;

public class Codebase {

	public enum types {
		INT, STRING, CHAR, DOUBLE, BOOLEAN, LIST, SET, ARRAY
	}

	private final Hashtable<String, Clazz> classes;
	private Range importRange;
	private Range slocRange;
	private Range paramRange;
	private int[] rangeHolder;

	public Codebase(Hashtable<String, Clazz> classes) {
		this.classes = classes;
	}

	public Hashtable<String, Clazz> getClasses() {
		return classes;
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

	public void determineScales() {
		rangeHolder = new int[6];
		for (Clazz clazz : classes.values()) {
			setMinMax(clazz.numImports, 0, 1);
			setMinMax(clazz.getSloc(), 2, 3);
			for (Methodz method : clazz.getMethods()) {
				setMinMax(method.parameters, 4, 5);
			}
		}
		importRange = new Range(rangeHolder[0], rangeHolder[1]);
		slocRange = new Range(rangeHolder[2], rangeHolder[3]);
		paramRange = new Range(rangeHolder[4], rangeHolder[5]);
		rangeHolder = null;
	}

	public void setMinMax(int input, int min, int max) {
		if (rangeHolder[min] < 0 || rangeHolder[max] < 0) {
			rangeHolder[min] = input;
			rangeHolder[max] = input;
		}
		if (input < rangeHolder[min])
			rangeHolder[min] = input;
		if (input > rangeHolder[max])
			rangeHolder[max] = input;
	}

	public Range getParamRange() {
		return paramRange;
	}

	public Range getSlocRange() {
		return slocRange;
	}

	public Range getImportRange() {
		return importRange;
	}

	// ----------
	// Nested Class: Clazz
	// ----------

	public static class Clazz {
		public int numImports;
		private Clazz superclass;
		public String superclassName;
		private ArrayList<Clazz> subclasses;
		private ArrayList<Methodz> methods = new ArrayList<Methodz>();
		private String className;
		private int sloc = 0;

		VarTable varTable = new VarTable();

		public Clazz(String className) {
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		public void addMethod(Methodz methodz) {
			sloc += methodz.sloc;
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

		public int getSloc() {
			return sloc;
		}

		public Clazz getSuperclass() {
			return this.superclass;
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

	// ----------
	// Nested Class: Methodz
	// ----------

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

	// ----------
	// Nested Class: VarTable
	// ----------

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
