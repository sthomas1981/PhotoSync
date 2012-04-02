package photosync.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import photosync.com.XMLFilter;
import photosync.controllers.InputDirectoryBrowseController;
import photosync.controllers.OutputDirectoryBrowseController;
import photosync.controllers.UserConfigListLoadController;
import photosync.models.PhotoSyncModel;

public class DirectoryPanel extends AbstractPhotoSyncPanel implements IComponentReachable {

	public DirectoryPanel(final PhotoSyncModel iModel) {
		super(iModel);
	}

	private static final long serialVersionUID = -7937821371265894596L;

	private static Insets DefaultInset = new Insets(2, 2, 2, 2);
	private JComboBox comboBoxOutput;
	private JComboBox comboBoxInput;
	private DefaultListModel userConfigListModel;
	private JList userConfigList;

	@Override
	protected final void build() {
		GridBagLayout gblPanelDirectory = new GridBagLayout();
		gblPanelDirectory.rowWeights = new double[]{0.0, 0.0};
		gblPanelDirectory.columnWeights = new double[]{0.0, 0.0, 12.0, 0.0};
		setLayout(gblPanelDirectory);

		File configDirectory = new File("config");
		String[] data = configDirectory.list(new XMLFilter());
		userConfigListModel = new DefaultListModel();
		for (String userConfig : data) {
			userConfigListModel.addElement(userConfig);
		}
		userConfigList = new JList(userConfigListModel);
		userConfigList.setVisibleRowCount(4);
		userConfigList.addMouseListener(new UserConfigListLoadController(photoSyncModel, this));

		JScrollPane scrollPane = new JScrollPane(userConfigList);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.insets = DefaultInset;
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 0;
		gbcScrollPane.gridy = 0;
		gbcScrollPane.gridheight = 2;
		gbcScrollPane.anchor = GridBagConstraints.LINE_START;
		add(scrollPane, gbcScrollPane);

		JLabel lblLabelInput = new JLabel("Input");
		GridBagConstraints gbcLblInput = new GridBagConstraints();
		gbcLblInput.insets = DefaultInset;
		gbcLblInput.gridx = 1;
		gbcLblInput.gridy = 0;
		gbcLblInput.weightx = 0;
		gbcLblInput.anchor = GridBagConstraints.EAST;
		add(lblLabelInput, gbcLblInput);

		comboBoxInput = new JComboBox();
		GridBagConstraints gbcComboBoxInput = new GridBagConstraints();
		gbcComboBoxInput.insets = DefaultInset;
		gbcComboBoxInput.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxInput.gridx = 2;
		gbcComboBoxInput.gridy = 0;
		gbcLblInput.anchor = GridBagConstraints.CENTER;
		add(comboBoxInput, gbcComboBoxInput);

		JButton btnInput = new JButton(new InputDirectoryBrowseController("Browse", photoSyncModel, this));
		GridBagConstraints gbcBtnInput = new GridBagConstraints();
		gbcBtnInput.insets = DefaultInset;
		gbcBtnInput.gridx = 3;
		gbcBtnInput.gridy = 0;
		gbcLblInput.anchor = GridBagConstraints.LINE_END;
		add(btnInput, gbcBtnInput);

		JLabel lblOutput = new JLabel("Output");
		GridBagConstraints gbcLblOutput = new GridBagConstraints();
		gbcLblOutput.anchor = GridBagConstraints.EAST;
		gbcLblOutput.insets = DefaultInset;
		gbcLblOutput.gridx = 1;
		gbcLblOutput.gridy = 1;
		add(lblOutput, gbcLblOutput);

		comboBoxOutput = new JComboBox();
		GridBagConstraints gbcComboBoxOutput = new GridBagConstraints();
		gbcComboBoxOutput.insets = DefaultInset;
		gbcComboBoxOutput.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxOutput.gridx = 2;
		gbcComboBoxOutput.gridy = 1;
		add(comboBoxOutput, gbcComboBoxOutput);

		JButton btnOutput = new JButton(new OutputDirectoryBrowseController("Browse", photoSyncModel, this));
		GridBagConstraints gbcBtnOutput = new GridBagConstraints();
		gbcBtnOutput.gridx = 3;
		gbcBtnOutput.gridy = 1;
		add(btnOutput, gbcBtnOutput);

	}

	@Override
	public final JComboBox getComboBoxOutput() {
		return comboBoxOutput;
	}

	@Override
	public final JComboBox getComboBoxInput() {
		return comboBoxInput;
	}

	@Override
	public final DefaultListModel getUserConfigListModel() {
		return userConfigListModel;
	}

	@Override
	public final JList getUserConfigList() {
		return userConfigList;
	}

    public final void addConfigListListener(final MouseListener mouseEvent) {
    	userConfigList.addMouseListener(mouseEvent);
    }
}
