package photosync.views;

import javax.swing.JPanel;

import photosync.models.PhotoSyncModel;

public abstract class AbstractPhotoSyncPanel extends JPanel {

	private static final long serialVersionUID = 4286317072394551689L;

	protected PhotoSyncModel photoSyncModel;

	public AbstractPhotoSyncPanel(final PhotoSyncModel iPhotoSyncModel) {
		super();
		photoSyncModel = iPhotoSyncModel;
		build();
	}

	protected abstract void build();

}
