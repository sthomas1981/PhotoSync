package photosync.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

import photosync.com.controllers.ConfigResourceController;
import photosync.com.exceptions.ExtensionNotHandledException;

public final class MediaFileFactory {

	private static Logger logger = Logger.getLogger(MediaFileFactory.class);

	public static final String EXTENSION_JPG = "jpg";
	public static final String EXTENSION_PNG = "png";
	public static final String EXTENSION_TIF = "tiff";
	public static final String EXTENSION_BMP = "bmp";

	public static final String EXTENSION_AVI = "avi";
	public static final String EXTENSION_MPG = "mpg";
	public static final String EXTENSION_3GP = "3gp";
	public static final String EXTENSION_MOV = "mov";
	public static final String EXTENSION_MP4 = "mp4";

	private static Set<String> photoExtensionHandled = new HashSet<String>();
	private static Set<String> videoExtensionHandled = new HashSet<String>();

	private static MediaFileFactory instance = null;

	protected MediaFileFactory() {

		try {
			photoExtensionHandled.addAll(ConfigResourceController.getInstance().getPhotoFileExtensionList());
		} catch (Exception e) {
			photoExtensionHandled.add(EXTENSION_JPG);
			photoExtensionHandled.add(EXTENSION_BMP);
			photoExtensionHandled.add(EXTENSION_PNG);
			photoExtensionHandled.add(EXTENSION_TIF);
		}

		try {
			videoExtensionHandled.addAll(ConfigResourceController.getInstance().getVideoFileExtensionList());
		} catch (Exception e) {
			videoExtensionHandled.add(EXTENSION_AVI);
			videoExtensionHandled.add(EXTENSION_MPG);
			videoExtensionHandled.add(EXTENSION_3GP);
			videoExtensionHandled.add(EXTENSION_MOV);
			videoExtensionHandled.add(EXTENSION_MP4);
		}
	}

	public static MediaFileFactory getInstance() {
		if (instance == null) {
			instance = new MediaFileFactory();
		}
		return instance;
	}

	public MediaFile getMediaFile(final String iExtension, final String iPath) throws ExtensionNotHandledException, IOException {
		if (photoExtensionHandled.contains(iExtension)) {
			logger.debug("PhotoFile instantiation");
			return new PhotoFile(iPath);
		} else if (videoExtensionHandled.contains(iExtension)) {
			logger.debug("VideoFile instantiation");
			return new VideoFile(iPath);
		} else {
			throw new ExtensionNotHandledException();
		}
	}

}
