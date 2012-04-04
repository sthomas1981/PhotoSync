package photosync.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import photosync.controllers.CompareController;
import photosync.models.PhotoSyncModels;

public class CorePanel extends AbstractPhotoSyncPanel implements ICoreComponentsReachable {

	private static final long serialVersionUID = -1514450698693657021L;

	private static Insets DefaultInset = new Insets(2, 2, 2, 2);
	private JTree comparedItemsTree;
	private StatisticsPanel statsPanel;

	public CorePanel(final PhotoSyncModels iPhotoSyncModel) {
		super(iPhotoSyncModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected final void build() {
		GridBagLayout gblTree = new GridBagLayout();
		gblTree.columnWidths = new int[]{0, 0};
		gblTree.rowHeights = new int[]{0, 0};
		gblTree.columnWeights = new double[]{1.0, 1.0};
		gblTree.rowWeights = new double[]{0.0, 1.0};
		setLayout(gblTree);

		JButton btnCompare = new JButton(new CompareController("Compare", photoSyncModel, this));
		GridBagConstraints gbcBtnCompare = new GridBagConstraints();
		gbcBtnCompare.insets = DefaultInset;
		gbcBtnCompare.gridx = 0;
		gbcBtnCompare.gridy = 0;
		add(btnCompare, gbcBtnCompare);

		JButton btnSynchronize = new JButton("Synchronize");
		GridBagConstraints gbcBtnSynchronize = new GridBagConstraints();
		gbcBtnSynchronize.insets = DefaultInset;
		gbcBtnSynchronize.gridx = 1;
		gbcBtnSynchronize.gridy = 0;
		add(btnSynchronize, gbcBtnSynchronize);

		comparedItemsTree = new JTree(photoSyncModel.getPhotoSynCoreModel().getTree());

		JScrollPane scrollPaneComparedItems = new JScrollPane(comparedItemsTree);
		GridBagConstraints gbcScrollPaneComparedItems = new GridBagConstraints();
		gbcScrollPaneComparedItems.insets = DefaultInset;
		gbcScrollPaneComparedItems.fill = GridBagConstraints.BOTH;
		gbcScrollPaneComparedItems.gridx = 0;
		gbcScrollPaneComparedItems.gridy = 1;
		add(scrollPaneComparedItems, gbcScrollPaneComparedItems);

		statsPanel = new StatisticsPanel(photoSyncModel);
		GridBagConstraints gbcStatsPanel = new GridBagConstraints();
		gbcStatsPanel.anchor = GridBagConstraints.NORTH;
		gbcStatsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbcStatsPanel.gridx = 1;
		gbcStatsPanel.gridy = 1;
		add(statsPanel, gbcStatsPanel);
	}

	@Override
	public final JTree getComparedItemsTree() {
		return comparedItemsTree;
	}

}
