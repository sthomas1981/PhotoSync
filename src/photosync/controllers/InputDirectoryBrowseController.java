package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public class InputDirectoryBrowseController extends DirectoryBrowseController {

	private static final long serialVersionUID = 1131460667561156360L;

	public InputDirectoryBrowseController(final String iBtnText, final PhotoSyncModels iPhotoSyncModel, final IComponentReachable iPanel) {
		super(iBtnText, iPhotoSyncModel, iPanel);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			photoSyncModels.getPhotoSynCoreModel().setInputDirectory(chooser.getSelectedFile().getAbsolutePath());
			panel.getComboBoxInput().insertItemAt(makeObj(chooser.getSelectedFile().getAbsolutePath()), 0);
			panel.getComboBoxInput().setSelectedIndex(0);
		}
	}

}
