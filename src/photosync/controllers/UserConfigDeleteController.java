package photosync.controllers;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;

import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public class UserConfigDeleteController extends PhotoSyncAbstractController {

	private static final long serialVersionUID = -8358134154892747301L;

	public UserConfigDeleteController(final String iText, final PhotoSyncModels iModel, final IComponentReachable iPanel) {
		super(iText, iModel, iPanel);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (panel.getUserConfigList().getSelectedValue() != null) {
			String filename = "config" + File.separator + panel.getUserConfigList().getSelectedValue().toString();
			File file = new File(filename);
			file.delete();
			photoSyncModels.getUserConfigModel().getUserConfigListModel();
		} else {
			JOptionPane.showMessageDialog(null, "User config not selected", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
