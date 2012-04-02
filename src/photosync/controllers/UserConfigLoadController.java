package photosync.controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import photosync.com.UserConfigResource;
import photosync.com.XMLFilter;
import photosync.models.PhotoSyncModel;
import photosync.views.IComponentReachable;

public class UserConfigLoadController extends PhotoSyncAbstractController {

	private static final long serialVersionUID = 4436785913040090273L;

	private JFrame frame;
	private JFileChooser chooser;

	public UserConfigLoadController(final String iText, final PhotoSyncModel iModel, final IComponentReachable iPanel) {
		super(iText, iModel, iPanel);

		// Init
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("config"));
		chooser.setDialogTitle("Select your configuration");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.addChoosableFileFilter(new XMLFilter());
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			try {
				UserConfigResource userConfig = new UserConfigResource(chooser.getSelectedFile().getAbsolutePath());

				photoSyncModel.setInputDirectory(userConfig.getInputDirectory().getAbsolutePath());
				panel.getComboBoxInput().insertItemAt(userConfig.getInputDirectory().getAbsolutePath(), 0);
				panel.getComboBoxInput().setSelectedIndex(0);

				photoSyncModel.setOutputDirectory(userConfig.getOutputDirectory().getAbsolutePath());
				panel.getComboBoxOutput().insertItemAt(userConfig.getOutputDirectory().getAbsolutePath(), 0);
				panel.getComboBoxOutput().setSelectedIndex(0);
			} catch (Exception e1) {
				//TODO
				e1.printStackTrace();
			}
		}
	}
}
