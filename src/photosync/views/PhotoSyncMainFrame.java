package photosync.views;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import photosync.controllers.UserConfigLoadController;
import photosync.controllers.UserConfigSaveController;
import photosync.models.PhotoSyncModels;

public class PhotoSyncMainFrame extends JFrame {

	private static final long serialVersionUID = 1671013850845383862L;

	private PhotoSyncPanel panelPhotoSync;
	private PhotoSyncModels photoSyncModel;

	public PhotoSyncMainFrame() {
		super();
		build();
	}

	private void build() {
		setTitle("Media Synchronizer");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setContentPane(buildContentPane());
	}

	private JTabbedPane buildContentPane() {
		photoSyncModel = new PhotoSyncModels();

		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		add(tab, BorderLayout.CENTER);

		panelPhotoSync = new PhotoSyncPanel(photoSyncModel);
		tab.addTab("Photo synchronization", null, panelPhotoSync, null);

		JPanel panelPicasaSync = new JPanel();
		tab.addTab("Picasa synchronization", null, panelPicasaSync, null);

		initMenuBar();

		return tab;
	}


	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoad = new JMenuItem(new UserConfigLoadController("Load", photoSyncModel, panelPhotoSync));
		mntmLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		mnFile.add(mntmLoad);

		JMenuItem mntmSave = new JMenuItem(new UserConfigSaveController("Save",  photoSyncModel, panelPhotoSync));
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);

		JMenu mnParameters = new JMenu("Parameters");
		menuBar.add(mnParameters);

		JMenuItem mntmMedia = new JMenuItem("Media");
		mnParameters.add(mntmMedia);

		JMenuItem mntmConfiguration = new JMenuItem("Configuration");
		mnParameters.add(mntmConfiguration);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmHelp = new JMenuItem("Help");
		mnAbout.add(mntmHelp);

		JMenuItem mntmAboutThis = new JMenuItem("About this ...");
		mnAbout.add(mntmAboutThis);
	}

}
