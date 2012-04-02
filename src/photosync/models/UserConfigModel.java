package photosync.models;

import java.io.File;

import javax.swing.DefaultListModel;

import photosync.com.XMLFilter;

public class UserConfigModel {
	private DefaultListModel userConfigList;

	public final DefaultListModel getUserConfigListModel() {
		File configDirectory = new File("config");
		String[] data = configDirectory.list(new XMLFilter());
		userConfigList = new DefaultListModel();
		for (String userConfig : data) {
			userConfigList.addElement(userConfig);
		}
		return userConfigList;
	}
}
