package photosync.views;

import java.awt.EventQueue;


import javax.swing.UIManager;

public class PhotoSyncIHM {

	public PhotoSyncIHM() {

	}

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhotoSyncMainFrame window = new PhotoSyncMainFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
