package photosync.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import photosync.controllers.StatusController;
import photosync.models.PhotoSyncModels;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;

public class StatusDialog extends AbstractPhotoSyncDialog implements IStatusReportable {

	private static final long serialVersionUID = -1770662895478574016L;

	public StatusDialog(final PhotoSyncModels iPhotoSyncModel) {
		super(iPhotoSyncModel);
	}

	private JProgressBar progressBar;
	private JLabel lbFileStatus;

	@Override
	protected final void build() {
		setVisible(true);
		setBounds(100, 100, 450, 100);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		progressBar = new JProgressBar(0, 100);
		contentPanel.add(progressBar, BorderLayout.CENTER);
		progressBar.setValue(0);

		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonPane = new GridBagLayout();
		gbl_buttonPane.columnWidths = new int[]{50, 100, 59, 0};
		gbl_buttonPane.rowHeights = new int[]{23, 0};
		gbl_buttonPane.columnWeights = new double[]{0.0, 10.0, 0.0, Double.MIN_VALUE};
		gbl_buttonPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		buttonPane.setLayout(gbl_buttonPane);

		JLabel lblFilesProcessed = new JLabel("Files processed");
		GridBagConstraints gbc_lblFilesProcessed = new GridBagConstraints();
		gbc_lblFilesProcessed.anchor = GridBagConstraints.WEST;
		gbc_lblFilesProcessed.insets = new Insets(0, 0, 0, 5);
		gbc_lblFilesProcessed.gridx = 0;
		gbc_lblFilesProcessed.gridy = 0;
		buttonPane.add(lblFilesProcessed, gbc_lblFilesProcessed);

		lbFileStatus = new JLabel("0 / 0");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		buttonPane.add(lbFileStatus, gbc_label);

		JButton cancelButton = new JButton(new StatusController("Abort", photoSyncModel, this));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_cancelButton.gridx = 2;
		gbc_cancelButton.gridy = 0;
		buttonPane.add(cancelButton, gbc_cancelButton);
	}

	@Override
	public final void setStatus(final int iStatus) {
		progressBar.setValue(iStatus);
	}

	@Override
	public final void close() {
		setVisible(false);
	}

	@Override
	public final void open() {
		progressBar.setValue(0);
		setVisible(true);
	}

	@Override
	public final void setFileStatus(final long iFileStatus, final long iFilesToProcess) {
		lbFileStatus.setText(iFileStatus + " / " + iFilesToProcess);
	}

}
