package TBALogic;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TBALogic {

	public final String EXTENSION = ".java";
	public final int ATTEMPTS = 3;
	public final int MAX_FILES = 25;
	public final int MIN_FILES = 5;

	/**
	 * Finds all java files in the codebase
	 * 
	 * @return an arraylist of all java files in the codebase
	 */
	public ArrayList<String> generateFileList() {
		int tries = 0;
		ArrayList<String> files;

		do {
			tries++;
			files = populateFileList();

			if (files == null)
				continue;

			if (checkValidCodebase(files.size()))
				break;

		} while (tries < ATTEMPTS);

		if (tries > ATTEMPTS)
			return null;

		return files;
	}

	/**
	 * Prompts for codebase root dir and then finds all java files in
	 * subdirectories
	 * 
	 * @return an arraylist of all of the absolute paths to the files in the
	 *         codebase
	 */
	private ArrayList<String> populateFileList() {
		String root = getCodeRoot();
		if (root == null) {
			return null;
		}

		ArrayList<String> files = new ArrayList<String>();
		searchDFS(new File(root).listFiles(), files);

		return files;
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
		chooser.setCurrentDirectory(new File("."));
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
}
