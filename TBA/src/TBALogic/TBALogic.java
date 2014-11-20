package TBALogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TBALogic {

	private final String EXTENSION = ".java";
	private final int ATTEMPTS = 3;
	private final int MAX_FILES = 50;
	private final int MIN_FILES = 5;
	private final String GIT = ".git";
	private final boolean isOSWindows;
	private String gitDir;
	String folderName;
	Hashtable<String, Integer> commitCounts;

	public TBALogic(boolean isOSWindows) {
		this.isOSWindows = isOSWindows;
	}

	/**
	 * Finds all java files in the codebase
	 * 
	 * @return an arraylist of all java files in the codebase
	 * @throws IOException
	 */
	public StupidContainer generateFileList() throws Exception {
		int tries = 0;
		StupidContainer parsingResults;

		do {
			tries++;
			parsingResults = populateFileList();

			if (parsingResults.sourceFiles == null)
				continue;

			if (checkValidCodebase(parsingResults.sourceFiles.size()))
				break;

		} while (tries < ATTEMPTS);

		if (tries > ATTEMPTS)
			return null;

		return parsingResults;
	}

	/**
	 * Prompts for codebase root dir and then finds all java files in
	 * subdirectories
	 * 
	 * @return an arraylist of all of the absolute paths to the files in the
	 *         codebase
	 * @throws Exception
	 */
	private StupidContainer populateFileList() throws Exception {

		String root = getCodeRepo();

		if (root == null) {
			return null;
		}

		ArrayList<String> files = new ArrayList<String>();
		searchDFS(new File(root).listFiles(), files);

		String dir = System.getProperty("user.dir");

		if (gitDir == null)
			return new StupidContainer(files, root);

		commitCounts = new Hashtable<String, Integer>();
		Process p = null;

		if (isOSWindows) {
			p = Runtime.getRuntime().exec(
					"cmd /C " + dir + "\\scripts\\gitlog.sh " + gitDir);
		} else {
			p = Runtime.getRuntime().exec("./scripts/gitlog.sh " + gitDir);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		String line = null, name;
		Integer count;
		while ((line = in.readLine()) != null) {
			if (line.contains(".java")) {
				name = extractName(line);
				count = commitCounts.get(name);
				if (count == null)
					count = new Integer(0);
				commitCounts.put(name, count + 1);
			}
		}

		in.close();
		return new StupidContainer(files, root, folderName, commitCounts);
	}

	/**
	 * Extracts the filename from a git log ADD/DELETE line
	 * 
	 * @param line
	 *            the line containing the name of the file
	 * @return the name of the file
	 */
	public String extractName(String line) {
		// Linux case
		if (line.contains("/")) {
			return line.substring(line.lastIndexOf("/") + 1, line.indexOf("."));
		}
		// Root dir case
		return line.substring(1, line.indexOf(".")).trim();
	}

	/**
	 * Checks whether the codebase has an acceptable number of files
	 * 
	 * @param numFiles
	 *            number of files in the codebase
	 * @return true if the codebase is valid
	 */
	private boolean checkValidCodebase(int numFiles) {
		JFrame frame;
		if (numFiles > MAX_FILES) {
			frame = new JFrame();
			JOptionPane.showMessageDialog(frame,
					"There are too many files in this codebase. Your codebase has "
							+ numFiles + " while we allow a maximum of "
							+ MAX_FILES);

		} else if (numFiles < MIN_FILES) {
			frame = new JFrame();
			JOptionPane.showMessageDialog(frame,
					"There are too few files in this codebase. Your codebase has "
							+ numFiles + " while we allow a minimum of "
							+ MIN_FILES);
		} else {
			return true;
		}
		frame.dispose();
		return false;
	}

	/**
	 * Depth first recursive directory search for java files
	 * 
	 * @param files
	 *            an array of the files in the current directory
	 * @param list
	 *            the arraylist of files to be populated
	 */
	private void searchDFS(File[] files, ArrayList<String> list) {

		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getAbsolutePath().endsWith(GIT)) {
					gitDir = file.getAbsolutePath();
				}
				searchDFS(file.listFiles(), list);
			} else {
				if (file.getName().endsWith(EXTENSION)) {
					list.add(file.getAbsolutePath());
				}
			}
		}
	}

	// TODO make this work
	private String getCodeRepo() throws Exception {
		Object[] options = { "Local Git Repository", "Github URI" };

		JFrame frame = new JFrame();
		int selection = JOptionPane.showOptionDialog(frame,
				"Please select your type of codebase", "GalacticTBA",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);

		if (selection == 1) {
			folderName = "cpsc410_" + new Date().getTime();
			String baseDir = getCodeRoot(true);

			String githubURI = (String) JOptionPane.showInputDialog(frame,
					"Galactic TBA:\n" + "Please enter Github URI",
					"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, null,
					null);

			String dir = System.getProperty("user.dir");
			Process p;
			if (isOSWindows) {
				p = Runtime.getRuntime().exec(
						"cmd /C " + dir + "\\scripts\\gitclone.sh " + githubURI
								+ " " + baseDir + "\\" + folderName);
			} else {
				p = Runtime.getRuntime().exec(
						dir + "/scripts/gitclone.sh " + folderName + "/"
								+ baseDir);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while (in.readLine() != null) {
				Thread.sleep(1000);
			}
			in.close();
			return baseDir + "\\" + folderName;
		} else if (selection != 0) {
			System.exit(0);
		}

		return getCodeRoot(false);
	}

	/**
	 * JFileChooser to select codebase root directory JFileChooser code adapted
	 * from: http://www.rgagnon.com/javadetails/java-0370.html
	 * 
	 * @param cloningRepo
	 *            Whether or not we're cloning our repo from github or a
	 *            different
	 * @return the root directory of the codebase to parse
	 */
	private String getCodeRoot(boolean cloningRepo) {
		String rootDir;
		JFrame frame = new JFrame();
		JFileChooser chooser = new JFileChooser();
		// Set proper options for root selector
		chooser.setCurrentDirectory(new File(".."));

		if (cloningRepo) {
			chooser.setDialogTitle("Please Select Directory to Create Temporary Git Repo");
		} else {
			chooser.setDialogTitle("Please Select Codebase Root Directory");
		}

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		// If they hit cancel shutdown system
		if (chooser.showOpenDialog(frame) == JFileChooser.CANCEL_OPTION) {
			System.exit(0);
		}

		// If this happens we have other issues, but to be safe :)
		if (chooser.getSelectedFile() == null)
			return null;

		rootDir = chooser.getSelectedFile().getAbsolutePath();
		frame.dispose();
		return rootDir;
	}

	/**
	 * This class just contains the order in which bits are added to the git
	 * repo being parsed as well as all of the files inside the codebase
	 */
	public static class StupidContainer {
		public final ArrayList<String> sourceFiles;
		public final String codeRoot;
		public final String folderName;
		Hashtable<String, Integer> commitCounts;

		public StupidContainer(ArrayList<String> files, String codeRoot) {
			this.sourceFiles = files;
			this.folderName = null;
			this.codeRoot = codeRoot;
		}

		public StupidContainer(ArrayList<String> files, String codeRoot,
				String folderName, Hashtable<String, Integer> commitCounts) {
			this.sourceFiles = files;
			this.folderName = folderName;
			this.codeRoot = codeRoot;
			this.commitCounts = commitCounts;
		}

		public boolean hasValidGitRepo() {
			return commitCounts != null;
		}

	}
}
