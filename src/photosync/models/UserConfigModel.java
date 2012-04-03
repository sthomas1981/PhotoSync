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
		if (configDirectory.exists()) {
			String[] data = configDirectory.list(new XMLFilter());
			if (data.length > 0) {
				for (String userConfig : data) {
					userConfigList.addElement(userConfig);
				}
			}
		} else {
			configDirectory.mkdir();
		}
		return userConfigList;
	}
}
