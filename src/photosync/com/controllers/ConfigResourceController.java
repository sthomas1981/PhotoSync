package photosync.com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ConfigResourceController {

	private static ConfigResourceController instance = null;

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

	public final Set<String> getPhotoFileExtensionList() {

		Set<String> photoFileExtensionList = new HashSet<String>();

		Element mediaFile = xmlConfig.getChild("mediaFile");
		List<?> list = mediaFile.getChildren("photoFileHandled");
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			photoFileExtensionList.add(node.getChildText("extension"));
		}

		return photoFileExtensionList;
	}

	public final Set<String> getVideoFileExtensionList() {

		Set<String> videoFileExtensionList = new HashSet<String>();

		Element mediaFile = xmlConfig.getChild("mediaFile");
		List<?> list = mediaFile.getChildren("videoFileHandled");
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			videoFileExtensionList.add(node.getChildText("extension"));
		}

		return videoFileExtensionList;
	}

	public final String getOutPutDirectoryFormat() {
		Element mediaFile = xmlConfig.getChild("format");
		return mediaFile.getChildText("outputDirectory");
	}
}
