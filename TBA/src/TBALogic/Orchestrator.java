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

	public static void main(String[] args) {
		// Generate a list of files to parse
		StupidContainer classFileInformation = null;
		try {
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
			codebase.determineScales(classFileInformation.commitCounts);
			codebase.organizeHierarchy();

			// Send structure to output generator
			Planetizer planetizer = new Planetizer(codebase);
			planetizer.celestialize();

		} catch (Exception e) {
			JFrame frame = new JFrame();
			JOptionPane
					.showMessageDialog(frame,
							"An unrecoverable error occured, GalacticTBA Mission Aborted");
			frame.dispose();
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
		frame.dispose();

		if (selection == 1)
			return;

		try {
			if (isOSWindows) {
				String userDir = System.getProperty("user.dir");
				Runtime.getRuntime().exec(
						"cmd /C " + userDir + "\\scripts\\cleanup.sh "
								+ parsingResults.codeRoot + "/");
			} else {
				Runtime.getRuntime().exec(
						"./scripts/cleanup.sh " + parsingResults.codeRoot + "/"
								+ parsingResults.folderName);
			}
		} catch (Exception e) {
			frame = new JFrame();
			JOptionPane.showMessageDialog(frame,
					"There was an error deleting your repository");
			frame.dispose();
			e.printStackTrace();
		}
	}
}
