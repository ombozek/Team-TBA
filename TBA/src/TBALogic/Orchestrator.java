package TBALogic;

import java.util.ArrayList;

import ParserTBA.Codebase;
import ParserTBA.Parser;

public class Orchestrator {
	public static final int maxFiles = 30;
	public static final int minFiles = 5;

	public static void main(String[] args) {
		// Generate a list of files to parse
		ArrayList<String> sourceFiles;
		sourceFiles = new TBALogic().generateFileList();
		// Parse the files
		Codebase codebase = new Parser(sourceFiles).parse();
		System.out.println(codebase.toString());
		// Send structure to output generator
		
		System.exit(0);
	}
}
