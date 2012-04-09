package photosync.models;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import photosync.core.MediaFile;
import photosync.wkr.TaskManager;

public class CoreModel implements Runnable {

	private String inputDirectory;
	private String outputDirectory;
	private TaskManager task;
	private DefaultMutableTreeNode tree = new DefaultMutableTreeNode("Synchronized Items");
	private DefaultTreeModel treeModel = new DefaultTreeModel(tree);
	private DefaultMutableTreeNode exceptionTree = new DefaultMutableTreeNode("Items in exception");
	private DefaultTreeModel exceptionTreeModel = new DefaultTreeModel(exceptionTree);

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

	public final DefaultMutableTreeNode getExceptionTree() {
		return exceptionTree;
	}

	public final DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public final DefaultTreeModel getExceptionTreeModel() {
		return exceptionTreeModel;
	}

	public final boolean hasTaskToProcess() {
		return task.hasTaskToProcess();
	}

	public final ConcurrentLinkedQueue<MediaFile> getSynchronizedItemsQueue() {
		return task.getSynchronizedItemsQueue();
	}

	public final ConcurrentLinkedQueue<MediaFile> getExceptionItemsQueue() {
		return task.getExceptionItemsQueue();
	}

	public final long getFilesToProcess() {
		return task.getFilesToProcess();
	}

	public final long getFilesStatus() {
		return task.getFilesStatus();
	}

	public final void init() {
		task = new TaskManager(new File(inputDirectory), new File(outputDirectory));
		task.init();
	}

	@Override
	public final void run() {
		task.run();
	}
}
