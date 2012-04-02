package photosync.views;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;

import photosync.models.PhotoSyncModel;

public class PhotoSyncPanel extends AbstractPhotoSyncPanel implements IComponentReachable {

	private static final long serialVersionUID = -7937821371265894596L;

	private DirectoryPanel dirPanel;

	public PhotoSyncPanel(final PhotoSyncModel iModel) {
		super(iModel);
	}

	@Override
	public final void build() {
		setLayout(new BorderLayout(0, 0));
		dirPanel = new DirectoryPanel(photoSyncModel);
		add(dirPanel, BorderLayout.NORTH);
	}

	@Override
	public final JComboBox getComboBoxOutput() {
		return dirPanel.getComboBoxOutput();
	}

	@Override
	public final JComboBox getComboBoxInput() {
		return dirPanel.getComboBoxInput();
	}

	@Override
	public final JList getUserConfigList() {
		return dirPanel.getUserConfigList();
	}

	@Override
	public final DefaultListModel getUserConfigListModel() {
		return dirPanel.getUserConfigListModel();
	}

}
