package photosync.models;

public class PhotoSyncModels {
	private CoreModel photoSynCoreModel;
	private UserConfigModel userConfigModel;

	public PhotoSyncModels() {
		photoSynCoreModel = new CoreModel();
		userConfigModel = new UserConfigModel();
	}

	public final CoreModel getPhotoSynCoreModel() {
		return photoSynCoreModel;
	}

	public final UserConfigModel getUserConfigModel() {
		return userConfigModel;
	}
}
