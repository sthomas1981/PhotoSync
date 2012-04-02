package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public class OutputDirectoryBrowseController extends DirectoryBrowseController {

	private static final long serialVersionUID = 8601948869448394032L;

	public OutputDirectoryBrowseController(final String iBtnText, final PhotoSyncModels iPhotoSyncModel, final IComponentReachable iPanel) {
		super(iBtnText, iPhotoSyncModel, iPanel);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			photoSyncModels.getPhotoSynCoreModel().setOutputDirectory(chooser.getSelectedFile().getAbsolutePath());
			panel.getComboBoxOutput().insertItemAt(makeObj(chooser.getSelectedFile().getAbsolutePath()), 0);
			panel.getComboBoxOutput().setSelectedIndex(0);
		}
	}

}
