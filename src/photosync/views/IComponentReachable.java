package photosync.views;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;

public interface IComponentReachable {

	JComboBox getComboBoxOutput();

	JComboBox getComboBoxInput();

	DefaultListModel getUserConfigListModel();

	JList getUserConfigList();

}
