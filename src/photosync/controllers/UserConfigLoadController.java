package photosync.controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import photosync.com.UserConfigResource;
import photosync.com.XMLFilter;
import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public class UserConfigLoadController extends PhotoSyncAbstractController implements ListSelectionListener {

	private static final long serialVersionUID = 4436785913040090273L;

	private JFrame frame;
	private JFileChooser chooser;

	public UserConfigLoadController(final String iText, final PhotoSyncModels iModel, final IComponentReachable iPanel) {
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

				photoSyncModels.getPhotoSynCoreModel().setInputDirectory(userConfig.getInputDirectory().getAbsolutePath());
				panel.getComboBoxInput().insertItemAt(userConfig.getInputDirectory().getAbsolutePath(), 0);
				panel.getComboBoxInput().setSelectedIndex(0);

				photoSyncModels.getPhotoSynCoreModel().setOutputDirectory(userConfig.getOutputDirectory().getAbsolutePath());
				panel.getComboBoxOutput().insertItemAt(userConfig.getOutputDirectory().getAbsolutePath(), 0);
				panel.getComboBoxOutput().setSelectedIndex(0);
			} catch (Exception e1) {
				//TODO
				e1.printStackTrace();
			}
		}
	}

	@Override
	public final void valueChanged(final ListSelectionEvent e) {
		String filename = "config" + File.separator + panel.getUserConfigList().getSelectedValue().toString();
		if (filename != "") {
			UserConfigResource userConfig;
			try {
				userConfig = new UserConfigResource(filename);

				photoSyncModels.getPhotoSynCoreModel().setInputDirectory(userConfig.getInputDirectory().getAbsolutePath());
				panel.getComboBoxInput().insertItemAt(userConfig.getInputDirectory().getAbsolutePath(), 0);
				panel.getComboBoxInput().setSelectedIndex(0);

				photoSyncModels.getPhotoSynCoreModel().setOutputDirectory(userConfig.getOutputDirectory().getAbsolutePath());
				panel.getComboBoxOutput().insertItemAt(userConfig.getOutputDirectory().getAbsolutePath(), 0);
				panel.getComboBoxOutput().setSelectedIndex(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
