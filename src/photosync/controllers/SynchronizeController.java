package photosync.controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
				photoSyncModels.getPhotoSynCoreModel().run();

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
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				ArrayList<String> listKey = new ArrayList<String>(directories.keySet());
				Collections.sort(listKey);
				for (String key : listKey) {
					photoSyncModels.getPhotoSynCoreModel().getTreeModel().insertNodeInto(directories.get(key), photoSyncModels.getPhotoSynCoreModel().getTree(), photoSyncModels.getPhotoSynCoreModel().getTree().getChildCount());
				}
			}
		});
	}

}
