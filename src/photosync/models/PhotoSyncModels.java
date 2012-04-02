package photosync.models;

public class PhotoSyncModels {
	private PhotoSyncCoreModel photoSynCoreModel;
	private UserConfigModel userConfigModel;

	public PhotoSyncModels() {
		photoSynCoreModel = new PhotoSyncCoreModel();
		userConfigModel = new UserConfigModel();
	}

	public final PhotoSyncCoreModel getPhotoSynCoreModel() {
		return photoSynCoreModel;
	}

	public final UserConfigModel getUserConfigModel() {
		return userConfigModel;
	}
}
