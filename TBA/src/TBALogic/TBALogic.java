package TBALogic;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class TBALogic {

	public final String ext = ".java";

	public ArrayList<String> generateFileList() {
		String root = "";
		root = getCodeRoot();

		ArrayList<String> files = new ArrayList<String>();
		searchDFS(new File(root).listFiles(), files);

		return files;
	}

	// Recursively search directories for files
	private void searchDFS(File[] files, ArrayList<String> list) {

		for (File file : files) {
			if (file.isDirectory()) {
				searchDFS(file.listFiles(), list);
			} else {
				if (file.getName().endsWith(ext)) {
					list.add(file.getAbsolutePath());
				}
			}
		}
	}

	// JFileChooser code adapted from:
	// http://www.rgagnon.com/javadetails/java-0370.html
	private String getCodeRoot() {
		String rootDir;
		JFrame frame = new JFrame();
		JFileChooser chooser = new JFileChooser();
		// Set proper options for root selector
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Please Select Codebase Root Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showOpenDialog(frame);
		rootDir = chooser.getSelectedFile().getAbsolutePath();
		frame.dispose();
		return rootDir;
	}
}
