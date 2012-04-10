package photosync.views;

import java.awt.EventQueue;


import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PhotoSyncIHM {

	private static Logger logger = Logger.getLogger(PhotoSyncIHM.class);

	public PhotoSyncIHM() {
	}

	public static void main(final String[] args) {

		PropertyConfigurator.configure("log4j.properties");
		logger.info("IHM start");

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
