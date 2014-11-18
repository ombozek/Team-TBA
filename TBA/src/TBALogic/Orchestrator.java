package TBALogic;

import java.io.IOException;

import ParserTBA.Codebase;
import ParserTBA.Parser;
import TBALogic.TBALogic.StupidContainer;

public class Orchestrator {
	public static final int maxFiles = 30;
	public static final int minFiles = 5;
	private static boolean isOSWindows;
	private static String userDir;

	public static void main(String[] args) {
		// Generate a list of files to parse
		StupidContainer parsingResults = null;
		try {
			userDir = System.getProperty("user.dir");

			isOSWindows = System.getProperty("os.name").toLowerCase()
					.contains("windows");

			parsingResults = new TBALogic(isOSWindows).generateFileList();

			if (parsingResults == null) {
				System.exit(0);
			}

			// Parse the files
			Codebase codebase = new Parser(parsingResults.sourceFiles).parse();
			System.out.println(codebase.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (parsingResults == null || parsingResults.folderName == null)
				System.exit(0);

			try {
				if (isOSWindows) {
					Runtime.getRuntime().exec(
							"cmd /C " + userDir + "\\scripts\\cleanup.sh "
									+ parsingResults.codeRoot + "/");
				} else {
					Runtime.getRuntime().exec(
							"./scripts/cleanup.sh " + parsingResults.codeRoot
									+ "/" + parsingResults.folderName);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Send structure to output generator
		System.exit(0);
	}
}
