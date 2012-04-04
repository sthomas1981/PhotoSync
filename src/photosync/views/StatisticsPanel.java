package photosync.views;

import java.awt.GridLayout;

import javax.swing.JLabel;

import photosync.models.PhotoSyncModels;

public class StatisticsPanel extends AbstractPhotoSyncPanel {

	private static final long serialVersionUID = 197097367070811050L;

	public StatisticsPanel(final PhotoSyncModels iPhotoSyncModel) {
		super(iPhotoSyncModel);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected final void build() {
		setLayout(new GridLayout(0, 2));

		JLabel lblNewLabel_2 = new JLabel("New label");
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("New label");
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("New label");
		add(lblNewLabel_4);
	}

}
