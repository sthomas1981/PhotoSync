package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import photosync.models.PhotoSyncModel;
import photosync.views.IComponentReachable;

public class OutputDirectoryBrowseController extends DirectoryBrowseController {

	private static final long serialVersionUID = 8601948869448394032L;

	public OutputDirectoryBrowseController(final String iBtnText, final PhotoSyncModel iPhotoSyncModel, final IComponentReachable iPanel) {
		super(iBtnText, iPhotoSyncModel, iPanel);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			photoSyncModel.setOutputDirectory(chooser.getSelectedFile().getAbsolutePath());
			panel.getComboBoxOutput().insertItemAt(makeObj(chooser.getSelectedFile().getAbsolutePath()), 0);
			panel.getComboBoxOutput().setSelectedIndex(0);
		}
	}

}
