package photosync.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import photosync.controllers.SynchronizeController;
import photosync.models.PhotoSyncModels;

public class CorePanel extends AbstractPhotoSyncPanel implements ICoreComponentsReachable {

	private static final long serialVersionUID = -1514450698693657021L;

	private static Insets DefaultInset = new Insets(2, 2, 2, 2);
	private JTree comparedItemsTree;
	private JTree exceptionItemsTree;
	private StatisticsPanel statsPanel;

	public CorePanel(final PhotoSyncModels iPhotoSyncModel) {
		super(iPhotoSyncModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected final void build() {
		GridBagLayout gblTree = new GridBagLayout();
		gblTree.columnWidths = new int[]{0, 0};
		gblTree.rowHeights = new int[]{0, 0, 0};
		gblTree.columnWeights = new double[]{1.0, 1.0};
		gblTree.rowWeights = new double[]{0.0, 2.0, 1.0};
		setLayout(gblTree);

		JButton btnSynchronize = new JButton(new SynchronizeController("Synchronize", photoSyncModel, this));
		GridBagConstraints gbcBtnSynchronize = new GridBagConstraints();
		gbcBtnSynchronize.insets = DefaultInset;
		gbcBtnSynchronize.gridx = 0;
		gbcBtnSynchronize.gridy = 0;
		gbcBtnSynchronize.gridwidth = 2;
		add(btnSynchronize, gbcBtnSynchronize);

		comparedItemsTree = new JTree(photoSyncModel.getPhotoSynCoreModel().getTreeModel());
		comparedItemsTree.expandPath(new TreePath(photoSyncModel.getPhotoSynCoreModel().getTree()));

		JScrollPane scrollPaneComparedItems = new JScrollPane(comparedItemsTree);
		GridBagConstraints gbcScrollPaneComparedItems = new GridBagConstraints();
		gbcScrollPaneComparedItems.insets = DefaultInset;
		gbcScrollPaneComparedItems.fill = GridBagConstraints.BOTH;
		gbcScrollPaneComparedItems.gridx = 0;
		gbcScrollPaneComparedItems.gridy = 1;
		gbcScrollPaneComparedItems.gridwidth = 2;
		add(scrollPaneComparedItems, gbcScrollPaneComparedItems);

		exceptionItemsTree = new JTree(photoSyncModel.getPhotoSynCoreModel().getExceptionTreeModel());
		exceptionItemsTree.expandPath(new TreePath(photoSyncModel.getPhotoSynCoreModel().getExceptionTree()));

		JScrollPane scrollPaneExceptionItems = new JScrollPane(exceptionItemsTree);
		GridBagConstraints gbcScrollPaneExceptionItems = new GridBagConstraints();
		gbcScrollPaneExceptionItems.insets = DefaultInset;
		gbcScrollPaneExceptionItems.fill = GridBagConstraints.BOTH;
		gbcScrollPaneExceptionItems.gridx = 0;
		gbcScrollPaneExceptionItems.gridy = 2;
		gbcScrollPaneExceptionItems.gridwidth = 2;
		add(scrollPaneExceptionItems, gbcScrollPaneExceptionItems);
	}

	@Override
	public final JTree getComparedItemsTree() {
		return comparedItemsTree;
	}

	@Override
	public JTree getExceptionItemsTree() {
		return exceptionItemsTree;
	}

}
