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
	private Range commitRange;
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

	public void determineScales(Hashtable<String, Integer> commitCounts) {
		rangeHolder = new int[8];
		Integer commitCount;
		for (Clazz clazz : classes.values()) {
			setMinMax(clazz.numImports, 0, 1);
			setMinMax(clazz.getSloc(), 2, 3);
			for (Methodz method : clazz.getMethods()) {
				setMinMax(method.parameters, 4, 5);
			}
			commitCount = commitCounts.get(clazz.className);
			if (commitCount == null)
				commitCount = new Integer(1);
			clazz.setNumCommits(commitCount);
			setMinMax(commitCount, 6, 7);
			clazz.determineScales();
		}
		importRange = new Range(rangeHolder[0], rangeHolder[1]);
		slocRange = new Range(rangeHolder[2], rangeHolder[3]);
		paramRange = new Range(rangeHolder[4], rangeHolder[5]);
		commitRange = new Range(rangeHolder[6], rangeHolder[7]);
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

	public Range getCommitRange() {
		return commitRange;
	}

	/**
	 * Goes through every element and if applicable adds subclasses to their
	 * parent class
	 * 
	 * @param clazzez
	 *            the HashTable of all of the encountered classes
	 */
	public void organizeHierarchy() {
		Clazz tempClazz;
		ArrayList<String> classesToRemove = new ArrayList<String>();
		for (Clazz clazz : classes.values()) {
			if (clazz.superclassName != null) {
				tempClazz = classes.get(clazz.superclassName);

				// In case we extend some external superclass
				if (tempClazz == null)
					continue;

				tempClazz.addSubclass(clazz);
				classesToRemove.add(clazz.getClassName());
			}
		}

		for (String clazzName : classesToRemove) {
			classes.remove(clazzName);
		}
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
		private int numCommits;
		public final VarTable varTable;

		public Clazz(String className) {
			this.className = className;
			varTable = new VarTable();
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

		public int getNumCommits() {
			return numCommits;
		}

		public void setNumCommits(int numCommits) {
			this.numCommits = numCommits;
		}

		public void determineScales() {
			this.varTable.determineScales();
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

		private int[] varTable;
		private Range varRange;

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

		public Range getVarRange() {
			return varRange;
		}

		public void determineScales() {
			int min = varTable[0], max = varTable[0];
			for (int i : varTable) {
				if (min > i)
					min = i;
				if (max < i)
					max = i;
			}
			varRange = new Range(min, max);
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
