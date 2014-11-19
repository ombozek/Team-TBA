package TBALogic;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import GalacticTBA.Planetizer;
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
		StupidContainer classFileInformation = null;
		try {
			userDir = System.getProperty("user.dir");

			isOSWindows = System.getProperty("os.name").toLowerCase()
					.contains("windows");

			classFileInformation = new TBALogic(isOSWindows).generateFileList();

			if (classFileInformation == null) {
				System.exit(0);
			}

			// Parse the files
			Codebase codebase = new Parser(classFileInformation.sourceFiles)
					.parse();
			// Calculate numbers for visual output
			codebase.determineScales();

			// Send structure to output generator
			Planetizer planetizer = new Planetizer(codebase);
			planetizer.celestialize();
			System.out.println(codebase.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} finally {
			if (classFileInformation.folderName != null)
				cleaupGracefully(classFileInformation);
		}
	}

	private static void cleaupGracefully(StupidContainer parsingResults) {

		// Otherwise delete the local repo
		// TODO maybe make deleting repo an option?
		Object[] options = { "Delete Cloned Repo", "Leave Cloned Repo" };

		JFrame frame = new JFrame();
		int selection = JOptionPane.showOptionDialog(frame,
				"Would you like to delete the cloned Github Repository???",
				"GalacticTBA", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (selection == 1)
			return;

		
		System.out.println("I WOULD'VE FUCKED UP YOUR SHIT HOMIE");
		// try {
		// if (isOSWindows) {
		// Runtime.getRuntime().exec(
		// "cmd /C " + userDir + "\\scripts\\cleanup.sh "
		// + parsingResults.codeRoot + "/");
		// } else {
		// Runtime.getRuntime().exec(
		// "./scripts/cleanup.sh " + parsingResults.codeRoot + "/"
		// + parsingResults.folderName);
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
