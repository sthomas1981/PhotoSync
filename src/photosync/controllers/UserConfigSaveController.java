package photosync.controllers;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;

import photosync.com.UserConfigResource;
import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public class UserConfigSaveController extends PhotoSyncAbstractController {

	private static final long serialVersionUID = 8007706631276219746L;


	public UserConfigSaveController(final String iText, final PhotoSyncModels iModel, final IComponentReachable iPanel) {
		super(iText, iModel, iPanel);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		try {
			if (!(panel.getComboBoxInput().getSelectedItem() == null) && !(panel.getComboBoxOutput().getSelectedItem() == null)) {
				String inputDir = panel.getComboBoxInput().getSelectedItem().toString();
				String outputDir = panel.getComboBoxOutput().getSelectedItem().toString();
				String filename = JOptionPane.showInputDialog(null, "Name of your new configuration") + ".xml";
				if (!filename.isEmpty()) {
					UserConfigResource userConfig = new UserConfigResource(filename, new File(inputDir), new File(outputDir));
					userConfig.saveConfig();
					//panel.getUserConfigListModel().addElement(filename);
				} else {
					JOptionPane.showMessageDialog(null, "Missing parameters", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Missing parameters", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e1) {
			//TODO
			e1.printStackTrace();
		}
	}
}
