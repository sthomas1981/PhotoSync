package photosync.models;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.tree.DefaultMutableTreeNode;

import photosync.core.MediaFile;
import photosync.wkr.TaskManager;

public class CoreModel {

	private String inputDirectory;
	private String outputDirectory;
	private TaskManager task;
	private DefaultMutableTreeNode tree = new DefaultMutableTreeNode("Synchronized Items");

	public final String getInputDirectory() {
		return inputDirectory;
	}

	public final void setInputDirectory(final String iInputDirectory) {
		this.inputDirectory = iInputDirectory;
	}

	public final String getOutputDirectory() {
		return outputDirectory;
	}

	public final void setOutputDirectory(final String iOutputDirectory) {
		this.outputDirectory = iOutputDirectory;
	}

	public final DefaultMutableTreeNode getTree() {
		return tree;
	}

	public final void synchronize() {
		task = new TaskManager(new File(inputDirectory), new File(outputDirectory));
		task.init();
		task.run();
	}

	public final boolean hasTaskToProcess() {
		return task.hasTaskToProcess();
	}

	public final ConcurrentLinkedQueue<MediaFile> getSynchronizedItemsQueue() {
		return task.getSynchronizedItemsQueue();
	}
}
