package TBALogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import TBALogic.TBALogic.StupidContainer.LogOp;
import TBALogic.TBALogic.StupidContainer.Ops;

public class TBALogic {

	public final String EXTENSION = ".java";
	public final int ATTEMPTS = 3;
	public final int MAX_FILES = 25;
	public final int MIN_FILES = 5;
	public final String WINDOWS = "windows";
	public final String GIT = ".git";
	public String gitDir;
	public ArrayList<LogOp> logs;

	/**
	 * Finds all java files in the codebase
	 * 
	 * @return an arraylist of all java files in the codebase
	 * @throws IOException
	 */
	public StupidContainer generateFileList() throws IOException {
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
	 * @throws IOException
	 */
	private StupidContainer populateFileList() throws IOException {
		String root = getCodeRoot();
		if (root == null) {
			return null;
		}

		ArrayList<String> files = new ArrayList<String>();
		searchDFS(new File(root).listFiles(), files);

		String dir = System.getProperty("user.dir");
		boolean windows = System.getProperty("os.name").toLowerCase()
				.contains("windows");

		if (gitDir == null)
			return new StupidContainer(files);

		logs = new ArrayList<LogOp>();
		if (windows) {
			Process p = Runtime.getRuntime().exec(
					"cmd /C " + dir + "\\gitlog.sh " + gitDir);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.contains(".java")) {
					if (line.startsWith("A")) {
						logs.add(new LogOp(Ops.ADD, extractName(line)));
					} else {
						logs.add(new LogOp(Ops.DELETE, extractName(line)));
					}
				}
			}
		} else {
			// TODO get this working on Linux Kernel
			Runtime.getRuntime().exec(
					"SOMETHING GOES HERE" + dir + "/gitlog.sh " + gitDir);
		}
		Collections.reverse(logs);
		return new StupidContainer(files, logs);
	}

	/**
	 * Extracts the filename from a git commit line
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
		return line.substring(1).trim();
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

	/**
	 * JFileChooser to select codebase root directory JFileChooser code adapted
	 * from: http://www.rgagnon.com/javadetails/java-0370.html
	 * 
	 * @return the root directory of the codebase to parse
	 */
	private String getCodeRoot() {
		String rootDir;
		JFrame frame = new JFrame();
		JFileChooser chooser = new JFileChooser();
		// Set proper options for root selector
		chooser.setCurrentDirectory(new File(".."));
		chooser.setDialogTitle("Please Select Codebase Root Directory");
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
		public final ArrayList<LogOp> gitLog;

		public enum Ops {
			ADD, DELETE
		};

		public StupidContainer(ArrayList<String> files) {
			this.sourceFiles = files;
			this.gitLog = null;
		}

		public StupidContainer(ArrayList<String> files, ArrayList<LogOp> logs) {
			this.sourceFiles = files;
			this.gitLog = logs;
		}

		public boolean hasValidGitRepo() {
			return gitLog != null;
		}

		public static class LogOp {
			public final Ops op;
			public final String fileName;

			public LogOp(Ops op, String fileName) {
				this.op = op;
				this.fileName = fileName;
			}

			@Override
			public String toString() {
				return "Operation: " + op.toString() + " " + fileName;
			}
		}
	}
}
