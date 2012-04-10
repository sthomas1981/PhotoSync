package photosync.com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import photosync.com.UserConfigResource;

public class ConfigResourceController {

	private static ConfigResourceController instance = null;

	private static Logger logger = Logger.getLogger(UserConfigResource.class);

	private static Element xmlConfig;

	protected ConfigResourceController() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("configuration.xml");
		Document document = (Document) builder.build(xmlFile);
		xmlConfig = document.getRootElement();
	}

	public static ConfigResourceController getInstance() throws JDOMException, IOException {
		if (instance == null) {
			instance = new ConfigResourceController();
		}
		return instance;
	}

	public static void init() {
		instance = null;
	}

	@SuppressWarnings("unchecked")
	public final Set<String> getPhotoFileExtensionList() {

		Set<String> photoFileExtensionList = new HashSet<String>();

		Element mediaFile = xmlConfig.getChild("mediaFile");
		List<?> list = mediaFile.getChildren("photoFileHandled");
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			photoFileExtensionList.add(node.getChildText("extension"));
		}
		logger.debug("Photo file extension handled : " + Arrays.asList(photoFileExtensionList));
		return photoFileExtensionList;
	}

	@SuppressWarnings("unchecked")
	public final Set<String> getVideoFileExtensionList() {

		Set<String> videoFileExtensionList = new HashSet<String>();

		Element mediaFile = xmlConfig.getChild("mediaFile");
		List<?> list = mediaFile.getChildren("videoFileHandled");
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			videoFileExtensionList.add(node.getChildText("extension"));
		}
		logger.debug("Video file extension handled : " + Arrays.asList(videoFileExtensionList));
		return videoFileExtensionList;
	}

	public final String getOutPutDirectoryFormat() {
		Element mediaFile = xmlConfig.getChild("format");
		logger.debug("Output directory format : " + mediaFile.getChildText("outputDirectory"));
		return mediaFile.getChildText("outputDirectory");
	}
}
