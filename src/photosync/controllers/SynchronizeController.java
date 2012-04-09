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
import photosync.views.IStatusReportable;
import photosync.views.StatusDialog;

public class SynchronizeController extends CoreAbstractController {

	private static final long serialVersionUID = 1937940987426253788L;

	public SynchronizeController(final String iText, final PhotoSyncModels iModel, final ICoreComponentsReachable iPanel) {
		super(iText, iModel, iPanel);
	}

	private void buildTree() {
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

	private void buildExceptionTree() {
		Map<String, DefaultMutableTreeNode> directories = new HashMap<String, DefaultMutableTreeNode>();
		for (MediaFile media : photoSyncModels.getPhotoSynCoreModel().getExceptionItemsQueue()) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(media.getAbsoluteFile());
			photoSyncModels.getPhotoSynCoreModel().getExceptionTreeModel().insertNodeInto(node, photoSyncModels.getPhotoSynCoreModel().getExceptionTree(), photoSyncModels.getPhotoSynCoreModel().getExceptionTree().getChildCount());
		}
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				photoSyncModels.getPhotoSynCoreModel().init();

				Thread run = new Thread(photoSyncModels.getPhotoSynCoreModel());
				run.start();

				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						IStatusReportable statusDialog = new StatusDialog(photoSyncModels);
						while (photoSyncModels.getPhotoSynCoreModel().hasTaskToProcess()) {
							statusDialog.setStatus((int) ((double) photoSyncModels.getPhotoSynCoreModel().getFilesStatus() / (double) photoSyncModels.getPhotoSynCoreModel().getFilesToProcess() * 100));
							statusDialog.setFileStatus(photoSyncModels.getPhotoSynCoreModel().getFilesStatus(), photoSyncModels.getPhotoSynCoreModel().getFilesToProcess());
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						statusDialog.setStatus(100);
						statusDialog.close();

						buildTree();
						buildExceptionTree();
					}
				});

			}
		});
	}

}
