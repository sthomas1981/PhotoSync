package photosync.controllers;

import java.awt.event.ActionEvent;

import photosync.models.PhotoSyncModels;
import photosync.views.IStatusReportable;

public class StatusController extends IStatusAbstractController {

	private static final long serialVersionUID = 7810006275021085813L;

	public StatusController(final String text, final PhotoSyncModels iModels, final IStatusReportable iStatusReport) {
		super(text, iModels, iStatusReport);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		photoSyncModels.getPhotoSynCoreModel().abort();
	}

}
