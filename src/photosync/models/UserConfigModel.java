package photosync.models;

import java.io.File;

import javax.swing.DefaultListModel;

import photosync.com.XMLFilter;

public class UserConfigModel {

	private DefaultListModel userConfigList;

	public UserConfigModel() {
		userConfigList = new DefaultListModel();
	}

	public final DefaultListModel getUserConfigListModel() {
		userConfigList.clear();
		File configDirectory = new File("config");
		String[] data = configDirectory.list(new XMLFilter());
		for (String userConfig : data) {
			userConfigList.addElement(userConfig);
		}
		return userConfigList;
	}
}
