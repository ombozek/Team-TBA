package TBALogic;

import ParserTBA.Codebase;
import ParserTBA.Parser;
import TBALogic.TBALogic.StupidContainer;
import TBALogic.TBALogic.StupidContainer.LogOp;

public class Orchestrator {
	public static final int maxFiles = 30;
	public static final int minFiles = 5;

	public static void main(String[] args) {
		// Generate a list of files to parse
		StupidContainer parsingResults;
		try {
			parsingResults = new TBALogic().generateFileList();
			if (parsingResults == null) {
				System.exit(0);
			}
			for (LogOp op : parsingResults.gitLog) {
				System.out.println(op);
			}
			// Parse the files
			Codebase codebase = new Parser(parsingResults.sourceFiles).parse();
			System.out.println(codebase.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Send structure to output generator

		System.exit(0);
	}
}
