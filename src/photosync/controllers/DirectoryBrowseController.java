package photosync.controllers;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public abstract class DirectoryBrowseController extends PhotoSyncAbstractController {

	private static final long serialVersionUID = -9003563582195262128L;

	protected File inputDirectory;
	protected JFrame frame;
	protected JFileChooser chooser;


	public DirectoryBrowseController(final String iBtnText, final PhotoSyncModels iPhotoSyncModel, final IComponentReachable iPanel) {
		super(iBtnText, iPhotoSyncModel, iPanel);
		this.inputDirectory = new File("");
		chooser = new JFileChooser();
		panel = iPanel;
		photoSyncModels = iPhotoSyncModel;

		// Init
		chooser.setCurrentDirectory(inputDirectory);
		chooser.setDialogTitle("Select your directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
	}

	@Override
	public abstract void actionPerformed(final ActionEvent e);

	protected final Object makeObj(final String item)  {
		return new Object() { public String toString() { return item; } };
	}
}
