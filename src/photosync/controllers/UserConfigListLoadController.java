package photosync.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import photosync.com.UserConfigResource;
import photosync.models.PhotoSyncModel;
import photosync.views.DirectoryPanel;

public class UserConfigListLoadController implements MouseListener {

	private PhotoSyncModel model;
	private DirectoryPanel panel;

	public UserConfigListLoadController(final PhotoSyncModel iModel, final DirectoryPanel iPanel) {
		model = iModel;
		panel = iPanel;
	}

	@Override
	public final void mouseClicked(final MouseEvent e) {
		String filename = "config" + File.separator + panel.getUserConfigList().getSelectedValue().toString();
		UserConfigResource userConfig;
		try {
			userConfig = new UserConfigResource(filename);

			model.setInputDirectory(userConfig.getInputDirectory().getAbsolutePath());
			panel.getComboBoxInput().insertItemAt(userConfig.getInputDirectory().getAbsolutePath(), 0);
			panel.getComboBoxInput().setSelectedIndex(0);

			model.setOutputDirectory(userConfig.getOutputDirectory().getAbsolutePath());
			panel.getComboBoxOutput().insertItemAt(userConfig.getOutputDirectory().getAbsolutePath(), 0);
			panel.getComboBoxOutput().setSelectedIndex(0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
