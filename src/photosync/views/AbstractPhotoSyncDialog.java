package photosync.views;

import javax.swing.JDialog;

import photosync.models.PhotoSyncModels;

public abstract class AbstractPhotoSyncDialog extends JDialog {

	private static final long serialVersionUID = 1450353924712592474L;

	protected PhotoSyncModels photoSyncModel;

	public AbstractPhotoSyncDialog(final PhotoSyncModels iPhotoSyncModel) {
		super();
		photoSyncModel = iPhotoSyncModel;
		build();
	}

	protected abstract void build();
}
