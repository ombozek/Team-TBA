package TBALogic;

import java.util.ArrayList;

import ParserTBA.Codebase;
import ParserTBA.Parser;

public class Orchestrator {
	public static final int maxFiles = 30;
	public static final int minFiles = 5;

	public static void main(String[] args) {
		ArrayList<String> sourceFiles;
		sourceFiles = new TBALogic().generateFileList();
		Codebase codebase = new Parser(sourceFiles).parse();

		System.exit(0);
	}
}
