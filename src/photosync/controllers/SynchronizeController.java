package photosync.controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import photosync.com.controllers.ConfigResourceController;
import photosync.core.MediaFile;
import photosync.models.PhotoSyncModels;
import photosync.views.ICoreComponentsReachable;

public class SynchronizeController extends CoreAbstractController {

	private static final long serialVersionUID = 1937940987426253788L;

	public SynchronizeController(final String iText, final PhotoSyncModels iModel, final ICoreComponentsReachable iPanel) {
		super(iText, iModel, iPanel);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				photoSyncModels.getPhotoSynCoreModel().synchronize();
				Map<String, DefaultMutableTreeNode> directories = new HashMap<String, DefaultMutableTreeNode>();
				for (MediaFile media : photoSyncModels.getPhotoSynCoreModel().getSynchronizedItemsQueue()) {
					DateFormat dfm;
					try {
						dfm = new SimpleDateFormat(ConfigResourceController.getInstance().getOutPutDirectoryFormat());
						String directory = dfm.format(media.getCreationDate());
						DefaultMutableTreeNode directoryNode = new DefaultMutableTreeNode(directory);
						if (!directories.containsKey(directory)) {
							directories.put(directory, directoryNode);
						}
						directories.get(directory).add(new DefaultMutableTreeNode(media.getAbsoluteFile()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}
		});
	}

}
